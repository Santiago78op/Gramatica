package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

/**
 * La clase Vector es la encargada de almacenar los valores de un vector.
 * Ejemplo: [valor1, valor2, valor3, ...]
 * -> almacena lo que esta dentro de los corchetes, en una lista.
 */
public class Vector extends Instruccion {

    private LinkedList<Object> valores;

    public Vector(LinkedList<Object> valores ,int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.valores = valores;
    }

    public LinkedList<Object> getValores() {
        return valores;
    }

    public void setValores(LinkedList<Object> valores) {
        this.valores = valores;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        return this.valores;
    }

    public LinkedList<Object> getValues() {
        return valores;
    }
}
