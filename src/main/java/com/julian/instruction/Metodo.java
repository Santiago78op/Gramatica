package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Tipo;

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
    private LinkedList<Instruccion> parametros;
    private LinkedList<Instruccion> instrucciones;

    public Metodo(Tipo tipo, int linea, int columna, String id, LinkedList<Instruccion> instrucciones) {
        super(tipo, linea, columna);
        this.id = id;
        this.instrucciones = instrucciones;
    }


}
