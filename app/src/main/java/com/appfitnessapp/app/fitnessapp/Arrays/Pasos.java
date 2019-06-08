package com.appfitnessapp.app.fitnessapp.Arrays;

public class Pasos {

    public String paso;

    public String descripcion;


    public Pasos(String paso, String descripcion) {
        this.paso = paso;
        this.descripcion = descripcion;
    }

    public String getPaso() {
        return paso;
    }

    public void setPaso(String paso) {
        this.paso = paso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
