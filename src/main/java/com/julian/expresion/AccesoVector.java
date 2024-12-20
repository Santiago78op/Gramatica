package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.instruction.MultiDimensionalVector;
import com.julian.instruction.Vector;
import com.julian.symbol.*;

import java.util.LinkedList;

public class AccesoVector extends Instruccion {

    private String id;
    // El index es para acceder a un vector.
    private Instruccion index;
    // El nestedAccess es para acceder a un vector dentro de otro vector.
    private Instruccion nestedAccess;

    /**
     * Constructor de la clase AccesoVector.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     * @param id Nombre del vector.
     * @param index Indice del vector.
     */
    public AccesoVector(String id, Instruccion index, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.index = index;
    }

    public AccesoVector(Instruccion nestedAccess, Instruccion index, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.nestedAccess = nestedAccess;
        this.index = index;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // validamos si nestedAccess es nulo, nestedAccess es para acceder a un vector dentro de otro vector.
        Object vector;
        if (nestedAccess != null) {
            vector = nestedAccess.interpretar(arbol, tablaDeSimbolos);
        } else {
            vector = tablaDeSimbolos.getVariable(id);
            if (vector == null) {
                semanticErrorManager.addError(new Errores("Semántico", "Variable no encontrada", linea, columna));
                return new Errores("Semántico", "Variable no encontrada", linea, columna);
            }
            vector = ((Simbolo) vector).getValor();
        }

        if (vector instanceof Errores) return vector;

        Object indexValue = index.interpretar(arbol, tablaDeSimbolos);
        if (indexValue instanceof Errores) return indexValue;

        if (!(indexValue instanceof Integer)) {
            semanticErrorManager.addError(new Errores("Semántico", "El índice debe ser un entero", linea, columna));
            return new Errores("Semántico", "El índice debe ser un entero", linea, columna);
        }

        int idx = (Integer) indexValue;
        if (vector instanceof Vector) {
            Vector vec = (Vector) vector;
            if (idx < 0 || idx >= vec.getValues().size()) {
                semanticErrorManager.addError(new Errores("Semántico", "Índice fuera de rango", linea, columna));
                return new Errores("Semántico", "Índice fuera de rango", linea, columna);
            }
            // Accede al valor del vector.
            var value = vec.getValues().get(idx);
            // Accede al valor del dato extraido en el vector.
            this.tipo.setTipo(tipoDato.getType(value));
            if (value instanceof Nativo) {
                value = ((Nativo) value).getValor();
            }
            return value;
        } else if (vector instanceof LinkedList) {
            LinkedList<Object> vec = (LinkedList<Object>) vector;
            if (idx < 0 || idx >= vec.size()) {
                semanticErrorManager.addError(new Errores("Semántico", "Índice fuera de rango", linea, columna));
                return new Errores("Semántico", "Índice fuera de rango", linea, columna);
            }
            // Accede al valor del vector.
            var value = vec.get(idx);
            // Accede al valor del dato extraido en el vector.
            this.tipo.setTipo(tipoDato.getType(value));
            if (value instanceof Nativo) {
                value = ((Nativo) value).getValor();
            }
            return value;
        } else if (vector instanceof MultiDimensionalVector) {
            MultiDimensionalVector vec = (MultiDimensionalVector) vector;
            if (idx < 0 || idx >= vec.getValues().size()) {
                semanticErrorManager.addError(new Errores("Semántico", "Índice fuera de rango", linea, columna));
                return new Errores("Semántico", "Índice fuera de rango", linea, columna);
            }
            // Accede al valor del vector.
            var value = vec.getValues().get(idx);
            this.tipo.setTipo(tipoDato.getType(value));
            if (value instanceof Nativo) {
                value = ((Nativo) value).getValor();
            }
            return value;
        } else {
            semanticErrorManager.addError(new Errores("Semántico", "Acceso a un tipo no vector", linea, columna));
            return new Errores("Semántico", "Acceso a un tipo no vector", linea, columna);
        }
    }

    // Metodo para agregar el error Semantico
    private Errores addSemanticError(String id, int linea, int columna) {
        Errores error = new Errores("Semantico",
                "El vector " + id + " no existe en la tabla de simbolos", linea, columna);
        semanticErrorManager.addError(error);
        return error;
    }

}
