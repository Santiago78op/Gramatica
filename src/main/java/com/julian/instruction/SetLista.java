package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.util.LinkedList;

/**
 * Clase que se encarga de asignar un valor a una lista.
 * let miLista : List<int>;
 * miLista.push(1);
 * miLista.set(0,10);
 */
public class SetLista extends Instruccion {

    private String id;
    private Instruccion index;
    private Instruccion expresion;

    /**
     * Constructor de la clase
     * @param id Identificador de la lista
     * @param index Indice de la lista
     * @param expresion Expresion a asignar
     * @param linea Linea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     */
    public SetLista(String id, Instruccion index, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.LISTA), linea, columna);
        this.id = id;
        this.index = index;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Obtener la lista de la tabla de simbolos
        Simbolo simbolo = tablaDeSimbolos.getVariable(id);
        if (simbolo == null) {
            semanticErrorManager.addError(new Errores("Semantico", "La lista " + id + " no existe", linea, columna));
            return new Errores("Semantico", "La lista " + id + " no existe", linea, columna);
        }

        // Verificamos que el simbolo sea una lista
        if (!(simbolo.getValor() instanceof LinkedList)) {
            semanticErrorManager.addError(new Errores("Semantico", "El simbolo " + id + " no es una lista", linea, columna));
            return new Errores("Semantico", "El simbolo " + id + " no es una lista", linea, columna);
        }

        // Interpretar el índice
        Object valorIndice = index.interpretar(arbol, tablaDeSimbolos);
        if (valorIndice instanceof Errores) {
            return valorIndice;
        }

        // Verificar que el índice sea un entero
        if (!(valorIndice instanceof Integer)) {
            return new Errores("Semantico", "El índice debe ser un entero", linea, columna);
        }

        int index = (Integer) valorIndice;
        LinkedList<Object> lista = (LinkedList<Object>) simbolo.getValor();

        // Verificar que el índice esté dentro del rango
        if (index < 0 || index >= lista.size()) {
            return new Errores("Semantico", "Índice fuera de rango", linea, columna);
        }

        // Interpretar el valor a establecer
        Object nuevoValor = expresion.interpretar(arbol, tablaDeSimbolos);
        if (nuevoValor instanceof Errores) {
            return nuevoValor;
        }

        // Establecer el valor en el índice especificado
        lista.set(index, nuevoValor);

        return null;
    }
}
