package com.appfitnessapp.app.fitnessapp.Arrays;

public class ImagenesEjercicios {

    public String id;

    private String imagen_1;
    private String imagen_2;
    private String imagen_3;
    private String nombre;


    public ImagenesEjercicios(String id, String imagen_1, String imagen_2, String imagen_3, String nombre) {
        this.id = id;
        this.imagen_1 = imagen_1;
        this.imagen_2 = imagen_2;
        this.imagen_3 = imagen_3;
        this.nombre = nombre;
    }

    public ImagenesEjercicios(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen_1() {
        return imagen_1;
    }

    public void setImagen_1(String imagen_1) {
        this.imagen_1 = imagen_1;
    }

    public String getImagen_2() {
        return imagen_2;
    }

    public void setImagen_2(String imagen_2) {
        this.imagen_2 = imagen_2;
    }

    public String getImagen_3() {
        return imagen_3;
    }

    public void setImagen_3(String imagen_3) {
        this.imagen_3 = imagen_3;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
