package com.appfitnessapp.app.fitnessapp.Arrays;

public class Ejercicios {

    private String nombre_ejercicio;
    private String rondas;
    private String repeticiones;
    private String video_ejercicio;
    private String id_ejercicio;

    public Ejercicios(String nombre_ejercicio, String rondas, String repeticiones, String video_ejercicio, String id_ejercicio) {
        this.nombre_ejercicio = nombre_ejercicio;
        this.rondas = rondas;
        this.repeticiones = repeticiones;
        this.video_ejercicio = video_ejercicio;
        this.id_ejercicio = id_ejercicio;
    }

    public Ejercicios(){}

    public String getNombre_ejercicio() {
        return nombre_ejercicio;
    }

    public void setNombre_ejercicio(String nombre_ejercicio) {
        this.nombre_ejercicio = nombre_ejercicio;
    }

    public String getRondas() {
        return rondas;
    }

    public void setRondas(String rondas) {
        this.rondas = rondas;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getVideo_ejercicio() {
        return video_ejercicio;
    }

    public void setVideo_ejercicio(String video_ejercicio) {
        this.video_ejercicio = video_ejercicio;
    }

    public String getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(String id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }
}
