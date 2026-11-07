package com.example.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ExportService {

    public byte[] generarPdf(String titulo, List<String> encabezados, List<List<String>> filas) {
        Document documento = new Document(PageSize.A4.rotate(), 24, 24, 24, 24);
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(documento, salida);
            documento.open();
            documento.add(new Paragraph(titulo, new Font(Font.HELVETICA, 16, Font.BOLD)));
            documento.add(new Paragraph("Registros: " + filas.size()));
            documento.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(encabezados.size());
            tabla.setWidthPercentage(100);
            tabla.setHeaderRows(1);
            float[] anchos = new float[encabezados.size()];
            java.util.Arrays.fill(anchos, 1f);
            tabla.setWidths(anchos);

            Font fuenteEncabezado = new Font(Font.HELVETICA, 8, Font.BOLD);
            Font fuenteCelda = new Font(Font.HELVETICA, 8);
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(encabezado, fuenteEncabezado));
                celda.setHorizontalAlignment(Element.ALIGN_LEFT);
                celda.setPadding(5);
                celda.setGrayFill(0.85f);
                tabla.addCell(celda);
            }
            for (List<String> fila : filas) {
                for (String valor : fila) {
                    PdfPCell celda = new PdfPCell(new Phrase(valor, fuenteCelda));
                    celda.setPadding(4);
                    tabla.addCell(celda);
                }
            }
            documento.add(tabla);
            documento.close();
            return salida.toByteArray();
        } catch (DocumentException exception) {
            throw new IllegalStateException("No se pudo generar el archivo PDF", exception);
        } finally {
            if (documento.isOpen()) {
                documento.close();
            }
        }
    }

    public byte[] generarExcel(String titulo, List<String> encabezados, List<List<String>> filas) {
        try (Workbook libro = new XSSFWorkbook(); ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            Sheet hoja = libro.createSheet("Datos");
            Row tituloFila = hoja.createRow(0);
            tituloFila.createCell(0).setCellValue(titulo);
            Row conteoFila = hoja.createRow(1);
            conteoFila.createCell(0).setCellValue("Registros: " + filas.size());

            CellStyle estiloEncabezado = libro.createCellStyle();
            org.apache.poi.ss.usermodel.Font fuenteEncabezado = libro.createFont();
            fuenteEncabezado.setBold(true);
            estiloEncabezado.setFont(fuenteEncabezado);

            Row encabezadoFila = hoja.createRow(3);
            for (int columna = 0; columna < encabezados.size(); columna++) {
                Cell celda = encabezadoFila.createCell(columna);
                celda.setCellValue(encabezados.get(columna));
                celda.setCellStyle(estiloEncabezado);
            }
            for (int indiceFila = 0; indiceFila < filas.size(); indiceFila++) {
                Row filaExcel = hoja.createRow(indiceFila + 4);
                List<String> valores = filas.get(indiceFila);
                for (int columna = 0; columna < valores.size(); columna++) {
                    filaExcel.createCell(columna).setCellValue(valores.get(columna));
                }
            }
            hoja.createFreezePane(0, 4);
            for (int columna = 0; columna < encabezados.size(); columna++) {
                hoja.autoSizeColumn(columna);
            }
            libro.write(salida);
            return salida.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo generar el archivo Excel", exception);
        }
    }
}
