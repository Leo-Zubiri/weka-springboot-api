package com.zub.weka_springboot_api.models;

public class ContenidoInstancia {
    private int numInstancia;
    private Contenido contenido;

    public int getNumInstancia() {
        return numInstancia;
    }

    public void setNumInstancia(int numInstancia) {
        this.numInstancia = numInstancia;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }
}
