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
            return new Errores("Semantico", "El vector " + this.id + " no existe", this.linea, this.columna);
        }

        if (simbolo.isConstante()) {
            return new Errores("Semantico", "El vector " + this.id + " es constante y no se puede modificar", this.linea, this.columna);
        }

        var valor = simbolo.getValor();
        if (valor instanceof LinkedList) {
            var indexValue = this.index.interpretar(arbol, tablaDeSimbolos);
            if (indexValue instanceof Errores) return indexValue;
            if (!(indexValue instanceof Integer)) {
                return new Errores("Semantico", "El índice debe ser un entero", this.linea, this.columna);
            }

            int idx = (int) indexValue;
            if (idx < 0 || idx >= ((LinkedList<?>) valor).size()) {
                return new Errores("Semantico", "Índice fuera de rango", this.linea, this.columna);
            }

            if (this.nestedIndex == null) {
                var newValue = this.value.interpretar(arbol, tablaDeSimbolos);
                if (newValue instanceof Errores) return newValue;
                ((LinkedList<Object>) valor).set(idx, newValue);
            } else {
                var sublist = ((LinkedList<?>) valor).get(idx);
                if (!(sublist instanceof LinkedList)) {
                    return new Errores("Semantico", "El valor no es un vector multidimensional", this.linea, this.columna);
                }

                var index2Value = this.nestedIndex.interpretar(arbol, tablaDeSimbolos);
                if (index2Value instanceof Errores) return index2Value;
                if (!(index2Value instanceof Integer)) {
                    return new Errores("Semantico", "El segundo índice debe ser un entero", this.linea, this.columna);
                }

                int idx2 = (int) index2Value;
                if (idx2 < 0 || idx2 >= ((LinkedList<?>) sublist).size()) {
                    return new Errores("Semantico", "Índice fuera de rango", this.linea, this.columna);
                }

                var newValue = this.value.interpretar(arbol, tablaDeSimbolos);
                if (newValue instanceof Errores) return newValue;
                ((LinkedList<Object>) sublist).set(idx2, newValue);
            }
        }

        return null;
    }
}
