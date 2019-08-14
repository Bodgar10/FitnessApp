package com.appfitnessapp.app.fitnessapp.Arrays;

public class EstadisticaEjercicio {


    public String id_usuario;
    public String fecha_cumplida;
    public String ejercicios_realizados;
    public String id_estadistica;

    public EstadisticaEjercicio(String id_usuario, String fecha_cumplida, String ejercicios_realizados, String id_estadistica) {
        this.id_usuario = id_usuario;
        this.fecha_cumplida = fecha_cumplida;
        this.ejercicios_realizados = ejercicios_realizados;
        this.id_estadistica = id_estadistica;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha_cumplida() {
        return fecha_cumplida;
    }

    public void setFecha_cumplida(String fecha_cumplida) {
        this.fecha_cumplida = fecha_cumplida;
    }

    public String getEjercicios_realizados() {
        return ejercicios_realizados;
    }

    public void setEjercicios_realizados(String ejercicios_realizados) {
        this.ejercicios_realizados = ejercicios_realizados;
    }

    public String getId_estadistica() {
        return id_estadistica;
    }

    public void setId_estadistica(String id_estadistica) {
        this.id_estadistica = id_estadistica;
    }
}
