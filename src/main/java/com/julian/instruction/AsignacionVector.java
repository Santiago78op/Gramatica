package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;

public class AsignacionVector extends Instruccion {
    /**
     * Constructor de la clase Instruccion.
     * -> tipo Tipo de dato de la instrucción.
     *
     * @param tipo    Tipo de dato de la instrucción.
     * @param linea   Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     */
    public AsignacionVector(Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        return null;
    }
}
