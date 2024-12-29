package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.HashMap;
import java.util.LinkedList;

public class Struct extends Instruccion {

    // Nombre de la estructura
    private final String nombre;
    // Campos de la estructura
    private final LinkedList<HashMap> lista;

    public Struct(String nombre, LinkedList<HashMap> lista, int linea, int columna) {
        super(new Tipo(tipoDato.STRUCT), linea, columna);
        this.nombre = nombre;
        this.lista = lista;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Crear la estructura
        for (HashMap<String, Object> campo : lista) {
            String nombreCampo = (String) campo.get("id");
            Tipo tipoCampo = (Tipo) campo.get("tipo");
            Object valorCampo = campo.get("vector");

            VarStruct varStruct = new VarStruct(tipoCampo, this.linea, this.columna, nombreCampo, 0);
            varStruct.setExpresion(tipoCampo);
            varStruct.interpretar(arbol, tablaDeSimbolos);
        }
        return null;
    }

    public String getId() {
        return nombre;
    }

    public LinkedList<HashMap> getLista() {
        return lista;
    }
}
