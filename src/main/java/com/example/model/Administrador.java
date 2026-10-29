package com.example.model;

import java.time.LocalDate;

public class Administrador {
    String Documento;
    String nombre;
    String Apellidos;
    LocalDate FechaDeNacimiento;
    String Celular;
    String Correo;
    String Nit;

    public Administrador(String documento, String nombre, String apellidos, LocalDate fechaDeNacimiento, String celular,
            String correo, String nit) {
        this.Documento = documento;
        this.nombre = nombre;
        this.Apellidos = apellidos;
        this.FechaDeNacimiento = fechaDeNacimiento;
        this.Celular = celular;
        this.Correo = correo;
        this.Nit = nit;
    }

    public Administrador() {
        
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public LocalDate getFechaDeNacimiento() {
        return FechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        FechaDeNacimiento = fechaDeNacimiento;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String nit) {
        Nit = nit;
    }

    
    
}
