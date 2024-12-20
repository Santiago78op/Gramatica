package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.util.LinkedList;

/**
 * Clase que define un vector.
 * La declaracion del vector se hace de la siguiente forma:
 *  -> mutabilidad id : tipo [] = [valor1, valor2, ...];
 *  -> let    numeros : int []  = [1, 2, 3, 4, 5];
 *  -> const  letras  : char [] = ['a', 'b', 'c', 'd', 'e'];
 */
public class DeclaracionVector extends Instruccion {

    private String id;
    private Instruccion vector;
    private int constante;

    /**
     * Constructor de la clase DefVector.
     * @param tipo Tipo de dato.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     * @param id Nombre del vector.
     * @param vector Valores del vector.
     * @param constante Si el vector es constante o no.
     */
    public DeclaracionVector(Tipo tipo, int linea, int columna, String id, Instruccion vector, int constante) {
        super(tipo, linea, columna);
        this.id = id;
        this.vector = vector;
        this.constante = constante;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Validamos el Vector recibido.
        Object result = this.vector.interpretar(arbol, tablaDeSimbolos);
        // Validamos los errores
        if (result instanceof Errores) return result;

        /**
         * Validamos que el tipo de dato en las listas de vectores, sean
         * del mismo tipo que el del arreglo declarado, sino error de
         * tipo semantico.
         */
        LinkedList<Object> valores = (LinkedList<Object>) result;
        if (!validarTipos(valores, this.tipo)) {
            // Retornamos Error si el tipo de dato no coincide.
            semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato en el vector no coincide con el tipo del vector", this.linea, this.columna));
            return new Errores("Semantico", "El tipo de dato en el vector no coincide con el tipo del vector", this.linea, this.columna);
        }

        // Agregar la variable a la tabla de simbolos
        Simbolo simbolo = new Simbolo(this.tipo, this.id, this.vector, false, "Externo", "",this.linea, this.columna);
        if (tablaDeSimbolos.setVariable(simbolo)) {
            // 0 no es constante, 1 es constante
            if (this.constante == 0) {
                simbolo.setConstante(false);
            } else {
                simbolo.setConstante(true);
            }
            return null;
        }
        // Retornamos Error si la variable ya existe en la tabla de simbolos.
        semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna));
        return new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna);
    }

    private boolean validarTipos(LinkedList<Object> valores, Tipo tipoEsperado) {
        for (Object valor : valores) {
            if (valor instanceof Vector) {
                if (!validarTipos(((Vector) valor).getValores(), tipoEsperado)) {
                    return false;
                }
            } else {
                if (tipoEsperado.getTipo() != tipoDato.getType(valor)) {
                    return false;
                }
            }
        }
        return true;
    }

}
