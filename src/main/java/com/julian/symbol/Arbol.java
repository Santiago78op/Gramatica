package com.julian.symbol;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;

import java.util.LinkedList;

/**
 * Arbol -> Clase que representa el árbol de instrucciones.
 */
public class Arbol {

    // Constructor
    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private LinkedList<Errores> errores;
    // tabla de simbolos (global)

    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        consola = "";
        this.errores = new LinkedList<>();
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public String getConsola() {
        return consola;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public void Print(String valor) {
        this.consola += valor + "\n";
    }

    public void addError(Errores error){
        this.errores.add(error);
    }
}
