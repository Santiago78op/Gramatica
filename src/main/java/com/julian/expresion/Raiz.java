package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/*
    * Clase que representa una expresión de raiz.
    * Valor = operIzq ^ (1/operDer)
 */
public class Raiz extends Instruccion {

        private Instruccion operIzq;
        private Instruccion operDer;

    /**
     *  Constructor de la clase Raiz.
     *  -> tipo Tipo de dato de la expresión.
     *  @param linea Linea en la que se encuentra la expresión.
     *  @param columna Columna en la que se encuentra la expresión.
     *  @param operIzq Operando izquierdo de la expresión.
     *  @param operDer Operando derecho de la expresión.
     */
    public Raiz(Instruccion operIzq, Instruccion operDer, int linea, int columna) {
            super(new Tipo(tipoDato.VOID), linea, columna);
            this.operIzq = operIzq;
            this.operDer = operDer;
        }


    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        var valorIzq = operIzq.interpretar(arbol, tablaDeSimbolos);
        // Si el valor izquierdo es un error, se retorna.
        if(valorIzq instanceof Error) return valorIzq;

        var valorDer = operDer.interpretar(arbol, tablaDeSimbolos);
        if(valorDer instanceof Error) return valorDer;

        // Se obtiene el tipo de dato de los operandos.
        var tipoIzq = operIzq.tipo.getTipo();
        var tipoDer = operDer.tipo.getTipo();

        // Implementacion de tabla de operatorias para la potencia.
        switch (tipoIzq){
            case ENTERO -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((int)valorIzq, (double) 1 /(int)valorDer);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((int)valorIzq, 1/(double)valorDer);
                    }
                    default -> {
                        return addSemanticError(tipoIzq, tipoDer, this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double)valorIzq, (double) 1 /(int)valorDer);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double)valorIzq, 1/(double)valorDer);
                    }
                    default -> {
                        return addSemanticError(tipoIzq, tipoDer, this.linea, this.columna);
                    }
                }
            }
            default -> {
                return addSemanticError(tipoIzq, tipoDer, this.linea, this.columna);
            }
        }
    }

    // Metodo para agregar el error Semantico
    private Errores addSemanticError(tipoDato tipoIzq, tipoDato tipoDer, int linea, int columna) {
        Errores error = new Errores("Semantico", "Error de tipos en la operación Raiz. " +
                "\n No se puede realizar la Raiz entre\n" + tipoIzq + " y " + tipoDer, linea, columna);
        semanticErrorManager.addError(error);
        return error;
    }
}
