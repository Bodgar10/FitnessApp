package com.appfitnessapp.app.fitnessapp.Arrays;

public class Respuestas {


    public String id_respuesta;
    public String id_pregunta;
    public String respuesta;
    public String id_usuario;

    public Respuestas(String id_respuesta, String id_pregunta, String respuesta, String id_usuario) {
        this.id_respuesta = id_respuesta;
        this.id_pregunta = id_pregunta;
        this.respuesta = respuesta;
        this.id_usuario = id_usuario;
    }

    public Respuestas(){}

    public String getId_respuesta() {
        return id_respuesta;
    }

    public void setId_respuesta(String id_respuesta) {
        this.id_respuesta = id_respuesta;
    }

    public String getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(String id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }


    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
