package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Clase que ejecuta la instrucción push a una lista
 */
public class PushLista extends Instruccion {

    private String id;
    private Instruccion expresion;

    /**
     * Constructor de la clase
     * @param tipo Tipo de dato de la lista
     * @param linea Linea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     * @param id Identificador de la lista
     * @param expresion Expresión a agregar a la lista
     */
    public PushLista(String id, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.LISTA), linea, columna);
        this.id = id;
        this.expresion = expresion;
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

        Object valorExpresion = expresion.interpretar(arbol, tablaDeSimbolos);
        if (valorExpresion instanceof Errores) {
            return valorExpresion;
        }

        LinkedList<Object> lista = (LinkedList<Object>) simbolo.getValor();

        // Validamos el tipo de la lista con el tipo de la expresion
        if (simbolo.getTipoDato().toString() != expresion.getTipo().getTipo().toString()) {
            semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato de la lista " + id + " no es correcto", linea, columna));
            return new Errores("Semantico", "El tipo de dato de la lista " + id + " no es correcto", linea, columna);
        }

        // Agregar el valor a la lista
        lista.add(valorExpresion);

        return null;
    }
}
