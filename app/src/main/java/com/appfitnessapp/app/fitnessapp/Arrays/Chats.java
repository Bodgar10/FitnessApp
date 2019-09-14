package com.appfitnessapp.app.fitnessapp.Arrays;

public class Chats {

    private   String  id_usuario;

    private String senderid;

    private String text;

    private String id_admin;

    public Chats() {
    }

    public Chats(String id_usuario, String senderid, String text, String id_admin) {
        this.id_usuario = id_usuario;
        this.senderid = senderid;
        this.text = text;
        this.id_admin = id_admin;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getId_admin() {
        return id_admin;
    }

    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }
}
