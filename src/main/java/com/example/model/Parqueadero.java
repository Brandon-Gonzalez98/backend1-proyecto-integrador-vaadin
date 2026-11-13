package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "parqueaderos")

public class Parqueadero {

    @Id
    /*@GeneratedValue(strategy = GenerationType.IDENTITY)*/
    String IdParqueadero;
    String Tipo;
    String IdPropietario;
    String IdResidente;
    String IdApto;   

    public Parqueadero(String IdParqueadero, String tipo, String IdPropietario, String IdResidente, String IdApto) {
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
    public String getIdParqueadero() {
        return IdParqueadero;
    }

    public void setIdParqueadero(String idParqueadero) {
        IdParqueadero = idParqueadero;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getIdPropietario() {
        return IdPropietario;
    }

    public void setIdPropietario(String idPropietario) {
        IdPropietario = idPropietario;
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
