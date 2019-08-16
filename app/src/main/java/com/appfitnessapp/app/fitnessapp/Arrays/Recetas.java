package com.appfitnessapp.app.fitnessapp.Arrays;

public class Recetas {

    public String imagen_alimento;

    public String min_alimento;

    public String nombre_alimento;

    public String kilocalorias;

    public String porciones;

    public String fecha_alimento;

    public String tipo_alimento;

    public String id_alimento;


    public Recetas(){}

    public Recetas(String imagen_alimento, String min_alimento, String nombre_alimento, String kilocalorias, String porciones, String fecha_alimento, String tipo_alimento, String id_alimento) {
        this.imagen_alimento = imagen_alimento;
        this.min_alimento = min_alimento;
        this.nombre_alimento = nombre_alimento;
        this.kilocalorias = kilocalorias;
        this.porciones = porciones;
        this.fecha_alimento = fecha_alimento;
        this.tipo_alimento = tipo_alimento;
        this.id_alimento = id_alimento;
    }

    public String getImagen_alimento() {
        return imagen_alimento;
    }

    public void setImagen_alimento(String imagen_alimento) {
        this.imagen_alimento = imagen_alimento;
    }

    public String getMin_alimento() {
        return min_alimento;
    }

    public void setMin_alimento(String min_alimento) {
        this.min_alimento = min_alimento;
    }

    public String getNombre_alimento() {
        return nombre_alimento;
    }

    public void setNombre_alimento(String nombre_alimento) {
        this.nombre_alimento = nombre_alimento;
    }

    public String getKilocalorias() {
        return kilocalorias;
    }

    public void setKilocalorias(String kilocalorias) {
        this.kilocalorias = kilocalorias;
    }

    public String getPorciones() {
        return porciones;
    }

    public void setPorciones(String porciones) {
        this.porciones = porciones;
    }

    public String getFecha_alimento() {
        return fecha_alimento;
    }

    public void setFecha_alimento(String fecha_alimento) {
        this.fecha_alimento = fecha_alimento;
    }

    public String getTipo_alimento() {
        return tipo_alimento;
    }

    public void setTipo_alimento(String tipo_alimento) {
        this.tipo_alimento = tipo_alimento;
    }

    public String getId_alimento() {
        return id_alimento;
    }

    public void setId_alimento(String id_alimento) {
        this.id_alimento = id_alimento;
    }
}
