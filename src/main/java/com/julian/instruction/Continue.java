package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class Continue extends Instruccion {

    private Object instruccion;

    public Continue(Object instruccion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.instruccion = instruccion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        return this.instruccion;
    }
}
