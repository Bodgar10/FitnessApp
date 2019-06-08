package com.appfitnessapp.app.fitnessapp.Arrays;

public class Ingredientes {

    public String ingredientes;

    public String cantidad;

    public Ingredientes(String ingredientes, String cantidad) {
        this.ingredientes = ingredientes;
        this.cantidad = cantidad;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
