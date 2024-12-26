package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.expresion.Nativo;
import com.julian.symbol.*;

import java.util.LinkedList;

/**
 * La asignacion de Vectores se hace de la siguiente forma:
 * -> id [expresion] = expresion;
 * -> id [expresion][expresion] = expresion;
 * -> numeros[0] = 10;
 * -> numeros[1][2] = 17;
 */
public class AsignacionVector extends Instruccion {

    private String id;
    private Instruccion index;
    private Instruccion nestedIndex;
    private Instruccion value;

    /**
     * Constructor de la clase
     * @param id Identificador del vector
     * @param index Indice del vector
     * @param value Valor a asignar
     * @param linea Linea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     */
    public AsignacionVector(String id, Instruccion index, Instruccion value, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.index = index;
        this.value = value;
    }

    /**
     * Constructor de la clase
     * @param id Identificador del vector
     * @param index Indice del vector
     * @param nestedIndex Indice anidado del vector
     * @param value Valor a asignar
     * @param linea Linea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     */
    public AsignacionVector(String id, Instruccion index, Instruccion nestedIndex, Instruccion value, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.index = index;
        this.nestedIndex = nestedIndex;
        this.value = value;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        Simbolo simbolo = tablaDeSimbolos.getVariable(id);
        if (simbolo == null) {
            return addSemanticError(this.id, this.linea, this.columna);
        }

        // Validar si la variable es constante
        if (simbolo.isConstante()) {
            semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " es constante y no puede ser modificada", this.linea, this.columna));
            return new Errores("Semantico", "La variable " + this.id + " es constante y no puede ser modificada", this.linea, this.columna);
        }

        // Validamanos si es un vector de 1 dimension
        if ( this.index != null && this.nestedIndex == null){
            // Interpretar el indice
            var ValorIndice = this.index.interpretar(arbol, tablaDeSimbolos);
            // Validamos el Error
            if (ValorIndice instanceof Errores) return ValorIndice;
            // Validamos que el indice sea un entero
            if (!(ValorIndice instanceof Integer)) {
                semanticErrorManager.addError(new Errores("Semántico", "El índice debe ser un entero", linea, columna));
                return new Errores("Semántico", "El índice debe ser un entero", linea, columna);
            }

            // Interpretamos el valor
            var ValorExpresion = this.value.interpretar(arbol, tablaDeSimbolos);
            // Validamos el Error
            if (ValorExpresion instanceof Errores) return ValorExpresion;
            // Validamos que el tipo de dato sea el mismo
            if (simbolo.getTipo().getTipo() != this.value.tipo.getTipo()) {
                semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato en la expresion no coincide con el tipo del vector", this.linea, this.columna));
                return new Errores("Semantico", "El tipo de dato en la expresion no coincide con el tipo del vector", this.linea, this.columna);
            }

            // Actualizamos el tipo
            this.tipo.setTipo(simbolo.getTipo().getTipo());

            int idx = (Integer) ValorIndice;
            // Actualizamos el valor
            var vector = simbolo.getValor();
            if (vector instanceof Vector) {
                Vector vec = (Vector) vector;
                if (idx < 0 || idx >= vec.getValues().size()) {
                    semanticErrorManager.addError(new Errores("Semántico", "Índice fuera de rango", linea, columna));
                    return new Errores("Semántico", "Índice fuera de rango", linea, columna);
                }
                // Actualizo el valor
                vec.getValues().set(idx, this.value);
                return null;
            }
            return null;

        }else{
            // Validamos si es un vector de 2 dimensiones
            // Interpretar el indice
            var ValorIndice = this.index.interpretar(arbol, tablaDeSimbolos);
            // Validamos el Error
            if (ValorIndice instanceof Errores) return ValorIndice;
            // Validamos que el indice sea un entero
            if (!(ValorIndice instanceof Integer)) {
                semanticErrorManager.addError(new Errores("Semántico", "El índice debe ser un entero", linea, columna));
                return new Errores("Semántico", "El índice debe ser un entero", linea, columna);
            }

            // Interpretar el indice anidado
            var ValorIndiceAnidado = this.nestedIndex.interpretar(arbol, tablaDeSimbolos);
            // Validamos el Error
            if (ValorIndiceAnidado instanceof Errores) return ValorIndiceAnidado;
            // Validamos que el indice anidado sea un entero
            if (!(ValorIndiceAnidado instanceof Integer)) {
                semanticErrorManager.addError(new Errores("Semántico", "El índice anidado debe ser un entero", linea, columna));
                return new Errores("Semántico", "El índice anidado debe ser un entero", linea, columna);
            }

            // Interpretamos el valor
            var ValorExpresion = this.value.interpretar(arbol, tablaDeSimbolos);
            // Validamos el Error
            if (ValorExpresion instanceof Errores) return ValorExpresion;
            // Validamos que el tipo de dato sea el mismo
            if (simbolo.getTipo().getTipo() != this.value.tipo.getTipo()) {
                semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato en la expresion no coincide con el tipo del vector", this.linea, this.columna));
                return new Errores("Semantico", "El tipo de dato en la expresion no coincide con el tipo del vector", this.linea, this.columna);
            }

            // Actualizamos el tipo
            this.tipo.setTipo(simbolo.getTipo().getTipo());

            int idx = (Integer) ValorIndice;
            int idy = (Integer) ValorIndiceAnidado;

            // Actualizamos el valor
            var vector = simbolo.getValor();
            if (vector instanceof MultiDimensionalVector) {
                MultiDimensionalVector vec = (MultiDimensionalVector) vector;
                if (idx < 0 || idx >= vec.getValues().size()) {
                    semanticErrorManager.addError(new Errores("Semántico", "Índice fuera de rango", linea, columna));
                    return new Errores("Semántico", "Índice fuera de rango", linea, columna);
                }

                // Acceder a la posicion de la fila con idx
                var value = vec.getValues().get(idx);
                // Actualizamos el valor
                if (value instanceof Vector) {
                    Vector vec2 = (Vector) value;
                    if (idy < 0 || idy >= vec2.getValues().size()) {
                        semanticErrorManager.addError(new Errores("Semántico", "Índice fuera de rango", linea, columna));
                        return new Errores("Semántico", "Índice fuera de rango", linea, columna);
                    }
                    // Actualizo el valor
                    vec2.getValues().set(idy, this.value);
                    return null;
                }
            }

            return null;
        }

    }

    private Errores addSemanticError(String id, int linea, int columna) {
        Errores error = new Errores("Semantico",
                "La variable " + id + " no existe en la tabla de simbolos", linea, columna);
        semanticErrorManager.addError(error);
        return error;
    }
}
