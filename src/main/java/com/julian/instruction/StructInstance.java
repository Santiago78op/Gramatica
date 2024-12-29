package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.util.HashMap;
import java.util.LinkedList;

public class StructInstance extends Instruccion {

    private String id;
    private String idStruct;
    private LinkedList<HashMap> valores;
    private int mutable;

    public StructInstance(String id, String idStruct, LinkedList<HashMap> valores, int mutable, int linea, int columna) {
        super(new Tipo(tipoDato.STRUCT), linea, columna);
        this.id = id;
        this.idStruct = idStruct;
        this.valores = valores;
        this.mutable = mutable;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Recupera la definición de la estructura
        Struct structDef = (Struct) arbol.getStruct(idStruct);
        if (structDef == null) {
            return new Errores("Semantico", "La estructura " + idStruct + " no está definida", this.linea, this.columna);
        }

        // Crea la instancia de la estructura
        Simbolo structInstance = new Simbolo(new Tipo(tipoDato.STRUCT), id, null, mutable == 1, "Externo", idStruct, this.linea, this.columna);

        // Set el valor de cada campo de la estructura
        for (HashMap<String, Object> campo : structDef.getLista()) {
            String nombreCampo = (String) campo.get("id");
            Tipo tipoCampo = (Tipo) campo.get("tipo");
            Object valorCampo = null;

            // Busca el valor del campo en la lista de valores
            for (HashMap<String, Object> valor : valores) {
                if (valor.get("id").equals(nombreCampo)) {
                    // valorCampo = ((Instruccion) valor.get("valor")).interpretar(arbol, tablaDeSimbolos);
                    valorCampo = valor.get("valor");
                    break;
                }
            }

            // Valida que el campo tenga un valor asignado
            if (valorCampo == null) {
                return new Errores("Semantico", "El campo " + nombreCampo + " no tiene un valor asignado", this.linea, this.columna);
            }

            // Como el valor no ti

            // Valida que el tipo del campo coincida con el valor asignado
            if (tipoCampo.getTipo() != tipoDato.getType(valorCampo)) {
                return new Errores("Semantico", "El tipo del campo " + nombreCampo + " no coincide con el valor asignado", this.linea, this.columna);
            }

            // Set the value in the struct instance
            structInstance.setValorCampo(nombreCampo, valorCampo);
        }

        // Add the struct instance to the symbol table
        if (!tablaDeSimbolos.setVariable(structInstance)) {
            return new Errores("Semantico", "La variable " + id + " ya existe en la tabla de símbolos", this.linea, this.columna);
        }

        return null;
    }
}
