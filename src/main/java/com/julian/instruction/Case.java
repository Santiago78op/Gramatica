package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

public class Case extends Instruccion {

    private Instruccion expresion;
    private LinkedList<Instruccion> instrucciones;

    /**
     * Constructor de la clase Case.
     * @param expresion Expresión a evaluar.
     * @param instrucciones Instrucciones a ejecutar.
     * @param linea Linea en la que se encuentra el case.
     * @param columna Columna en la que se encuentra el case.
     */
    public Case(Instruccion expresion, LinkedList<Instruccion> instrucciones, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se evalua la expresion del case
        var exp = this.expresion.interpretar(arbol, tablaDeSimbolos);
        // Si la expresion es un error se retorna el error.
        if(exp instanceof Errores){
            return exp;
        }

        return null;
    }
}
