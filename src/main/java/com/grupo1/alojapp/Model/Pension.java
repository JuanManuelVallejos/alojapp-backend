package com.grupo1.alojapp.Model;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Pension")
public class Pension  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TIPOPENSION tipopension;
    private Float precio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
