package com.appfitnessapp.app.fitnessapp.Arrays;

public class Comprado {

    public String   id_pdf;
    public String   url_pdf;
    public String  descripcion;
    public String  id_usuario;



    public Comprado(String id_pdf, String url_pdf, String descripcion, String id_usuario) {
        this.id_pdf = id_pdf;
        this.url_pdf = url_pdf;
        this.descripcion = descripcion;
        this.id_usuario = id_usuario;
    }

    public Comprado(){}


    public String getId_pdf() {
        return id_pdf;
    }

    public void setId_pdf(String id_pdf) {
        this.id_pdf = id_pdf;
    }

    public String getUrl_pdf() {
        return url_pdf;
    }

    public void setUrl_pdf(String url_pdf) {
        this.url_pdf = url_pdf;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
