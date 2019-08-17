package com.appfitnessapp.app.fitnessapp.Arrays;

public class PlanEntrenamiento {

    public String min_ejercicio;
    public String nivel_ejercicio;
    public String descripcion_ejercicios;
    public String num_ejercicios;
    public String id_plan_ejercicio;

    public PlanEntrenamiento(String min_ejercicio, String nivel_ejercicio, String descripcion_ejercicios, String num_ejercicios, String id_plan_ejercicio) {
        this.min_ejercicio = min_ejercicio;
        this.nivel_ejercicio = nivel_ejercicio;
        this.descripcion_ejercicios = descripcion_ejercicios;
        this.num_ejercicios = num_ejercicios;
        this.id_plan_ejercicio = id_plan_ejercicio;
    }

    public PlanEntrenamiento(){}

    public String getMin_ejercicio() {
        return min_ejercicio;
    }

    public void setMin_ejercicio(String min_ejercicio) {
        this.min_ejercicio = min_ejercicio;
    }

    public String getNivel_ejercicio() {
        return nivel_ejercicio;
    }

    public void setNivel_ejercicio(String nivel_ejercicio) {
        this.nivel_ejercicio = nivel_ejercicio;
    }

    public String getDescripcion_ejercicios() {
        return descripcion_ejercicios;
    }

    public void setDescripcion_ejercicios(String descripcion_ejercicios) {
        this.descripcion_ejercicios = descripcion_ejercicios;
    }

    public String getNum_ejercicios() {
        return num_ejercicios;
    }

    public void setNum_ejercicios(String num_ejercicios) {
        this.num_ejercicios = num_ejercicios;
    }

    public String getId_plan_ejercicio() {
        return id_plan_ejercicio;
    }

    public void setId_plan_ejercicio(String id_plan_ejercicio) {
        this.id_plan_ejercicio = id_plan_ejercicio;
    }
}
