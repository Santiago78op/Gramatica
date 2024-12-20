package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.util.LinkedList;

/**
 * Clase que ejecuta la instrucción push a una lista
 */
public class PushLista extends Instruccion {

    private String id;
    private Instruccion expresion;

    /**
     * Constructor de la clase
     * @param id Identificador de la lista
     * @param expresion Expresión a agregar a la lista
     * @param linea Linea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     */
    public PushLista(String id, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.LISTA), linea, columna);
        this.id = id;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Obtener la lista de la tabla de simbolos
        Simbolo simbolo = tablaDeSimbolos.getVariable(id);
        if (simbolo == null){
            semanticErrorManager.addError(new Errores("Semántico", "La lista '" + id + "' no existe", linea, columna));
            return new Errores("Semántico", "La lista '" + id + "' no existe", linea, columna);
        }

        // Verificar que el símbolo sea una lista
        if (!(simbolo.getValor() instanceof LinkedList)) {
            return new Errores("Semantico", id + " no es una lista", linea, columna);
        }

        // Interpretar la expresión a agregar
        Object valor = expresion.interpretar(arbol, tablaDeSimbolos);
        if (valor instanceof Errores) {
            return valor;
        }

        // Agregar el valor a la lista
        LinkedList<Object> lista = (LinkedList<Object>) simbolo.getValor();
        lista.add(this.expresion);

        return null;
    }
}
