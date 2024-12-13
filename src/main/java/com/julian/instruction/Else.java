package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

public class Else extends Instruccion {

    private LinkedList<Instruccion> instruccionesElse;

    public Else(LinkedList<Instruccion> instruccionesElse, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.instruccionesElse = instruccionesElse;
    }


    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {

        // tenemos un bloque de instrucciones para el if y otro para el else
        var nuevaTabla = new tablaSimbolo(tablaDeSimbolos);

        // Si la condicion es falsa, se ejecutan las instrucciones del else
        for (Instruccion instruccion : instruccionesElse) {
            var result = instruccion.interpretar(arbol, nuevaTabla);
            if(result instanceof Errores){
                return result;
            }
        }
        return null;
    }
}
