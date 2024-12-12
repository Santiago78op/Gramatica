package com.julian.symbol;

public class Simbolo {

    private Tipo tipo;
    private String id;
    private Object valor;
    private boolean constante = false;

    public Simbolo(Tipo tipo, String id, Object valor, boolean constante) {
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
        this.constante = constante;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public boolean isConstante() {
        return constante;
    }

    public void setConstante(boolean constante) {
        this.constante = constante;
    }
}
