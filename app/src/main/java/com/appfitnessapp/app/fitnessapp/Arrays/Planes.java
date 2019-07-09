package com.appfitnessapp.app.fitnessapp.Arrays;

public class Planes {

    public String id_plan;
    public String meses_plan;
    public String descripcion_plan;
    public String costo_plan;
    public Boolean isVendid;
    public String nombre_plan;

    public Planes(String id_plan, String meses_plan, String descripcion_plan, String costo_plan, Boolean isVendid, String nombre_plan) {
        this.id_plan = id_plan;
        this.meses_plan = meses_plan;
        this.descripcion_plan = descripcion_plan;
        this.costo_plan = costo_plan;
        this.isVendid = isVendid;
        this.nombre_plan = nombre_plan;
    }

    public Planes(){}

    public String getId_plan() {
        return id_plan;
    }

    public void setId_plan(String id_plan) {
        this.id_plan = id_plan;
    }

    public String getMeses_plan() {
        return meses_plan;
    }

    public void setMeses_plan(String meses_plan) {
        this.meses_plan = meses_plan;
    }

    public String getDescripcion_plan() {
        return descripcion_plan;
    }

    public void setDescripcion_plan(String descripcion_plan) {
        this.descripcion_plan = descripcion_plan;
    }

    public String getCosto_plan() {
        return costo_plan;
    }

    public void setCosto_plan(String costo_plan) {
        this.costo_plan = costo_plan;
    }

    public Boolean getVendid() {
        return isVendid;
    }

    public void setVendid(Boolean vendid) {
        isVendid = vendid;
    }

    public String getNombre_plan() {
        return nombre_plan;
    }

    public void setNombre_plan(String nombre_plan) {
        this.nombre_plan = nombre_plan;
    }
}
