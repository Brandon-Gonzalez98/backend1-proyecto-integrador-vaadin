package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "propiedades")

public class Propiedad {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Nit;
    String  NombrePropiedad;
    String Direccion;
    String Ciudad;
    String Departamento;
    String IdApto;

    public Propiedad(Long nit, String nombrePropiedad, String direccion, String ciudad, String departamento,
            String idApto) {
        this.Nit = nit;
        this.NombrePropiedad = nombrePropiedad;
        this.Direccion = direccion;
        this.Ciudad = ciudad;
        this.Departamento = departamento;
        this.IdApto = idApto;
    }

    public Propiedad() {
       
    }

    public long getNit() {
        return Nit;
    }

    public void setNit(Long nit) {
        Nit = nit;
    }

    public String getNombrePropiedad() {
        return NombrePropiedad;
    }

    public void setNombrePropiedad(String nombrePropiedad) {
        NombrePropiedad = nombrePropiedad;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }

    public String getIdApto() {
        return IdApto;
    }

    public void setIdApto(String idApto) {
        IdApto = idApto;
    }

    
}
