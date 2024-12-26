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
    private Tipo tipoVector;

    /**
     * Constructor de la clase DefVector.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     * @param id Nombre del vector.
     * @param vector Valores del vector.
     * @param constante Si el vector es constante o no.
     */
    public DeclaracionVector(Tipo tipoVector, int linea, int columna, String id, Instruccion vector, int constante) {
        super(new Tipo(tipoDato.VECTOR), linea, columna);
        this.tipoVector = tipoVector;
        this.id = id;
        this.vector = vector;
        this.constante = constante;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se obtienen los valores del vector.
        Object result = this.vector.interpretar(arbol, tablaDeSimbolos);
        // Validamos Errores
        if(result instanceof Errores) return result;

        if(result instanceof LinkedList){
            LinkedList<Object> valores = (LinkedList<Object>) result;
            // Se valida que los valores del vector sean del tipo esperado.
            if(!validarTipos(valores, this.tipoVector)){
                semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato en el vector no coincide con el tipo del vector", this.linea, this.columna));
                return new Errores("Semantico", "El tipo de dato en el vector no coincide con el tipo del vector", this.linea, this.columna);
            }
        } else if (result instanceof MultiDimensionalVector) {
            LinkedList<LinkedList<Object>> valores = ((MultiDimensionalVector) result).getValores();
            for (LinkedList<Object> sublist : valores) {
                if (!validarTipos(sublist, this.tipoVector)) {
                    semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato en el vector no coincide con el tipo del vector", this.linea, this.columna));
                    return new Errores("Semantico", "El tipo de dato en el vector no coincide con el tipo del vector", this.linea, this.columna);
                }
            }
        } else {
            LinkedList<LinkedList<Object>> valores = ((MultiDimensionalVector) result).getValores();
            for (LinkedList<Object> sublist : valores) {
                if (!validarTipos(sublist, this.tipoVector)) {
                    semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato en el vector no coincide con el tipo del vector", this.linea, this.columna));
                    return new Errores("Semantico", "El tipo de dato en el vector no coincide con el tipo del vector", this.linea, this.columna);
                }
            }
        }

        Simbolo simbolo = new Simbolo(this.tipo, this.id, result, false, "Externo", "", this.linea, this.columna);
        if (tablaDeSimbolos.setVariable(simbolo)) {
            simbolo.setConstante(this.constante == 1);
            return null;
        }

        semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna));
        return new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna);
    }

    private boolean validarTipos(LinkedList<Object> valores, Tipo tipoEsperado) {
        for (Object valor : valores) {
            if (valor instanceof LinkedList) {
                if (!validarTipos((LinkedList<Object>) valor, tipoEsperado)) {
                    return false;
                }
            }else {
                if (tipoEsperado.getTipo() != tipoDato.getType(valor)) {
                    return false;
                }
            }
        }
        return true;
    }

}
