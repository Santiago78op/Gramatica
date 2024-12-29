package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;

import java.util.HashMap;
import java.util.LinkedList;

public class Funcion extends Instruccion {

    private String id;
    private LinkedList<HashMap> parametros;
    private LinkedList<Instruccion> instruccions;

    /**
     * Funcion -> Constructor de la clase.
     * @param tipo Tipo de dato que retorna el método.
     * @param id Identificador del método.
     * @param parametros Lista de parámetros del método.
     * @param instruccions Lista de instrucciones del método.
     * @param linea Linea del método.
     * @param columna Columna del método.
     */
    public Funcion(Tipo tipo, String id, LinkedList<HashMap> parametros, LinkedList<Instruccion> instruccions, int linea, int columna) {
        super(tipo, linea, columna);
        this.id = id;
        this.parametros = parametros;
        this.instruccions = instruccions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<HashMap> getParametros() {
        return parametros;
    }

    public void setParametros(LinkedList<HashMap> parametros) {
        this.parametros = parametros;
    }

    public LinkedList<Instruccion> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(LinkedList<Instruccion> instruccions) {
        this.instruccions = instruccions;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Interpretacion de una funcion
        for (var instruccion: this.instruccions) {
            if (instruccion == null) {
                continue;
            }
            var result = instruccion.interpretar(arbol, tablaDeSimbolos);
            // Recuperacion de errores
            if (result instanceof Errores) {
                arbol.addError((Errores) result);
            }else if (result instanceof Return) {
                return result;
            }
            return result;
        }
        return null;
    }
}
