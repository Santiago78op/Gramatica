package com.julian.symbol;

import com.julian.abstracto.Instruccion;

import java.util.LinkedList;

/**
 * Arbol -> Clase que representa el árbol de instrucciones.
 */
public class Arbol {

    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private LinkedList<Error> errores;

    // Tabla de símbolos (Global)

    // Constructor
    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consola = "";
        this.errores = new LinkedList<>();
    }

    public void Print(String consola) {
        this.consola += consola + "\n";
    }
}
