package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Simbolo;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;

/**
 * Esta clase almacena todos los campos declarados en una estructura.
 * Ejemplo:
 * struct Persona {
 *      int edad;      -> Campo a agregar => edad
 *      string nombre; -> Campo a agregar => nombre
 * }
 */
public class VarStruct extends Instruccion {

    // Nombre del campo
    private String nombre;
    // Tipo variable de dato del campo
    private int mutable;
    // Valor de la expresion
    private Object valueExpresion;

    public VarStruct(Tipo tipo, int linea, int columna, String nombre, int mutable) {
        super(tipo, linea, columna);
        this.nombre = nombre;
        this.mutable = mutable;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        setExpresion(this.tipo);

        Simbolo newVar = new Simbolo(this.tipo, this.nombre, valueExpresion, false, "Externo", "", this.linea, this.columna);
        if (tablaDeSimbolos.setVariable(newVar)) {
            newVar.setConstante(this.mutable == 1);
            return null;
        }

        semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.nombre + " ya existe en la tabla de simbolos", this.linea, this.columna));
        return new Errores("Semantico", "La variable " + this.nombre + " ya existe en la tabla de simbolos", this.linea, this.columna);
    }

    public void setExpresion(Tipo tipo) {
        switch (tipo.getTipo()) {
            case ENTERO:
                valueExpresion = 0;
                break;
            case DECIMAL:
                valueExpresion = 0.0;
                break;
            case CADENA:
                valueExpresion = "";
                break;
            case CARACTER:
                valueExpresion = '\u0000';
                break;
            case BOOLEANO:
                valueExpresion = true;
                break;
        }
    }
}
