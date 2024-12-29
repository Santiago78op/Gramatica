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

    private final Tipo tipo;
    private final String id;
    private Object expresion;

    public VarStruct(Tipo tipo, int linea, int columna, Tipo tipo1, String id, Object expresion) {
        super(tipo, linea, columna);
        this.tipo = tipo1;
        this.id = id;
        this.expresion = expresion;
    }

    @Override
    public Tipo getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public Object getExpresion() {
        return expresion;
    }

    public void setExpresion(Object expresion) {
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        return expresion;
    }
}
