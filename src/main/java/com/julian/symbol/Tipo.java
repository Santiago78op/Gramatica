package com.julian.symbol;

/**
 * tipo -> Clase que representa el tipo de dato de una variable.
 */
public class Tipo {

    private tipoDato tipo;

    /**
     * Constructor de la clase Tipo.
     * @param tipo Tipo de dato.
     */
    public Tipo(tipoDato tipo) {
        this.tipo = tipo;
    }

    public tipoDato getTipo() {
        return tipo;
    }

    public void setTipo(tipoDato tipo) {
        this.tipo = tipo;
    }
}
