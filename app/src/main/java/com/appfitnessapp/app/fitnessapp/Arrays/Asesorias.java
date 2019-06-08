package com.appfitnessapp.app.fitnessapp.Arrays;

public class Asesorias {

    public String nombre_usuario;

    public String peso;

    public String objetivo;

    public String imagen;

    public Asesorias(String nombre_usuario, String peso, String objetivo, String imagen) {
        this.nombre_usuario = nombre_usuario;
        this.peso = peso;
        this.objetivo = objetivo;
        this.imagen = imagen;
    }


    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
