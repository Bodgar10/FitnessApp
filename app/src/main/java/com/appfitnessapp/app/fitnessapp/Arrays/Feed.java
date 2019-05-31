package com.appfitnessapp.app.fitnessapp.Arrays;

public class Feed {


    public String   tipo_feed;

    public Boolean   is_grati;

    public String   imagen_feed;

    public String   costo_pdf;

    public String   url_tipo;

    public String   imagen_admin;

    public String   nombre_admin;

    public String   timestamp;

    public String  descripcion;




    public Feed(String tipo_feed, Boolean is_grati, String imagen_feed, String costo_pdf, String url_tipo, String imagen_admin, String nombre_admin, String timestamp, String descripcion) {
        this.tipo_feed = tipo_feed;
        this.is_grati = is_grati;
        this.imagen_feed = imagen_feed;
        this.costo_pdf = costo_pdf;
        this.url_tipo = url_tipo;
        this.imagen_admin = imagen_admin;
        this.nombre_admin = nombre_admin;
        this.timestamp = timestamp;
        this.descripcion = descripcion;
    }

    public String getTipo_feed() {
        return tipo_feed;
    }

    public void setTipo_feed(String tipo_feed) {
        this.tipo_feed = tipo_feed;
    }

    public Boolean getIs_grati() {
        return is_grati;
    }

    public void setIs_grati(Boolean is_grati) {
        this.is_grati = is_grati;
    }

    public String getImagen_feed() {
        return imagen_feed;
    }

    public void setImagen_feed(String imagen_feed) {
        this.imagen_feed = imagen_feed;
    }

    public String getCosto_pdf() {
        return costo_pdf;
    }

    public void setCosto_pdf(String costo_pdf) {
        this.costo_pdf = costo_pdf;
    }

    public String getUrl_tipo() {
        return url_tipo;
    }

    public void setUrl_tipo(String url_tipo) {
        this.url_tipo = url_tipo;
    }

    public String getImagen_admin() {
        return imagen_admin;
    }

    public void setImagen_admin(String imagen_admin) {
        this.imagen_admin = imagen_admin;
    }

    public String getNombre_admin() {
        return nombre_admin;
    }

    public void setNombre_admin(String nombre_admin) {
        this.nombre_admin = nombre_admin;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
