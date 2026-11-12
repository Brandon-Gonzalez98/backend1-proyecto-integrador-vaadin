package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "parqueaderos")

public class Parqueadero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdParqueadero;
    String Tipo;
    String IdPropietario;
    String IdResidente;
    String IdApto;   

    public Parqueadero(Long IdParqueadero, String tipo, String IdPropietario, String IdResidente, String IdApto) {
        this.IdParqueadero = IdParqueadero;
        this.Tipo = tipo;
        this.IdPropietario = IdPropietario;
        this.IdResidente = IdResidente;
        this.IdApto = IdApto;
    }

    public Parqueadero() {
    }

     // ===================
    // Getters y setters
    // ===================
    public Long getIdParqueadero() {
        return IdParqueadero;
    }

    public void setIdParqueadero(Long idParqueadero) {
        IdParqueadero = idParqueadero;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getIdPopietario() {
        return IdPropietario;
    }

    public void setIdPopietario(String idPopietario) {
        IdPropietario = idPopietario;
    }

    public String getIdResidente() {
        return IdResidente;
    }

    public void setIdResidente(String idResidente) {
        IdResidente = idResidente;
    }

    public String getIdApto() {
        return IdApto;
    }

    public void setIdApto(String idApto) {
        IdApto = idApto;
    }

}
