package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/*
    * Clase que representa una expresión de potencia.
 */
public class Potencia extends Instruccion {

    private Instruccion operIzq;
    private Instruccion operDer;

    /**
     * Constructor de la clase Potencia.
     * -> tipo Tipo de dato de la expresión.
     * @param linea Linea en la que se encuentra la expresión.
     * @param columna Columna en la que se encuentra la expresión.
     * @param operIzq Operando izquierdo de la expresión.
     * @param operDer Operando derecho de la expresión.
     */
    public Potencia(Instruccion operIzq, Instruccion operDer, int linea, int columna) {
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
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)Math.pow((int)valorIzq, (int)valorDer);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((int)valorIzq, (double)valorDer);
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error en la potencia, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double)valorIzq, (int)valorDer);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double)valorIzq, (double)valorDer);
                    }
                    default -> {
                        addSemanticError(tipoIzq, tipoDer, linea, columna);
                        return new Errores("Semantico", "Error en la potencia, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            default -> {
                addSemanticError(tipoIzq, tipoDer, linea, columna);
                return new Errores("Semantico", "Error en la potencia, tipo de dato no valido", this.linea, this.columna);
            }
        }
    }

    // Metodo para agregar el error Semantico
    private void addSemanticError(tipoDato tipoIzq, tipoDato tipoDer, int linea, int columna) {
        semanticErrorManager errorSemantico = new semanticErrorManager();
        errorSemantico.addError(new Errores("Semantico", "Error de tipos en la operación Potencia. " +
                "\n No se puede realizar la Potencia entre\n" + tipoIzq + " y " + tipoDer, linea, columna));
    }
}
