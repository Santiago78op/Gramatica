package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class AsignacionVar extends Instruccion {

    private String id;
    private Instruccion expresion;

    public AsignacionVar(String id, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        var variable = tablaDeSimbolos.getVariable(this.id);
        if (variable == null) {
            semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " no existe en la tabla de simbolos", this.linea, this.columna));
            return new Errores("Semantico", "La variable " + this.id + " no existe en la tabla de simbolos", this.linea, this.columna);
        }

        // Es una constante o no?
        if (variable.isConstante()) {
            semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " es una constante y no se puede modificar", this.linea, this.columna));
            Errores errores = new Errores("Semantico", "La variable " + this.id + " es una constante y no se puede modificar", this.linea, this.columna);
            arbol.Print(errores.toString());
            return new Errores("Semantico", "La variable " + this.id + " es una constante y no se puede modificar", this.linea, this.columna);
        } else {
            var newValue = this.expresion.interpretar(arbol, tablaDeSimbolos);
            if (newValue instanceof Errores) return newValue;

            if (variable.getTipo().getTipo() != this.expresion.tipo.getTipo()) {
                semanticErrorManager.addError(new Errores("Semantico", "El tipo de la variable no coincide con el tipo de la expresion", this.linea, this.columna));
                return new Errores("Semantico", "El tipo de la variable no coincide con el tipo de la expresion", this.linea, this.columna);
            }

            this.tipo.setTipo(variable.getTipo().getTipo());
            variable.setValor(newValue);
        }


        return null;
    }
}
