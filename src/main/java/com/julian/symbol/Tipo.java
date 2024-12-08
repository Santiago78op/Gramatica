package com.julian.symbol;

/**
 * tipo -> Clase que representa el tipo de dato de una variable.
 */
public class Tipo {

    private tipoDato tipoDato;

    public Tipo(com.julian.symbol.tipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

    public com.julian.symbol.tipoDato getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(com.julian.symbol.tipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }
}
