package com.example.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "residentes")

public class Residente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdResidente;
    /*String Documento;*/
    String nombre;
    String Apellidos;
    LocalDate FechaDeNacimiento;
    String Celular;
    String Correo;
    
    public Residente(Long IdResidente, String nombre, String apellidos, LocalDate fechaDeNacimiento, String celular,
            String correo) {
        this.IdResidente = IdResidente;
        this.nombre = nombre;
        this.Apellidos = apellidos;
        this.FechaDeNacimiento = fechaDeNacimiento;
        this.Celular = celular;
        this.Correo = correo;
    }
    
    public Residente() {
        
    }

     // ===================
    // Getters y setters
    // ===================
    public Long getIdResidente() {
        return IdResidente;
    }

    public void setIdResidente(Long idResidente) {
        this.IdResidente = idResidente;
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
}

