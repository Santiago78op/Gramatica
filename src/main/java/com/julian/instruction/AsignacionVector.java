package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.expresion.Nativo;
import com.julian.symbol.*;

import java.util.LinkedList;

/**
 * La asignacion de Vectores se hace de la siguiente forma:
 * -> id [expresion] = expresion;
 * -> id [expresion][expresion] = expresion;
 * -> numeros[0] = 10;
 * -> numeros[1][2] = 17;
 */
public class AsignacionVector extends Instruccion {

    private final String id;
    private final Instruccion index;
    private final Instruccion nestedIndex;
    private final Instruccion value;

    /**
     * Constructor de la clase
     * @param id Identificador del vector
     * @param index Indice del vector
     * @param value Valor a asignar
     * @param linea Línea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     */
    public AsignacionVector(String id, Instruccion index, Instruccion value, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.index = index;
        this.nestedIndex = null;
        this.value = value;
    }

    /**
     * Constructor de la clase
     * @param id Identificador del vector
     * @param index Indice del vector
     * @param nestedIndex Indice anidado del vector
     * @param value Valor a asignar
     * @param linea Linea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     */
    public AsignacionVector(String id, Instruccion index, Instruccion nestedIndex, Instruccion value, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.index = index;
        this.nestedIndex = nestedIndex;
        this.value = value;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        var simbolo = tablaDeSimbolos.getVariable(this.id);
        if (simbolo == null) {
            return addSemanticError(this.id, this.linea, this.columna);
        }

        var valor = simbolo.getValor();
        if (valor instanceof Vector) {
            return asignarValorVector(arbol, tablaDeSimbolos, (Vector) valor);
        } else if (valor instanceof MultiDimensionalVector) {
            return asignarValorMultiDimensionalVector(arbol, tablaDeSimbolos, (MultiDimensionalVector) valor);
        }

        return addSemanticError(this.id, this.linea, this.columna);
    }

    private Object asignarValorVector(Arbol arbol, tablaSimbolo tablaDeSimbolos, Vector vector) {
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
        var valorAsignar = this.value.interpretar(arbol, tablaDeSimbolos);
        if (valorAsignar instanceof Errores) {
            return valorAsignar;
        }
        vector.getValores().set(index, valorAsignar);
        return null;
    }

    private Object asignarValorMultiDimensionalVector(Arbol arbol, tablaSimbolo tablaDeSimbolos, MultiDimensionalVector vector) {
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
            var lista = (LinkedList<Object>) valorVector;
            var nestedIndice = this.nestedIndex.interpretar(arbol, tablaDeSimbolos);
            if (nestedIndice instanceof Errores) {
                return nestedIndice;
            }
            if (!(nestedIndice instanceof Integer)) {
                return new Errores("Semantico", "El indice del vector debe ser de tipo entero", this.linea, this.columna);
            }
            var nestedIndex = (int) nestedIndice;
            if (nestedIndex < 0 || nestedIndex >= lista.size()) {
                return new Errores("Semantico", "El indice del vector esta fuera de rango", this.linea, this.columna);
            }
            var valorAsignar = this.value.interpretar(arbol, tablaDeSimbolos);
            if (valorAsignar instanceof Errores) {
                return valorAsignar;
            }
            lista.set(nestedIndex, valorAsignar);
            return null;
        }
        return new Errores("Semantico", "El valor no es un vector multidimensional", this.linea, this.columna);
    }

    private Errores addSemanticError(String id, int linea, int columna) {
        Errores error = new Errores("Semantico", "La variable " + id + " no existe en la tabla de simbolos", linea, columna);
        semanticErrorManager.addError(error);
        return error;
    }
}
