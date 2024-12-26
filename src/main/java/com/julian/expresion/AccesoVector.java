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
            return new Errores("Semantico", "El vector " + this.id + " no existe", this.linea, this.columna);
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

            if (this.nestedAccess == null) {
                return ((LinkedList<?>) valor).get(idx);
            } else {
                var sublist = ((LinkedList<?>) valor).get(idx);
                if (!(sublist instanceof LinkedList)) {
                    return new Errores("Semantico", "El valor no es un vector multidimensional", this.linea, this.columna);
                }

                var index2Value = this.nestedAccess.interpretar(arbol, tablaDeSimbolos);
                if (index2Value instanceof Errores) return index2Value;
                if (!(index2Value instanceof Integer)) {
                    return new Errores("Semantico", "El segundo índice debe ser un entero", this.linea, this.columna);
                }

                int idx2 = (int) index2Value;
                if (idx2 < 0 || idx2 >= ((LinkedList<?>) sublist).size()) {
                    return new Errores("Semantico", "Índice fuera de rango", this.linea, this.columna);
                }

                return ((LinkedList<?>) sublist).get(idx2);
            }
        }

        return new Errores("Semantico", "El valor no es un vector", this.linea, this.columna);
    }
}
