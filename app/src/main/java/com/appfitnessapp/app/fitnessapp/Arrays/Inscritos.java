package com.appfitnessapp.app.fitnessapp.Arrays;

public class Inscritos {

    public String fecha_limite;

    public String id_inscrito;

    public Boolean id_pendiente;

    public String id_usuario;

    public String admin;




    public Inscritos(){}

    public Inscritos(String fecha_limite, String id_inscrito, Boolean id_pendiente, String id_usuario, String admin) {
        this.fecha_limite = fecha_limite;
        this.id_inscrito = id_inscrito;
        this.id_pendiente = id_pendiente;
        this.id_usuario = id_usuario;
        this.admin = admin;
    }


    public String getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(String fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public String getId_inscrito() {
        return id_inscrito;
    }

    public void setId_inscrito(String id_inscrito) {
        this.id_inscrito = id_inscrito;
    }

    public Boolean getId_pendiente() {
        return id_pendiente;
    }

    public void setId_pendiente(Boolean id_pendiente) {
        this.id_pendiente = id_pendiente;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
