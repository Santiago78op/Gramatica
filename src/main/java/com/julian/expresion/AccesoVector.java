package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.instruction.MultiDimensionalVector;
import com.julian.instruction.Vector;
import com.julian.symbol.*;

import java.util.LinkedList;


public class AccesoVector extends Instruccion {

    private final String id;
    // El index es para acceder a un vector.
    private final Instruccion index;
    // El nestedAccess es para acceder a un vector dentro de otro vector.
    private final Instruccion nestedAccess;

    /**
     * Constructor de la clase AccesoVector.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     * @param id Nombre del vector.
     * @param index Indice del vector.
     */
    public AccesoVector(String id, Instruccion index, int linea, int columna) {
        super(new Tipo(tipoDato.VECTOR), linea, columna);
        this.id = id;
        this.index = index;
        this.nestedAccess = null;
    }

    /**
     * Constructor de la clase AccesoVector.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     * @param id Nombre del vector.
     * @param index Indice del vector.
     * @param nestedAccess Acceso anidado al vector.
     */
    public AccesoVector(String id, Instruccion index, Instruccion nestedAccess, int linea, int columna) {
        super(new Tipo(tipoDato.VECTOR), linea, columna);
        this.id = id;
        this.index = index;
        this.nestedAccess = nestedAccess;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        var simbolo = tablaDeSimbolos.getVariable(this.id);
        if (simbolo == null) {
            return addSemanticError(this.id, this.linea, this.columna);
        }

        var valor = simbolo.getValor();
        if (valor instanceof Vector) {
            return interpretarVector(arbol, tablaDeSimbolos, (Vector) valor);
        } else if (valor instanceof MultiDimensionalVector) {
            return interpretarMultiDimensionalVector(arbol, tablaDeSimbolos, (MultiDimensionalVector) valor);
        }

        return addSemanticError(this.id, this.linea, this.columna);
    }

    private Object interpretarVector(Arbol arbol, tablaSimbolo tablaDeSimbolos, Vector vector) {
        var indice = this.index.interpretar(arbol, tablaDeSimbolos);
        if (indice instanceof Errores) {
            return indice;
        }

        if (!(indice instanceof Integer)) {
            return new Errores("Semantico", "El indice del vector debe ser de tipo entero", this.linea, this.columna);
        }

        var index = (int) indice;
        if (index < 0 || index >= vector.getValores().size()) {
            return new Errores("Semantico", "El indice del vector esta fuera de rango", this.linea, this.columna);
        }

        var valorVector = vector.getValores().get(index);
        if (valorVector instanceof Vector) {
            if (this.nestedAccess != null) {
                return this.nestedAccess.interpretar(arbol, tablaDeSimbolos);
            }
        } else {
            var valorCampo = ((Instruccion) valorVector).interpretar(arbol, tablaDeSimbolos);
            // Validar si la expresion es un error
            if (valorCampo instanceof Errores) {
                return valorCampo;
            }
            this.tipo.setTipo(tipoDato.getType(valorVector));
            return valorCampo;
        }
        return null;
    }

    private Object interpretarMultiDimensionalVector(Arbol arbol, tablaSimbolo tablaDeSimbolos, MultiDimensionalVector vector) {
        var indice = this.index.interpretar(arbol, tablaDeSimbolos);
        if (indice instanceof Errores) {
            return indice;
        }
        if (!(indice instanceof Integer)) {
            return new Errores("Semantico", "El indice del vector debe ser de tipo entero", this.linea, this.columna);
        }
        var index = (int) indice;
        if (index < 0 || index >= vector.getValores().size()) {
            return new Errores("Semantico", "El indice del vector esta fuera de rango", this.linea, this.columna);
        }
        var valorVector = vector.getValores().get(index);
        if (valorVector instanceof LinkedList) {
            // Acceso a la lista
            var lista = (LinkedList<Object>) valorVector;
            // Si en la lista hay un vector
            if (lista.get(0) instanceof Vector) {
                // Ya que el vector es de una sola dimension, se puede acceder directamente a los valores.
                var valor = lista.get(0);
                var nuevoValor = (Vector) valor;
                if (this.nestedAccess == null) {
                    return nuevoValor;
                }
                var nestedAccess2 = this.nestedAccess.interpretar(arbol, tablaDeSimbolos);
                if (nestedAccess2 instanceof Errores) {
                    return nestedAccess2;
                }
                if (!(nestedAccess2 instanceof Integer)) {
                    return new Errores("Semantico", "El indice del vector debe ser de tipo entero", this.linea, this.columna);
                }
                var index2 = (int) nestedAccess2;
                if (index2 < 0 || index2 >= nuevoValor.getValores().size()) {
                    return new Errores("Semantico", "El indice del vector esta fuera de rango", this.linea, this.columna);
                }
                var valorNuevo = nuevoValor.getValores().get(index2);
                var valorCampo = ((Instruccion) valorNuevo).interpretar(arbol, tablaDeSimbolos);
                // Validar si la expresion es un error
                if (valorCampo instanceof Errores) {
                    return valorCampo;
                }
                this.tipo.setTipo(tipoDato.getType(valorNuevo));
                return valorCampo;
            }
        }
        return null;
    }

    private Errores addSemanticError(String id, int linea, int columna) {
        Errores error = new Errores("Semantico", "La variable " + id + " no existe en la tabla de simbolos", linea, columna);
        semanticErrorManager.addError(error);
        return error;
    }
}
