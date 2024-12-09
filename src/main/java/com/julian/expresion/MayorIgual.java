package com.julian.expresion;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * Clase que representa una expresión de comparación de mayor o igual.
 * Valor1 >= Valor2 = 1 si es verdadero, 0 si es falso.
 */
public class MayorIgual extends Instruccion {

    private Instruccion operIzq;
    private Instruccion operDer;

    /**
     * Constructor de la clase MayorIgual.
     * @param linea Linea en la que se encuentra la expresión.
     * @param columna Columna en la que se encuentra la expresión.
     * @param operIzq Operando izquierdo de la expresión.
     * @param operDer Operando derecho de la expresión.
     * */
    public MayorIgual(Instruccion operIzq, Instruccion operDer, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.operIzq = operIzq;
        this.operDer = operDer;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se interpreta el operando izquierdo y derecho de la expresión.
        var valorIzq = operIzq.interpretar(arbol, tablaDeSimbolos);
        // Si el valor izquierdo es un error, se retorna.
        if (valorIzq instanceof Error) return valorIzq;

        var valorDer = operDer.interpretar(arbol, tablaDeSimbolos);
        if (valorDer instanceof Error) return valorDer;

        // Se obtiene el tipo de dato de los operandos.
        var tipoIzq = operIzq.tipo.getTipo();
        var tipoDer = operDer.tipo.getTipo();

        // Implementacion de tabla de operatorias para la potencia.
        switch (tipoIzq) {
            case ENTERO -> {
                switch (tipoDer) {
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (int) valorIzq >= (int) valorDer ? 1 : 0;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (int) valorIzq >= (double) valorDer ? 1 : 0;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (int) valorIzq >= (char) valorDer ? 1 : 0;
                    }
                    default -> {
                        return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipoDer) {
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (double) valorIzq >= (int) valorDer ? 1 : 0;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (double) valorIzq >= (double) valorDer ? 1 : 0;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (double) valorIzq >= (char) valorDer ? 1 : 0;
                    }
                    default -> {
                        return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipoDer) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return ((boolean) valorIzq ? 1 : 0) >= ((boolean) valorDer ? 1 : 0) ? 1 : 0;
                    }
                    default -> {
                        return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipoDer) {
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (char) valorIzq >= (int) valorDer ? 1 : 0;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (char) valorIzq >= (double) valorDer ? 1 : 0;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (char) valorIzq >= (char) valorDer ? 1 : 0;
                    }
                    default -> {
                        return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
                    }
                }
            }
            case CADENA -> {
                switch (tipoDer) {
                    case CADENA -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return valorIzq.toString().compareTo(valorDer.toString()) >= 0 ? 1 : 0;
                    }
                    default -> {
                        return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
                    }
                }
            }
            default -> {
                return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
            }
        }
    }
}
