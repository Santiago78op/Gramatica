package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;
import java.util.List;

public class MultiDimensionalVector extends Instruccion {

    private LinkedList<Object> valores;

    public MultiDimensionalVector(LinkedList<Object> valores, int linea, int columna) {
        super(new Tipo(tipoDato.VECTOR), linea, columna);
        this.valores = valores;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        return valores;
    }

    public LinkedList<Object> getValores() {
        return valores;
    }

    public List<Object> getValues() {
        return valores;
    }
}
