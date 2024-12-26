package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.util.List;

/**
 * Clase que representa una instrucción de longitud.
 * length(Expresion)
 * -> Si la expresion es una cadena, retorna la longitud de la cadena.
 * -> Si la expresion es un vector, retorna la cantidad de elementos del vector.
 * -> Si la expresion es una lista, retorna la cantidad de elementos de la lista.
 */
public class Length extends Instruccion {

    private final Instruccion expression;

    /**
     * Constructor de la clase Length.
     * @param expression Expresion a la que se le calculara la longitud.
     * @param linea Linea en la que se encuentra la expresion.
     * @param columna Columna en la que se encuentra la expresion.
     */
    public Length(Instruccion expression, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.expression = expression;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Interpretamos la expresion
        var valor = this.expression.interpretar(arbol, tablaDeSimbolos);
        // Si la expresion es un error se retorna el error.
        if (valor instanceof Errores) {
            return valor;
        }

        // Verificamos el tipo de la expresion
        switch (this.expression.tipo.getTipo()) {
            case CADENA:
                return ((String) valor).length();
            case VECTOR:
                // validar si es 1 dimencion o 2 dimenciones
                if (valor instanceof Vector) {
                    return ((Vector) valor).getValores().size();
                } else {
                    if(valor instanceof MultiDimensionalVector){
                        return ((MultiDimensionalVector) valor).getValores().size();
                    }
                    return ((List<?>) valor).size();
                }
            case LISTA:
                return ((List<?>) valor).size();
            default:
                semanticErrorManager.addError(new Errores("Semantico", "La funcion length solo se puede aplicar a cadenas, vectores o listas", this.linea, this.columna));
                return new Errores("Semantico", "La funcion length solo se puede aplicar a cadenas, vectores o listas", this.linea, this.columna);
        }
    }
}
