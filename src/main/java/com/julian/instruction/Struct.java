package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.HashMap;

public class Struct extends Instruccion {

    // Identificador de la estructura
    private String id;
    // Lista de la estructura HashMap
    private HashMap<String, Instruccion> lista;

    /**
     * Constructor de la estructura
     * @param id Identificador de la estructura
     * @param lista Lista de la estructura
     * @param linea Linea del archivo
     * @param columna Columna del archivo
     */
    public Struct(String id, HashMap<String, Instruccion> lista, int linea, int columna) {
        super(new Tipo(tipoDato.STRUCT), linea, columna);
        this.id = id;
        this.lista = lista;
    }


    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Crear la estructura

        return null;
    }

    public String getId() {
        return id;
    }
}
