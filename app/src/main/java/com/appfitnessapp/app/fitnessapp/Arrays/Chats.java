package com.appfitnessapp.app.fitnessapp.Arrays;

public class Chats {

    private   String  id_servicio;

    private String senderid;

    private String text;

    public Chats() {
    }

    public Chats(String id_servicio, String senderid, String text) {
        this.id_servicio = id_servicio;
        this.senderid = senderid;
        this.text = text;
    }

    public String getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(String id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
