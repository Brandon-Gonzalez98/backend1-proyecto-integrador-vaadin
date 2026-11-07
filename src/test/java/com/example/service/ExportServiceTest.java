package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import com.lowagie.text.pdf.PdfReader;

class ExportServiceTest {

    private final ExportService exportService = new ExportService();

    @Test
    void generaPdfConRegistros() throws Exception {
        byte[] pdf = exportService.generarPdf("Residentes", List.of("Documento", "Nombre"),
                List.of(List.of("123", "Ana")));

        assertTrue(new String(pdf, 0, 5, java.nio.charset.StandardCharsets.US_ASCII).startsWith("%PDF-"));
        try (PdfReader lector = new PdfReader(pdf)) {
            assertEquals(1, lector.getNumberOfPages());
        }
    }

    @Test
    void generaExcelConEncabezadosYRegistros() throws Exception {
        byte[] excel = exportService.generarExcel("Residentes", List.of("Documento", "Nombre"),
                List.of(List.of("123", "Ana")));

        try (XSSFWorkbook libro = new XSSFWorkbook(new ByteArrayInputStream(excel))) {
            var hoja = libro.getSheet("Datos");
            assertEquals("Documento", hoja.getRow(3).getCell(0).getStringCellValue());
            assertEquals("Nombre", hoja.getRow(3).getCell(1).getStringCellValue());
            assertEquals("123", hoja.getRow(4).getCell(0).getStringCellValue());
            assertEquals("Ana", hoja.getRow(4).getCell(1).getStringCellValue());
        }
    }
}
