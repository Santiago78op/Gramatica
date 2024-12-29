package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

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
        // Atributos de la estructura
        Object nombreCampo = null;
        Object tipoCampo = null;
        Object valorCampo;

        // Crear la estructura
        for (HashMap<String, Object> campo : lista) {
            // Obtengo el nombre del campo
            nombreCampo = campo.get("id");
            // Obtengo el tipo del campo como Object
            tipoCampo = campo.get("tipo");
            // Si el tipo del campo es un Tipo
            if(tipoCampo instanceof Tipo) {
                // Si es un tipo nativo
                tipoCampo = (Tipo) campo.get("tipo");
            } else { // Si el tipo del campo es un ID
                // Busca la definición de la estructura
                // Recupera la estructura
                Simbolo nuevaStruct = tablaDeSimbolos.getVariable(tipoCampo.toString());
                if (nuevaStruct == null) {
                    return new Errores("Semantico", "La estructura " + tipoCampo + " no está definida", this.linea, this.columna);
                }
                tipoCampo = new Tipo(tipoDato.STRUCT, nuevaStruct.getId());
            }
        }
        // Crear el campo
        Simbolo campoStruct = new Simbolo(this.tipo, this.nombre, lista, false, "Externo", null, this.linea, this.columna);
        // Agregar el campo a la estructura
        tablaDeSimbolos.setVariable(campoStruct);
        return null;
    }

    public String getId() {
        return nombre;
    }

    public LinkedList<HashMap> getLista() {
        return lista;
    }
}
