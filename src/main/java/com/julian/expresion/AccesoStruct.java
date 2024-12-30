package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.util.LinkedList;

public class AccesoStruct extends Instruccion {

    private String id;
    private String atributoStruct;
    private Instruccion structAcceso;

    public AccesoStruct(String id, String atributoStruct, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.atributoStruct = atributoStruct;
    }

    public AccesoStruct(Instruccion structAcceso, String atributoStruct, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.structAcceso = structAcceso;
        this.atributoStruct = atributoStruct;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        Simbolo struct;
        if (structAcceso == null) {
            struct = tablaDeSimbolos.getVariable(id);
        } else {
            Object resultado = structAcceso.interpretar(arbol, tablaDeSimbolos);
            if (resultado instanceof Simbolo) {
                struct = (Simbolo) resultado;
            } else {
                return new Errores("Semantico", "El acceso a la estructura no es válido", this.linea, this.columna);
            }
        }

        if (struct == null) {
            semanticErrorManager.addError(new Errores("Semantico", "La variable " + id + " no está definida", this.linea, this.columna));
            return new Errores("Semantico", "La variable " + id + " no está definida", this.linea, this.columna);
        }

        if (struct.getTipo().getTipo() != tipoDato.STRUCT) {
            return new Errores("Semantico", "La variable " + id + " no es una estructura", this.linea, this.columna);
        }

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

        this.tipo = new Tipo(tipoDato.getType(valorCampo));
        if (valorCampo instanceof Nativo) {
            return ((Nativo) valorCampo).getValor();
        }
        return valorCampo;
    }
}
