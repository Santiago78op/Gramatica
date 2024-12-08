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

    public tablaSimbolo(String id, String tipo, String valor, int linea, int columna) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
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
