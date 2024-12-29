package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

public class AccesoStruct extends Instruccion {

    private String id;
    private String atributoStruct;

    public AccesoStruct(String id, String atributoStruct, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.atributoStruct = atributoStruct;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Recupera la estructura
        Simbolo struct = tablaDeSimbolos.getVariable(id);
        // Valida que la variable sea una estructura
        if (struct == null) {
            semanticErrorManager.addError(new Errores("Semantico", "La variable " + id + " no está definida", this.linea, this.columna));
            return new Errores("Semantico", "La variable " + id + " no está definida", this.linea, this.columna);
        }

        // Valida que la variable sea una estructura
        if (struct.getTipo().getTipo() != tipoDato.STRUCT) {
            return new Errores("Semantico", "La variable " + id + " no es una estructura", this.linea, this.columna);
        }

        // Recuperar el valor especifico del campo
        Object valorCampo = struct.getValorCampo(atributoStruct);
        if (valorCampo == null) {
            semanticErrorManager.addError(new Errores("Semantico", "El campo " + atributoStruct + " no está definido en la estructura", this.linea, this.columna));
            return new Errores("Semantico", "El campo " + atributoStruct + " no está definido en la estructura", this.linea, this.columna);
        }

        if (valorCampo instanceof Simbolo) {
            Simbolo campoStruct = (Simbolo) valorCampo;
            this.tipo = campoStruct.getTipo();
            return campoStruct.getValor();
        }

        // Actuliza el tipo de la instruccion
        this.tipo = new Tipo(tipoDato.getType(valorCampo));
        // Retorna el valor del campo
        if (valorCampo instanceof Nativo) {
            return ((Nativo) valorCampo).getValor();
        }
        return valorCampo;
    }
}
