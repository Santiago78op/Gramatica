package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.expresion.Nativo;
import com.julian.symbol.*;

import java.util.LinkedList;

/**
 * Clase GetLista que devuelve un elemento de una lista en una posición específica.
 */
public class GetLista extends Instruccion {

    private String id;
    private Instruccion index;

    /**
     * Constructor de la clase GetLista.
     * @param id Identificador de la lista.
     * @param index Índice de la lista.
     * @param linea Línea en la que se encuentra.
     * @param columna Columna en la que se encuentra.
     */
    public GetLista(String id, Instruccion index, int linea, int columna) {
        super(new Tipo(tipoDato.LISTA), linea, columna);
        this.id = id;
        this.index = index;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Obtener la lista de la tabla de símbolos.
        Simbolo simbolo = tablaDeSimbolos.getVariable(id);
        if (simbolo == null) {
            semanticErrorManager.addError(new Errores("Semantico", "La lista " + id + " no existe", linea, columna));
            return new Errores("Semantico", "La lista " + id + " no existe", linea, columna);
        }

        // Verificar que el símbolo sea una lista
        if (!(simbolo.getValor() instanceof LinkedList)) {
            semanticErrorManager.addError(new Errores("Semantico", id + " no es una lista", linea, columna));
            return new Errores("Semantico", id + " no es una lista", linea, columna);
        }

        Object valorIndice = index.interpretar(arbol, tablaDeSimbolos);
        if (valorIndice instanceof Errores) {
            return valorIndice;
        }

        int index = (Integer) valorIndice;
        LinkedList<Object> lista = (LinkedList<Object>) simbolo.getValor();

        // Verificar que el índice esté dentro del rango
        if (index < 0 || index >= lista.size()) {
            semanticErrorManager.addError(new Errores("Semantico", "Índice fuera de rango", linea, columna));
            return new Errores("Semantico", "Índice fuera de rango", linea, columna);
        }

        var value = lista.get(index);
        // Actulizar el tipo de la variable
        this.tipo.setTipo(simbolo.getTipo().getTipo());
        // Se retorna el valor de la variable
        if (value instanceof Nativo) {
            return ((Nativo) value).getValor();
        } else {
            return value;
        }
    }
}
