package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.expresion.Nativo;
import com.julian.expresion.Negacion;
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
        // Se obtine el valor del vector
        var valorVector = this.vector.interpretar(arbol, tablaDeSimbolos);
        // Se validan errores
        if (valorVector instanceof Errores) {
            return valorVector;
        }
        // Se valida si el vector es de una sola dimension
        if (valorVector instanceof Vector) {
            // Se obtiene el vector
            var vector = (Vector) valorVector;
            // Se recorren los elementos del vector para validar el tipo
            for (var valor : vector.getValores()) {
                // Se valida si el valor es de tipo correcto
                if (valor instanceof Errores) {
                    return valor;
                }

                // Interpretar el valor
                Object tipoDato = null;
                if (valor instanceof Nativo) {
                    tipoDato = ((Nativo) valor).getTipo().getTipo();
                } else if (valor instanceof Negacion) {
                    var negacion = ((Negacion) valor).getOper();
                    if (negacion instanceof Nativo) {
                        tipoDato = ((Nativo) negacion).getTipo().getTipo();
                    } else if (negacion instanceof Negacion) {
                        tipoDato = ((Negacion) negacion).getTipo().getTipo();
                    }
                }

                // Se valida si el valor es de tipo correcto
                if (tipoDato != this.tipoVector.getTipo()) {
                    semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato del vector no coincide con el tipo de dato declarado", this.linea, this.columna));
                    return new Errores("Semantico", "El tipo de dato del vector no coincide con el tipo de dato declarado", this.linea, this.columna);
                }

            }

            // Se actualiza el tipo del Vector
            vector.setTipo(this.tipoVector);

            Simbolo simbolo = new Simbolo(this.tipo, this.id, vector, false, "Externo", "", this.linea, this.columna);
            if (tablaDeSimbolos.setVariable(simbolo)) {
                simbolo.setConstante(this.constante == 1);
                return null;
            }
        }
        // Cuando el Vector es D2 dimenciones
        else if (valorVector instanceof MultiDimensionalVector) {
            // Se obtiene el vector en D2 dimensiones
            var vector = (MultiDimensionalVector) valorVector;
            // Se recorren los elementos del vector para validar el tipo
            for (var valor : vector.getValores()) {
                if (valor instanceof LinkedList) {
                    for (var valor2 : (LinkedList<Object>) valor) {
                        // Se obtiene el vector
                        var vector1 = (Vector) valor2;
                        // Se recorren los elementos del vector para validar el tipo
                        for (var valor3 : vector1.getValores()) {
                            // Se valida si el valor es de tipo correcto
                            if (valor3 instanceof Errores) {
                                return valor3;
                            }

                            // Interpretar el valor
                            Object tipoDato = null;
                            if (valor3 instanceof Nativo) {
                                tipoDato = ((Nativo) valor3).getTipo().getTipo();
                            } else if (valor3 instanceof Negacion) {
                                var negacion = ((Negacion) valor3).getOper();
                                if (negacion instanceof Nativo) {
                                    tipoDato = ((Nativo) negacion).getTipo().getTipo();
                                } else if (negacion instanceof Negacion) {
                                    tipoDato = ((Negacion) negacion).getTipo().getTipo();
                                }
                            }

                            // Se valida si el valor es de tipo correcto
                            if (tipoDato != this.tipoVector.getTipo()) {
                                semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato del vector no coincide con el tipo de dato declarado", this.linea, this.columna));
                                return new Errores("Semantico", "El tipo de dato del vector no coincide con el tipo de dato declarado", this.linea, this.columna);
                            }

                        }
                    }
                }

            }

            // Se actualiza el tipo del Vector
            vector.setTipo(this.tipoVector);

            Simbolo simbolo = new Simbolo(this.tipo, this.id, vector, false, "Externo", "", this.linea, this.columna);
            if (tablaDeSimbolos.setVariable(simbolo)) {
                simbolo.setConstante(this.constante == 1);
                return null;
            }
        }

        semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna));
        return new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna);
    }

}
