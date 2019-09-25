package com.grupo1.alojapp.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo1.alojapp.Model.TIPOPENSION;

public class PensionDTO {

    private long id;
    private TIPOPENSION tipopension;
    private Float precio;

    public long idalojamiento;
    
    public TIPOPENSION getTipopension() {
        return tipopension;
    }

    public void setTipopension(TIPOPENSION tipopension) {
        this.tipopension = tipopension;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
