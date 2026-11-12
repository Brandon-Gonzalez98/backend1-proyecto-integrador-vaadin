package com.example.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "propietarios")

public class Propietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdPropietario;
    String  Nombre;
    String Apellido;
    LocalDate fechaNacimiento;
    String Celular;
    String Mail;
    String Reside;

    public Propietario(Long idPropietario, String nombre, String apellido, LocalDate fechaNacimiento, String celular, String mail, String reside) {
        this.IdPropietario = idPropietario;
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.Celular = celular;
        this.Mail = mail;
        this.Reside = reside;
    }

    public Propietario() {
    }

    public Long getIdPropietario() {
        return IdPropietario;
    }

    public void setIdPropietario(Long idPropietario) {
        IdPropietario = idPropietario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getReside() {
        return Reside;
    }

    public void setReside(String reside) {
        Reside = reside;
    }

     // ===================
    // Getters y setters
    // ===================  

    









}
