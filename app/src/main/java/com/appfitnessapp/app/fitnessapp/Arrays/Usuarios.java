package com.appfitnessapp.app.fitnessapp.Arrays;

public class Usuarios {

    public String  nombre_usuario;

    public String  telefono_usuario;

    public String  email_usuario;

    public String  contrasena_usuario;

    public String  foto_usuario;

    public String  id_usuario;

    public String  token_usuario;

    public String  tipo_usuario;

    public String  peso_actual;

    public String  estatura;

    public String  objetivo;


    public Usuarios() {
    }

    public Usuarios(String nombre_usuario, String telefono_usuario, String email_usuario, String contrasena_usuario, String foto_usuario, String id_usuario, String token_usuario, String tipo_usuario, String peso_actual, String estatura, String objetivo) {
        this.nombre_usuario = nombre_usuario;
        this.telefono_usuario = telefono_usuario;
        this.email_usuario = email_usuario;
        this.contrasena_usuario = contrasena_usuario;
        this.foto_usuario = foto_usuario;
        this.id_usuario = id_usuario;
        this.token_usuario = token_usuario;
        this.tipo_usuario = tipo_usuario;
        this.peso_actual = peso_actual;
        this.estatura = estatura;
        this.objetivo = objetivo;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getTelefono_usuario() {
        return telefono_usuario;
    }

    public void setTelefono_usuario(String telefono_usuario) {
        this.telefono_usuario = telefono_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getContrasena_usuario() {
        return contrasena_usuario;
    }

    public void setContrasena_usuario(String contrasena_usuario) {
        this.contrasena_usuario = contrasena_usuario;
    }

    public String getFoto_usuario() {
        return foto_usuario;
    }

    public void setFoto_usuario(String foto_usuario) {
        this.foto_usuario = foto_usuario;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getToken_usuario() {
        return token_usuario;
    }

    public void setToken_usuario(String token_usuario) {
        this.token_usuario = token_usuario;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getPeso_actual() {
        return peso_actual;
    }

    public void setPeso_actual(String peso_actual) {
        this.peso_actual = peso_actual;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
}
