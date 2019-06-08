package com.appfitnessapp.app.fitnessapp.Arrays;

public class Ejercicios {

    public String tipo_ejercicio;

    public String repeticiones;

    public String rondas;

    public Ejercicios(String tipo_ejercicio, String repeticiones, String rondas) {
        this.tipo_ejercicio = tipo_ejercicio;
        this.repeticiones = repeticiones;
        this.rondas = rondas;
    }

    public String getTipo_ejercicio() {
        return tipo_ejercicio;
    }

    public void setTipo_ejercicio(String tipo_ejercicio) {
        this.tipo_ejercicio = tipo_ejercicio;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getRondas() {
        return rondas;
    }

    public void setRondas(String rondas) {
        this.rondas = rondas;
    }
}
