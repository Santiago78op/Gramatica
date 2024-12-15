package com.julian.tablaSimbolo;

public class TablaSimbolo{

    private String nombre;
    private String tipo;
    private String tipoDato;
    private String ambito;
    private String valor;
    private int linea;
    private int columna;

    public TablaSimbolo(String nombre, String tipo, String tipoDato, String ambito, String valor, int linea, int columna) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.tipoDato = tipoDato;
        this.ambito = ambito;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "TablaSimbolo{" +
                "nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tipoDato='" + tipoDato + '\'' +
                ", ambito='" + ambito + '\'' +
                ", valor='" + valor + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                '}';
    }
}
