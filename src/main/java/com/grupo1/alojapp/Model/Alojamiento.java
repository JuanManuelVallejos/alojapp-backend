package com.grupo1.alojapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "Alojamiento")
public class Alojamiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String descripcion;
    private TIPOALOJAMIENTO tipoalojamiento;
    private Float categoria;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Ubicacion ubicacion;

    @JoinTable(
            name = "alojamiento_files",
            joinColumns = @JoinColumn(name = "alojamiento_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="file_id", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<CloudFile> referenceFiles;

    @JoinTable(
            name = "alojamiento_pension",
            joinColumns = @JoinColumn(name = "alojamiento_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="pension_id", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Pension> pensiones;

    @NotNull
    private boolean eliminado;
    @Null
    private Boolean checked;
    private String justificacionRechazo;

    public Alojamiento(){
        eliminado = false;
    }

    public Alojamiento(String nombre, String descripcion){
        this.eliminado = false;
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
    }

    public Long getId(){ return this.id; }

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

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Collection<CloudFile> getReferenceFiles() {
        if(referenceFiles == null)
            referenceFiles = new ArrayList<>();
        return referenceFiles;
    }

    public void addReferenceFile(CloudFile cloudFile){
        referenceFiles = getReferenceFiles();
        referenceFiles.add(cloudFile);
    }

    public Collection<Pension> getPensiones() {
        if(pensiones == null)
            pensiones = new ArrayList<>();
        return pensiones;
    }

    public void addPension(Pension pension){
        pensiones = getPensiones();
        pensiones.add(pension);
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
}
