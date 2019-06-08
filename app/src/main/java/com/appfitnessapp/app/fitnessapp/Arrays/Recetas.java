package com.appfitnessapp.app.fitnessapp.Arrays;

public class Recetas {

    public String imagen_receta;

    public String tiempo_comida;

    public String nombre_comida;

    public Recetas(String imagen_receta, String tiempo_comida, String nombre_comida) {
        this.imagen_receta = imagen_receta;
        this.tiempo_comida = tiempo_comida;
        this.nombre_comida = nombre_comida;
    }


    public String getImagen_receta() {
        return imagen_receta;
    }

    public void setImagen_receta(String imagen_receta) {
        this.imagen_receta = imagen_receta;
    }

    public String getTiempo_comida() {
        return tiempo_comida;
    }

    public void setTiempo_comida(String tiempo_comida) {
        this.tiempo_comida = tiempo_comida;
    }

    public String getNombre_comida() {
        return nombre_comida;
    }

    public void setNombre_comida(String nombre_comida) {
        this.nombre_comida = nombre_comida;
    }
}
