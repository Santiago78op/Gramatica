package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.expresion.Nativo;
import com.julian.symbol.*;

import java.util.LinkedList;

public class RemoveLista extends Instruccion {

    private String id;
    private Instruccion index;

    /**
     * Constructor de la clase RemoveLista.
     * @param id Identificador de la lista.
     * @param index Indice de la lista.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     */
    public RemoveLista(String id, Instruccion index, int linea, int columna) {
        super(new Tipo(tipoDato.LISTA), linea, columna);
        this.id = id;
        this.index = index;
    }


    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Obtener la lista de la tabla de símbolos
        Simbolo simbolo = tablaDeSimbolos.getVariable(id);
        if (simbolo == null) {
            return new Errores("Semantico", "La lista " + id + " no existe", linea, columna);
        }

        // Verificar que el símbolo sea una lista
        if (!(simbolo.getValor() instanceof LinkedList)) {
            return new Errores("Semantico", id + " no es una lista", linea, columna);
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

        var value = lista.get(index);
        // Actulizar el tipo de la variable
        this.tipo.setTipo(simbolo.getTipo().getTipo());
        // Se remueve el valor de la lista
        lista.remove(index);
        // Se retorna el valor de la variable
        if (value instanceof Nativo) {
            return ((Nativo) value).getValor();
        } else {
            return value;
        }
    }
}
