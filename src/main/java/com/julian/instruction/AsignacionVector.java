package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.expresion.Nativo;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

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
    private Instruccion index_1;
    private Instruccion index_2;
    private Instruccion expresion;

    /**
     * Constructor de la clase AsignacionVector.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     * @param id Nombre del vector.
     * @param index_1 Indice 1 del vector.
     * @param expresion epresionm a asignar al vector.
     */
    public AsignacionVector(String id, Instruccion index_1, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.index_1 = index_1;
        this.expresion = expresion;
    }

    /**
     * Constructor de la clase AsignacionVector.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     * @param id Nombre del vector.
     * @param index_1 Indice 1 del vector.
     * @param index_2 Indice 2 del vector.
     * @param expresion epresion a asignar al vector.
     */
    public AsignacionVector(String id, Instruccion index_1, Instruccion index_2, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.index_1 = index_1;
        this.index_2 = index_2;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Buscamos el identificador en tabla de simbolos
        var simbolo = tablaDeSimbolos.getVariable(this.id);
        // Si la variable no existe, se retorna un error
        if (simbolo == null) {
            semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " no existe en la tabla de simbolos", this.linea, this.columna));
            return new Errores("Semantico", "La variable " + this.id + " no existe en la tabla de simbolos", this.linea, this.columna);
        }

        // Validar si la variable es constante
        if (simbolo.isConstante()) {
            semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " es constante y no puede ser modificada", this.linea, this.columna));
            return new Errores("Semantico", "La variable " + this.id + " es constante y no puede ser modificada", this.linea, this.columna);
        }

        // Validamos si es un Vector de 1 o 2 Dimenciones
        if(this.index_1 != null && this.index_2 == null) {
            // Interpretar la epresion
            var valor = this.index_1.interpretar(arbol, tablaDeSimbolos);
            // Validamos el Error
            if (valor instanceof Errores) return valor;
            // Validamos que el indice sea un entero
            if (!(valor instanceof Integer)) {
                return new Errores("Semántico", "El índice debe ser un entero", linea, columna);
            }

            // Interpretamos la expresion a asignar
            var valorExpresion = this.expresion.interpretar(arbol, tablaDeSimbolos);
            // Validamos el Error
            if (valorExpresion instanceof Errores) return valorExpresion;
            // Validamos que el tipo de dato sea el mismo
            if (simbolo.getTipo().getTipo() != this.expresion.tipo.getTipo()) {
                semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato en la expresion no coincide con el tipo del vector", this.linea, this.columna));
                return new Errores("Semantico", "El tipo de dato en la expresion no coincide con el tipo del vector", this.linea, this.columna);
            }

            // Actualizamos el tipo
            this.tipo.setTipo(simbolo.getTipo().getTipo());

            // Actulizamos el Valor en la posicion que indica el index_1
            if (simbolo.getValor() instanceof LinkedList) {
                LinkedList<Object> vec = (LinkedList<Object>) simbolo.getValor();
                int idx = (Integer) valor;
                if (idx < 0 || idx >= vec.size()) {
                    return new Errores("Semántico", "Índice fuera de rango", linea, columna);
                }

                // Accede al valor del vector, para cambiarlo por el nuevo dato
                vec.set(idx, valorExpresion);
                // Accede al valor del vector.
                var value = vec.get(idx);
                // Accede al valor del dato extraido en el vector.
                this.tipo.setTipo(tipoDato.getType(value));
                this.tipo.setTipo(simbolo.getTipo().getTipo());
                return null;
            }
        }

        semanticErrorManager.addError(new Errores("Semantico", "La asignacion " + this.id + "fallo", this.linea, this.columna));
        return new Errores("Semantico", "La asignacion " + this.id + " fallo", this.linea, this.columna);
    }
}
