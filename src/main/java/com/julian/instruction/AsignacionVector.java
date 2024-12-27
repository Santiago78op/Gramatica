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
            // Acceso a la lista
            var lista = (LinkedList<Object>) valorVector;
            // Verificar si hay un indice anidado
            if (lista.get(0) instanceof Vector) {
                if (this.nestedIndex != null) {
                    var nestedIndex = this.nestedIndex.interpretar(arbol, tablaDeSimbolos);
                    if (nestedIndex instanceof Errores) {
                        return nestedIndex;
                    }
                    if (!(nestedIndex instanceof Integer)) {
                        return new Errores("Semantico", "El indice del vector debe ser de tipo entero", this.linea, this.columna);
                    }
                    var nested = (int) nestedIndex;
                    if (nested < 0 || nested >= lista.size()) {
                        return new Errores("Semantico", "El indice del vector esta fuera de rango", this.linea, this.columna);
                    }
                    var valor = lista.get(0);
                    var nuevoValor = (Vector) valor;
                    var valorNuevo = nuevoValor.interpretar(arbol, tablaDeSimbolos);
                    if (valorNuevo instanceof Errores) {
                        return valorNuevo;
                    }

                    var valorAsignar = this.value.interpretar(arbol, tablaDeSimbolos);
                    if (valorAsignar instanceof Errores) {
                        return valorAsignar;
                    }
                    nuevoValor.getValores().set(nested, valorAsignar);
                    return null;
                }
                return new Errores("Semantico", "El valor no es un vector multidimensional", this.linea, this.columna);
            }
        }
        return new Errores("Semantico", "El valor no es un vector multidimensional", this.linea, this.columna);
    }

    private Errores addSemanticError(String id, int linea, int columna) {
        Errores error = new Errores("Semantico", "La variable " + id + " no existe en la tabla de simbolos", linea, columna);
        semanticErrorManager.addError(error);
        return error;
    }
}
