package com.example.model;

public class Propiedad {
    String Nit;
    String  NombrePropiedad;
    String Direccion;
    String Ciudad;
    String Departamento;
    String IdApto;

    public Propiedad(String nit, String nombrePropiedad, String direccion, String ciudad, String departamento,
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

    public String getNit() {
        return Nit;
    }

    public void setNit(String nit) {
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
