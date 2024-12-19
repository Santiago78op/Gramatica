package com.julian.symbol;

import com.julian.expresion.Nativo;

/**
 * tipoDato -> Enumerador que contiene los tipos de datos que se pueden
 * utilizar en el lenguaje.
 */
public enum tipoDato {

    ENTERO,
    DECIMAL,
    BOOLEANO,
    CARACTER,
    CADENA,
    VOID,
    STRUCT,
    ERROR;

    public static tipoDato getType(Object valor) {
        if (valor instanceof Nativo) {
            if(((Nativo) valor).tipo.getTipo() == tipoDato.ENTERO) {
                return ENTERO;
            } else if(((Nativo) valor).tipo.getTipo() == tipoDato.DECIMAL) {
                return DECIMAL;
            } else if(((Nativo) valor).tipo.getTipo() == tipoDato.BOOLEANO) {
                return BOOLEANO;
            } else if(((Nativo) valor).tipo.getTipo() == tipoDato.CARACTER) {
                return CARACTER;
            } else if(((Nativo) valor).tipo.getTipo() == tipoDato.CADENA) {
                return CADENA;
            } else if (((Nativo) valor).tipo.getTipo() == tipoDato.STRUCT) {
                return STRUCT;
            }
        }
        return ERROR;
    }
}