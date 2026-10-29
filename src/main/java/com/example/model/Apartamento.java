package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "apartamentos")

public class Apartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdApto;
    /*String IdApto;*/
    String NumeroApto;
    String Torre;
    String Parqueadero;
    String CuartoUtil;
    String IdDocumento;

    public Apartamento(Long IdApto, String numeroApto, String torre, String parqueadero, String cuartoUtil,
            String idDocumento) {
        this.IdApto = IdApto;
        this.NumeroApto = numeroApto;
        this.Torre = torre;
        this.Parqueadero = parqueadero;
        this.CuartoUtil = cuartoUtil;
        this.IdDocumento = idDocumento;
    }

    public Apartamento() {
    }

    public Long getIdApto() {
        return IdApto;
    }

    public void setIdApto(Long idApto) {
        IdApto = idApto;
    }

    public String getNumeroApto() {
        return NumeroApto;
    }

    public void setNumeroApto(String numeroApto) {
        NumeroApto = numeroApto;
    }

    public String getTorre() {
        return Torre;
    }

    public void setTorre(String torre) {
        Torre = torre;
    }

    public String getParqueadero() {
        return Parqueadero;
    }

    public void setParqueadero(String parqueadero) {
        Parqueadero = parqueadero;
    }

    public String getCuartoUtil() {
        return CuartoUtil;
    }

    public void setCuartoUtil(String cuartoUtil) {
        CuartoUtil = cuartoUtil;
    }

    public String getIdDocumento() {
        return IdDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        IdDocumento = idDocumento;
    }

    
}
