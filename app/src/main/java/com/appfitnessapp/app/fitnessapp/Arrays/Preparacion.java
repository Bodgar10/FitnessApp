package com.appfitnessapp.app.fitnessapp.Arrays;

public class Preparacion {

    public String nombre_paso;

    public String descripcion_paso;

    public String  id_preparacion;

    public Preparacion(String nombre_paso, String descripcion_paso, String id_preparacion) {
        this.nombre_paso = nombre_paso;
        this.descripcion_paso = descripcion_paso;
        this.id_preparacion = id_preparacion;
    }

    public Preparacion(){}

    public String getNombre_paso() {
        return nombre_paso;
    }

    public void setNombre_paso(String nombre_paso) {
        this.nombre_paso = nombre_paso;
    }

    public String getDescripcion_paso() {
        return descripcion_paso;
    }

    public void setDescripcion_paso(String descripcion_paso) {
        this.descripcion_paso = descripcion_paso;
    }

    public String getId_preparacion() {
        return id_preparacion;
    }

    public void setId_preparacion(String id_preparacion) {
        this.id_preparacion = id_preparacion;
    }
}
