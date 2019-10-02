package com.appfitnessapp.app.fitnessapp.Arrays;

public class Ingredientes {

    public String nombre_ingrediente;
    public String cantidad;
    public String id_ingrediente;
    public String descripcion_ingredientes;


    public Ingredientes(){}

    public Ingredientes(String nombre_ingrediente, String cantidad, String id_ingrediente, String descripcion_ingredientes) {
        this.nombre_ingrediente = nombre_ingrediente;
        this.cantidad = cantidad;
        this.id_ingrediente = id_ingrediente;
        this.descripcion_ingredientes = descripcion_ingredientes;
    }

    public String getNombre_ingrediente() {
        return nombre_ingrediente;
    }

    public void setNombre_ingrediente(String nombre_ingrediente) {
        this.nombre_ingrediente = nombre_ingrediente;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getId_ingrediente() {
        return id_ingrediente;
    }

    public void setId_ingrediente(String id_ingrediente) {
        this.id_ingrediente = id_ingrediente;
    }

    public String getDescripcion_ingredientes() {
        return descripcion_ingredientes;
    }

    public void setDescripcion_ingredientes(String descripcion_ingredientes) {
        this.descripcion_ingredientes = descripcion_ingredientes;
    }
}
