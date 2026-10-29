package com.example.model;

public class Apartamento {

    String IdApto;
    String NumeroApto;
    String Torre;
    String Parqueadero;
    String CuartoUtil;
    String IdDocumento;

    public Apartamento(String idApto, String numeroApto, String torre, String parqueadero, String cuartoUtil,
            String idDocumento) {
        this.IdApto = idApto;
        this.NumeroApto = numeroApto;
        this.Torre = torre;
        this.Parqueadero = parqueadero;
        this.CuartoUtil = cuartoUtil;
        this.IdDocumento = idDocumento;
    }

    public Apartamento() {
    }

    public String getIdApto() {
        return IdApto;
    }

    public void setIdApto(String idApto) {
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
