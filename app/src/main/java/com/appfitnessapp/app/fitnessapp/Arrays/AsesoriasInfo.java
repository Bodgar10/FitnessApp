package com.appfitnessapp.app.fitnessapp.Arrays;

public class AsesoriasInfo {


    public String id_asesoria;
    public String imagen_portada;
    public String descripcion_asesoria;
    public String costo_asesoria;
    public String video_explicativo;
    public String rutinas_imagen;
    public String rutinas_descripcion;
    public String alimentos_imagen;
    public String alimentos_descripcion;

    public AsesoriasInfo(String id_asesoria, String imagen_portada, String descripcion_asesoria, String costo_asesoria,
                         String video_explicativo, String rutinas_imagen, String rutinas_descripcion,
                         String alimentos_imagen, String alimentos_descripcion) {
        this.id_asesoria = id_asesoria;
        this.imagen_portada = imagen_portada;
        this.descripcion_asesoria = descripcion_asesoria;
        this.costo_asesoria = costo_asesoria;
        this.video_explicativo = video_explicativo;
        this.rutinas_imagen = rutinas_imagen;
        this.rutinas_descripcion = rutinas_descripcion;
        this.alimentos_imagen = alimentos_imagen;
        this.alimentos_descripcion = alimentos_descripcion;
    }

    public AsesoriasInfo(){}


    public String getId_asesoria() {
        return id_asesoria;
    }

    public void setId_asesoria(String id_asesoria) {
        this.id_asesoria = id_asesoria;
    }

    public String getImagen_portada() {
        return imagen_portada;
    }

    public void setImagen_portada(String imagen_portada) {
        this.imagen_portada = imagen_portada;
    }

    public String getDescripcion_asesoria() {
        return descripcion_asesoria;
    }

    public void setDescripcion_asesoria(String descripcion_asesoria) {
        this.descripcion_asesoria = descripcion_asesoria;
    }

    public String getCosto_asesoria() {
        return costo_asesoria;
    }

    public void setCosto_asesoria(String costo_asesoria) {
        this.costo_asesoria = costo_asesoria;
    }

    public String getVideo_explicativo() {
        return video_explicativo;
    }

    public void setVideo_explicativo(String video_explicativo) {
        this.video_explicativo = video_explicativo;
    }

    public String getRutinas_imagen() {
        return rutinas_imagen;
    }

    public void setRutinas_imagen(String rutinas_imagen) {
        this.rutinas_imagen = rutinas_imagen;
    }

    public String getRutinas_descripcion() {
        return rutinas_descripcion;
    }

    public void setRutinas_descripcion(String rutinas_descripcion) {
        this.rutinas_descripcion = rutinas_descripcion;
    }

    public String getAlimentos_imagen() {
        return alimentos_imagen;
    }

    public void setAlimentos_imagen(String alimentos_imagen) {
        this.alimentos_imagen = alimentos_imagen;
    }

    public String getAlimentos_descripcion() {
        return alimentos_descripcion;
    }

    public void setAlimentos_descripcion(String alimentos_descripcion) {
        this.alimentos_descripcion = alimentos_descripcion;
    }
}
