package com.julian.symbol;

/**
 * tablaSimbolo -> Clase que representa una tabla de simbolos.
 */
public class tablaSimbolo {
    private String id;
    private String tipo;
    private String valor;
    private int linea;
    private int columna;

    public tablaSimbolo() {

    }

    @Override
    public String toString() {
        return "tablaSimbolo{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", valor='" + valor + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                '}';
    }
}
