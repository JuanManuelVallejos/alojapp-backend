package com.grupo1.alojapp.DTOs;

import com.grupo1.alojapp.Model.TIPOALOJAMIENTO;

import javax.validation.constraints.Null;
import java.util.Collection;

public class AlojamientoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private TIPOALOJAMIENTO tipoalojamiento;
    private Float categoria;
    @Null
    private Boolean checked;
    private String justificacionRechazo;
    private UbicacionDTO ubicacion;
    private Collection<CloudFileDTO> referenceFiles;
    private Collection<PensionDTO> pensiones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TIPOALOJAMIENTO getTipoalojamiento() {
        return tipoalojamiento;
    }

    public void setTipoalojamiento(TIPOALOJAMIENTO tipoalojamiento) {
        this.tipoalojamiento = tipoalojamiento;
    }

    public Float getCategoria() {
        return categoria;
    }

    public void setCategoria(Float categoria) {
        this.categoria = categoria;
    }

    public UbicacionDTO getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionDTO ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Collection<CloudFileDTO> getReferenceFiles() {
        return referenceFiles;
    }

    public void setReferenceFiles(Collection<CloudFileDTO> referenceFiles) {
        this.referenceFiles = referenceFiles;
    }

    public String getJustificacionRechazo() {
        return justificacionRechazo;
    }

    public void setJustificacionRechazo(String justificacionRechazo) {
        this.justificacionRechazo = justificacionRechazo;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Collection<PensionDTO> getPensiones() {
        return pensiones;
    }

    public void setPensiones(Collection<PensionDTO> pensiones) {
        this.pensiones = pensiones;
    }
}
