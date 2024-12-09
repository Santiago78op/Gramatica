package com.julian.expresion;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * Clase que representa una expresión de resta.
 * Valor = Valor1 - Valor2
 */
public class Resta extends Instruccion {

    private Instruccion operIzq;
    private Instruccion operDer;

    /*
        * Constructor de la clase Resta.
        * -> tipo Tipo de dato de la expresión.
        * @param linea Linea en la que se encuentra la expresión.
        * @param columna Columna en la que se encuentra la expresión.
        * @param operIzq Operando izquierdo de la expresión.
        * @param operDer Operando derecho de la expresión.
     */
    public Resta(Instruccion operIzq, Instruccion operDer, int linea, int columna) {
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

        // Implementacion de tabla de operatorias para la resta.
        switch (tipoIzq){
            case ENTERO -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)valorIzq - (int)valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)valorIzq - (double)valorDer;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)valorIzq - (char)valorDer;
                    }
                    default -> {
                        return new Errores("Semantico", "Error en la resta, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)valorIzq - (int)valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)valorIzq - (double)valorDer;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)valorIzq - (char)valorDer;
                    }
                    default -> {
                        return new Errores("Semantico", "Error en la resta, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (char)valorIzq - (int)valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (char)valorIzq - (double)valorDer;
                    }
                    default -> {
                        return new Errores("Semantico", "Error en la resta, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("Semantico", "Error en la resta, tipo de dato no valido", this.linea, this.columna);
            }
        }
    }
}
