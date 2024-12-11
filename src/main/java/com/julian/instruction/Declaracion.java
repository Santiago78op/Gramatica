package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;

/**
 * let / const                id : tipo = valor;
 * Mutabilidad seguidad de un id : tipo = exper;
 */
public class Declaracion extends Instruccion {

    private String id;
    private Instruccion expr;

    public Declaracion(Tipo tipo, int linea, int columna, String id, Instruccion expr) {
        super(tipo, linea, columna);
        this.id = id;
        this.expr = expr;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        return null;
    }
}
