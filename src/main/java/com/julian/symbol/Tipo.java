package com.julian.symbol;

/**
 * tipo -> Clase que representa el tipo de dato de una variable.
 */
public class Tipo {

    private tipoDato tipo;
    private String id;

    /**
     * Constructor de la clase Tipo.
     * @param tipo Tipo de dato.
     */
    public Tipo(tipoDato tipo) {
        this.tipo = tipo;
    }

    /**
     * Constructor de la clase Tipo para crear un nuevo tipo de dato.
     * @param tipo Tipo de dato.
     */
    public Tipo(tipoDato tipo, String id) {
        this.tipo = tipo;
        this.id = id;
    }

    public tipoDato getTipo() {
        return tipo;
    }

    public void setTipo(tipoDato tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return tipo.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
