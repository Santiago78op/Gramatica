package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Esta clase se encarga de invertir una lista.
 * Ejemplo:
 *      reverse <ID>;
 *      // Ejemplo
 *      reverse miLista;
 */
public class ReverseLista extends Instruccion {

    private String id;

    /**
     * Constructor de la clase
     * @param id Identificador de la lista
     * @param linea Linea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     */
    public ReverseLista(String id, int linea, int columna) {
        super(new Tipo(tipoDato.LISTA), linea, columna);
        this.id = id;
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

        LinkedList<Object> lista = (LinkedList<Object>) simbolo.getValor();

        // Invertir la lista
        Collections.reverse(lista);

        return null;
    }
}
