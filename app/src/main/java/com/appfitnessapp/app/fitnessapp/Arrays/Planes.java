package com.appfitnessapp.app.fitnessapp.Arrays;

public class Planes {

    public String id_plan;
    public String meses_plan;
    public String descripcion_plan;
    public String costo_plan;
    public Boolean isVendida;
    public String nombre_plan;
    public String admin;


    public Planes(String id_plan, String meses_plan, String descripcion_plan, String costo_plan, Boolean isVendida, String nombre_plan, String admin) {
        this.id_plan = id_plan;
        this.meses_plan = meses_plan;
        this.descripcion_plan = descripcion_plan;
        this.costo_plan = costo_plan;
        this.isVendida = isVendida;
        this.nombre_plan = nombre_plan;
        this.admin = admin;
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

    public Boolean getVendida() {
        return isVendida;
    }

    public void setVendida(Boolean vendida) {
        isVendida = vendida;
    }

    public String getNombre_plan() {
        return nombre_plan;
    }

    public void setNombre_plan(String nombre_plan) {
        this.nombre_plan = nombre_plan;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
