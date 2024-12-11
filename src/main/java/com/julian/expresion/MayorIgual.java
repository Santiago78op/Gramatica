package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
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
                        return (int) valorIzq >= (int) valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (int) valorIzq >= (double) valorDer;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        char charValue = getCharValue(valorDer);
                        return (int) valorIzq >= charValue;
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipoDer) {
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (double) valorIzq >= (int) valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (double) valorIzq >= (double) valorDer;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        char charValue = getCharValue(valorDer);
                        return (double) valorIzq >= charValue;
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipoDer) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return ((boolean) valorIzq ? 1 : 0) >= ((boolean) valorDer ? 1 : 0);
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipoDer) {
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        char charValue = getCharValue(valorIzq);
                        return charValue >= (int) valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        char charValue = getCharValue(valorIzq);
                        return charValue >= (double) valorDer;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        char charValueIzq = getCharValue(valorIzq);
                        char charValueDer = getCharValue(valorDer);
                        return charValueIzq >= charValueDer;
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
                    }
                }
            }
            case CADENA -> {
                switch (tipoDer) {
                    case CADENA -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return valorIzq.toString().compareToIgnoreCase(valorDer.toString()) >= 0;
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
                    }
                }
            }
            default -> {
                addSemanticError(tipoIzq, tipoDer, linea, columna);
                return new Errores("Semantico", "Error de tipos en la operación menor.", linea, columna);
            }
        }
    }

    private static char getCharValue(Object valorIzq) {
        char charValue;

        if (valorIzq instanceof String && ((String) valorIzq).length() == 1) {
            // Convierte un String de un solo carácter a un char
            charValue = ((String) valorIzq).charAt(0);
        } else if (valorIzq instanceof Character) {
            // Si ya es un Character
            charValue = (char) valorIzq;
        } else {
            throw new IllegalArgumentException("valorIzq debe ser un carácter o una cadena de un carácter");
        }
        return charValue;
    }

    // Metodo para agregar el error Semantico
    private void addSemanticError(tipoDato tipoIzq, tipoDato tipoDer, int linea, int columna) {
        semanticErrorManager errorSemantico = new semanticErrorManager();
        errorSemantico.addError(new Errores("Semantico", "Error de tipos en la operación Mayor o Igual. " +
                "\n No se puede realizar Mayor o Igual entre\n" + tipoIzq + " y " + tipoDer, linea, columna));
    }
}
