package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * Clase que representa una expresión de comparación de and.
 * Valor1 && Valor2 = 1 si es verdadero, 0 si es falso.
 */
public class And extends Instruccion {

    private Instruccion operIzq;
    private Instruccion operDer;

    /**
     * Constructor de la clase And.
     *  -> tipo Tipo de dato de la expresión.
     * @param linea Linea en la que se encuentra la expresión.
     * @param columna Columna en la que se encuentra la expresión.
     * @param operIzq Operando izquierdo de la expresión.
     * @param operDer Operando derecho de la expresión.
     * */
    public And(Instruccion operIzq, Instruccion operDer, int linea, int columna) {
        super(new Tipo(tipoDato.BOOLEANO), linea, columna);
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
            case BOOLEANO -> {
                switch (tipoDer) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return (boolean) valorIzq && (boolean) valorDer;
                    }
                    default -> {
                        semanticErrorManager errorSemantico = new semanticErrorManager();
                        errorSemantico.addError(new Errores("Semantico", "Error de tipos en la operación and. \n No se puede realizar and entre\n" +
                                "BOOLEANO y " + tipoDer, linea, columna));
                        return new Errores("Semantico", "Error de tipos en la operación and. \n No se puede realizar and entre\n" +
                                "BOOLEANO y " + tipoDer, linea, columna);
                    }
                }
            }
            default -> {
                semanticErrorManager errorSemantico = new semanticErrorManager();
                errorSemantico.addError(new Errores("Semantico", "Error de tipos en la operación and. \n No se puede realizar and entre\n" +
                        tipoIzq + " y " + tipoDer, linea, columna));
                return new Errores("Semantico", "Error de tipos en la operación and. \n No se puede realizar and entre\n" +
                        tipoIzq + " y " + tipoDer, linea, columna);
            }
        }
    }
}
