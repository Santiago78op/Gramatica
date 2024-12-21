package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Metodo -> Clase que representa un método.
 * Un método también es una subrutina de código que se
 * identifica un nombre y un conjunto de parámetros, aunque
 * a diferencia de las funciones estas subrutinas no deben
 * de retornar un valor.
 *
 * Ejemplo:
 * void <ID> ( <PARAMETROS> ) {
 *      <INSTRUCCIONES>
 * }
 */
public class Metodo extends Instruccion {

    private String id;
    private LinkedList<HashMap> parametros;
    private LinkedList<Instruccion> instrucciones;

    /**
     * Metodo -> Constructor de la clase.
     * @param tipo Tipo de dato que retorna el método.
     * @param id Identificador del método.
     * @param parametros Lista de parámetros del método.
     * @param instrucciones Lista de instrucciones del método.
     * @param linea Linea del método.
     * @param columna Columna del método.
     */
    public Metodo(Tipo tipo, String id, LinkedList<HashMap> parametros, LinkedList<Instruccion> instrucciones, int linea, int columna) {
        super(tipo, linea, columna);
        this.id = id;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    public String getId() {
        return id;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Interpretacion de una funcion
        for (var instruccion: this.instrucciones){
            if ( instruccion == null){
                continue;
            }
            var result = instruccion.interpretar(arbol, tablaDeSimbolos);
            // Recuperacion de errores
            if (result instanceof Errores) {
                arbol.addError((Errores) result);
            }
        }
        return null;
    }
}
