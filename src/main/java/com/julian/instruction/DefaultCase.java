package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

public class DefaultCase extends Instruccion {

    private LinkedList<Instruccion> instrucciones;

    /**
     * Constructor de la clase DefaultCase.
     * @param instrucciones Instrucciones a ejecutar.
     * @param linea Linea en la que se encuentra el default.
     * @param columna Columna en la que se encuentra el default.
     */
    public DefaultCase(LinkedList<Instruccion> instrucciones, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.instrucciones = instrucciones;
    }

    /**
     * Método que ejecuta las instrucciones del default.
     * @param arbol Árbol de instrucciones.
     * @param tablaDeSimbolos Tabla de símbolos.
     * @return null.
     */
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // tenemos un bloque de instrucciones para el default
        var nuevaTabla = new tablaSimbolo(tablaDeSimbolos);

        // Se ejecutan las instrucciones del default
        for (Instruccion instruccion : this.instrucciones) {
            var result = instruccion.interpretar(arbol, nuevaTabla);
            if (result instanceof Errores) {
                arbol.addError((Errores) result);
            }
        }
        return null;
    }
}
