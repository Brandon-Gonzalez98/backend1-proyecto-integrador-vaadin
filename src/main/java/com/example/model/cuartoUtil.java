package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuartosutiles")

public class CuartoUtil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdCuartoUtil;
    String Area;
    String IdPropietario;
    String IdResidente;
    String IdApto;

    public CuartoUtil(Long IdCuartoUtil, String Area, String IdPropietario, String IdResidente, String IdApto) {
        this.IdCuartoUtil = IdCuartoUtil;
        this.Area = Area;
        this.IdPropietario = IdPropietario;
        this.IdResidente = IdResidente;
        this.IdApto = IdApto;
    }

    public CuartoUtil() {

    }

     // ===================
    // Getters y setters
    // ===================
    public Long getIdCuartoUtil() {
        return IdCuartoUtil;
    }

    public void setIdCuartoUtil(Long idCuartoUtil) {
        IdCuartoUtil = idCuartoUtil;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
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