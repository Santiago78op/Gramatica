package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/*
    * Clase que representa una expresión de comparación de igualdad.
    * Valor1 == Valor2
*/
public class Igual extends Instruccion {

    private Instruccion expIzq;
    private Instruccion expDer;

    /**
     * Constructor de la clase Igual.
     * -> tipo Tipo de dato de la expresión.
     * @param linea Linea en la que se encuentra la expresión.
     * @param columna Columna en la que se encuentra la expresión.
     * @param expIzq Operando izquierdo de la expresión.
     * @param expDer Operando derecho de la expresión.
     */
    public Igual(Instruccion expIzq, Instruccion expDer, int linea, int columna) {
        super(new Tipo(tipoDato.BOOLEANO), linea, columna);
        this.expIzq = expIzq;
        this.expDer = expDer;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se interpreta el operando de la expresión.
        var valorIzq = expIzq.interpretar(arbol, tablaDeSimbolos);
        // Si el valor izquierdo es un error, se retorna.
        if(valorIzq instanceof Error) return valorIzq;

        var valorDer = expDer.interpretar(arbol, tablaDeSimbolos);
        if(valorDer instanceof Error) return valorDer;

        // Se obtiene el tipo de dato de los operandos.
        var tipoIzq = this.expIzq.tipo.getTipo();
        var tipoDer = this.expDer.tipo.getTipo();

        // Implementacion de tabla de operatorias para la potencia.
        switch (tipoIzq){
            case ENTERO -> {
                switch (tipoDer){
                    case ENTERO -> {
                        return (int)valorIzq == (int)valorDer;
                    }
                    case DECIMAL -> {
                        return (int)valorIzq == (double)valorDer;
                    }
                    case CARACTER -> {
                        char charValue = getCharValue(valorDer);
                        return (int)valorIzq == charValue;
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error en la igualdad, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipoDer){
                    case ENTERO -> {
                        return (double)valorIzq == (int)valorDer;
                    }
                    case DECIMAL -> {
                        return (double)valorIzq == (double)valorDer;
                    }
                    case CARACTER -> {
                        char charValue = getCharValue(valorDer);
                        return (double)valorIzq == charValue;
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error en el modulo, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipoDer){
                    case BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (boolean)valorIzq == (boolean)valorDer;
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error en la igualdad, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        char charValue = getCharValue(valorIzq);
                        return charValue == (int)valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        char charValue = getCharValue(valorIzq);
                        return charValue == (double)valorDer;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        char charValueIzq = getCharValue(valorIzq);
                        char charValueDer = getCharValue(valorDer);
                        return charValueIzq == charValueDer;
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error en la igualdad, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case CADENA -> {
                switch (tipoDer){
                    case CADENA -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return valorIzq.toString().equalsIgnoreCase(valorDer.toString());
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error en la igualdad, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            default -> {
                addSemanticError(tipoIzq, tipoDer, linea, columna);
                return new Errores("Semantico", "Error en el modulo, tipo de dato no valido", this.linea, this.columna);
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
        errorSemantico.addError(new Errores("Semantico", "Error de tipos en la operación Igualdad. " +
                "\n No se puede realizar la iguldad entre\n" + tipoIzq + " y " + tipoDer, linea, columna));
    }
}
