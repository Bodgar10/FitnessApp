package com.appfitnessapp.app.fitnessapp.Arrays;

public class Preguntas {

    public String id_pregunta;
    public String pregunta;
    public String respuesta;


    public Preguntas(String id_pregunta, String pregunta, String respuesta) {
        this.id_pregunta = id_pregunta;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public String getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(String id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
