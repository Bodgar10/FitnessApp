package com.appfitnessapp.app.fitnessapp.Arrays;

public class EstadisticaAlimentos {

    public String id_usuario;
    public String fecha_cumplida;
    public String tipo_alimento;


    public EstadisticaAlimentos(String id_usuario, String fecha_cumplida, String tipo_alimento) {
        this.id_usuario = id_usuario;
        this.fecha_cumplida = fecha_cumplida;
        this.tipo_alimento = tipo_alimento;
    }

    public EstadisticaAlimentos(){}

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha_cumplida() {
        return fecha_cumplida;
    }

    public void setFecha_cumplida(String fecha_cumplida) {
        this.fecha_cumplida = fecha_cumplida;
    }

    public String getTipo_alimento() {
        return tipo_alimento;
    }

    public void setTipo_alimento(String tipo_alimento) {
        this.tipo_alimento = tipo_alimento;
    }
}
