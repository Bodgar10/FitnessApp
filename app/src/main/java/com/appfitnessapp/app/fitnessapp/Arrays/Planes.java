package com.appfitnessapp.app.fitnessapp.Arrays;

public class Planes {

    public String tipo_plan;

    public String oferta;

    public String meses;

    public String descripcion;


    public Planes(String tipo_plan, String oferta, String meses, String descripcion) {
        this.tipo_plan = tipo_plan;
        this.oferta = oferta;
        this.meses = meses;
        this.descripcion = descripcion;
    }

    public String getTipo_plan() {
        return tipo_plan;
    }

    public void setTipo_plan(String tipo_plan) {
        this.tipo_plan = tipo_plan;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getMeses() {
        return meses;
    }

    public void setMeses(String meses) {
        this.meses = meses;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
