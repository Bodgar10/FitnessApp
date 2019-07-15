package com.appfitnessapp.app.fitnessapp.Arrays;

public class Valoraciones {


    public String nombre_usuario_valoracion;
    public String id_valoracion;
    public String descripcion_valoracion;
    public String fecha_valoracion;
    public String imagen_antes;
    public String imagen_despues;
    public String valoracion;
    public String nombre_usuario;
    public String foto_usuario;

    public Valoraciones(){}

    public Valoraciones(String nombre_usuario_valoracion, String id_valoracion, String descripcion_valoracion, String fecha_valoracion, String imagen_antes, String imagen_despues, String valoracion, String nombre_usuario, String foto_usuario) {
        this.nombre_usuario_valoracion = nombre_usuario_valoracion;
        this.id_valoracion = id_valoracion;
        this.descripcion_valoracion = descripcion_valoracion;
        this.fecha_valoracion = fecha_valoracion;
        this.imagen_antes = imagen_antes;
        this.imagen_despues = imagen_despues;
        this.valoracion = valoracion;
        this.nombre_usuario = nombre_usuario;
        this.foto_usuario = foto_usuario;
    }

    public String getNombre_usuario_valoracion() {
        return nombre_usuario_valoracion;
    }

    public void setNombre_usuario_valoracion(String nombre_usuario_valoracion) {
        this.nombre_usuario_valoracion = nombre_usuario_valoracion;
    }

    public String getId_valoracion() {
        return id_valoracion;
    }

    public void setId_valoracion(String id_valoracion) {
        this.id_valoracion = id_valoracion;
    }

    public String getDescripcion_valoracion() {
        return descripcion_valoracion;
    }

    public void setDescripcion_valoracion(String descripcion_valoracion) {
        this.descripcion_valoracion = descripcion_valoracion;
    }

    public String getFecha_valoracion() {
        return fecha_valoracion;
    }

    public void setFecha_valoracion(String fecha_valoracion) {
        this.fecha_valoracion = fecha_valoracion;
    }

    public String getImagen_antes() {
        return imagen_antes;
    }

    public void setImagen_antes(String imagen_antes) {
        this.imagen_antes = imagen_antes;
    }

    public String getImagen_despues() {
        return imagen_despues;
    }

    public void setImagen_despues(String imagen_despues) {
        this.imagen_despues = imagen_despues;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getFoto_usuario() {
        return foto_usuario;
    }

    public void setFoto_usuario(String foto_usuario) {
        this.foto_usuario = foto_usuario;
    }
}
