package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * El acceso se da unicamente cuando venga el indentificador de la variable.
 * Ejemplo: let edad:int = (10 + 10) - 5;
 */
public class AccesoVar extends Instruccion {

    private String id;

    public AccesoVar(String id, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Buscar la variable en la tabla de simbolos
        var simbolo = tablaDeSimbolos.getVariable(this.id);
        // Si la variable no existe, se retorna un error
        if (simbolo == null) {
            semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " no existe en la tabla de simbolos", this.linea, this.columna));
            return new Errores("Semantico", "La variable " + this.id + " no existe en la tabla de simbolos", this.linea, this.columna);
        }

        // Actulizar el tipo de la variable
        this.tipo.setTipo(simbolo.getTipo().getTipo());

        // Se retorna el valor de la variable
        return simbolo.getValor();
    }
}
