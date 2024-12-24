package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class Return extends Instruccion {

    private final Instruccion expression;

    public Return(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expression = expresion;
    }

    public Instruccion getExpression() {
        return expression;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        if (expression != null) {
            return this;
        }else {
            // Necesitamos retornar un Break para que el switch se detenga.
            Object instruccion = null;
            return new Break(instruccion,this.linea, this.columna);
        }
    }
}
