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

    private String nombre;
    private int mutable;
    private Object valueExpresion;

    public VarStruct(Tipo tipo, int linea, int columna, String nombre, int mutable) {
        super(tipo, linea, columna);
        this.nombre = nombre;
        this.mutable = mutable;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se le asigna un valor a la variable dependiendo del tipo de dato.
        setExpresion(this.tipo);

        // Validamos la existencia de la variable en la tabla de simbolos y la agregamos
        Simbolo newVar = new Simbolo(this.tipo, this.nombre, valueExpresion, false, "Externo", "",this.linea, this.columna);
        if (tablaDeSimbolos.setVariable(newVar)) {
            // 0 no es constante, 1 es constante
            if (this.mutable == 0) {
                newVar.setConstante(false);
            } else {
                newVar.setConstante(true);
            }
            return null;
        }

        // Retornamos Error si la variable ya existe en la tabla de simbolos.
        semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.nombre + " ya existe en la tabla de simbolos", this.linea, this.columna));
        return new Errores("Semantico", "La variable " + this.nombre + " ya existe en la tabla de simbolos", this.linea, this.columna);
    }

    /**
     * La siguiente funcion setExpresion, tiene como funcion asignar una expresion a la variable.
     * ya que esta se declara sin expresion, entonces se le debe dar un por defecto dependioendo
     * del tipo que tenga su delaracion.
     *
     * Formato: -> id : tipo;
     *        :numero : int;
     */
    public void setExpresion(Tipo tipo) {
        switch (tipo.getTipo()){
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
