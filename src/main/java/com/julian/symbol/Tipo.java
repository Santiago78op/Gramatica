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

    public static Tipo validarTipo(String tipo){
        switch (tipo){
            case "ENTERO":
                return new Tipo(tipoDato.ENTERO);
            case "DECIMAL":
                return new Tipo(tipoDato.DECIMAL);
            case "BOOLEANO":
                return new Tipo(tipoDato.BOOLEANO);
            case "CARACTER":
                return new Tipo(tipoDato.CARACTER);
            case "CADENA":
                return new Tipo(tipoDato.CADENA);
            case "STRUCT":
                return new Tipo(tipoDato.STRUCT);
            case "VECTOR":
                return new Tipo(tipoDato.VECTOR);
            case "LISTA":
                return new Tipo(tipoDato.LISTA);
            default:
                return new Tipo(tipoDato.VOID);
        }
    }
}
