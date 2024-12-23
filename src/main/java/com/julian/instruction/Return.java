package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class Return extends Instruccion {

    private Instruccion expresion;

    public Return(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        if (expresion != null) {
            Object resultado = expresion.interpretar(arbol, tablaDeSimbolos);
            if (resultado instanceof Errores) {
                return resultado;
            }
            return resultado;
        }
        return null;
    }
}
