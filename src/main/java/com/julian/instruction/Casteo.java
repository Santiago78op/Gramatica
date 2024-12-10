package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * Clase que ejecuta la instrucción de casteo de un tipo de dato a otro.
 * (valor as tipo)
 */
public class Casteo extends Instruccion {

    private Instruccion expresion;
    private Instruccion tipoCasteo;

    public Casteo(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        return null;
    }
}
