package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * Clase que ejecuta la instrucción de casteo de un tipo de dato a otro.
 * (valor as tipo)
 */
public class Casteo extends Instruccion {

    private Instruccion expresion;
    private Tipo tipoCasteo;

    /**
     * Constructor de la clase Casteo.
     * @param expresion Expresión a castear.
     * @param linea Linea en la que se encuentra el casteo.
     * @param columna Columna en la que se encuentra el casteo.
     */
    public Casteo(Instruccion expresion, Tipo tipoCasteo, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.tipoCasteo = tipoCasteo;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se interpreta la expresión a castear.
        var valor = expresion.interpretar(arbol, tablaDeSimbolos);
        // Si el valor es un error, se retorna.
        if (valor instanceof Errores) return valor;


        /**
         * Datos a Castear:
         * int a double -> cast(5 as double)
         * double a int -> cast(5.5 as int)
         * int a char -> cast(5 as char)
         * char a int -> cast('a' as int)
         * char a double -> cast('a' as double)
         */
        switch (tipoCasteo.getTipo()) {
            case tipoDato.ENTERO -> {
                switch (expresion.tipo.getTipo()) {
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) (double) valor;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        char charValueIzq = getCharValue(valor);
                        return (int) charValueIzq;
                    }
                    default -> {
                        return new Errores("Semantico", "Error de tipos en el casteo.", linea, columna);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (expresion.tipo.getTipo()) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) (int) valor;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        char charValue = getCharValue(valor);
                        return (double) charValue;
                    }
                    default -> {
                        return new Errores("Semantico", "Error de tipos en el casteo.", linea, columna);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (expresion.tipo.getTipo()) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (char) (int) valor;
                    }
                    default -> {
                        return new Errores("Semantico", "Error de tipos en el casteo.", linea, columna);
                    }
                }
            }
            default -> {
                return new Errores("Semantico", "Error de tipos en el casteo.", linea, columna);
            }
        }
    }

    private static char getCharValue(Object valorChar) {
        char charValue;

        if (valorChar instanceof String && ((String) valorChar).length() == 1) {
            // Convierte un String de un solo carácter a un char
            charValue = ((String) valorChar).charAt(0);
        } else if (valorChar instanceof Character) {
            // Si ya es un Character
            charValue = (char) valorChar;
        } else {
            throw new IllegalArgumentException("valorChar debe ser un carácter o una cadena de un carácter");
        }
        return charValue;
    }
}
