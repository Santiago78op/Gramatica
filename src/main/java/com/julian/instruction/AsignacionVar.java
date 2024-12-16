package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * Esta clase se encarga de asignar un valor a una variable.
 * AsignacionVar -> id = expresion;
 * Ejemplo: numero = numero + 1;
 *          numero = 10;
 */
public class AsignacionVar extends Instruccion {

    private String id;
    private Instruccion expresion;

    /**
     * Constructor de la clase AsignacionVar.
     * @param id Nombre de la variable.
     * @param expresion Expresión a asignar a la variable.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     */
    public AsignacionVar(String id, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.expresion = expresion;
    }

    /**
     * Metodo que se encarga de asignar un valor a una variable.
     * @param arbol Arbol de instrucciones.
     * @param tablaDeSimbolos Tabla de simbolos.
     * @return Retorna el valor de la variable.
     */
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        /**
         * 1. Realizar la busqueda de la variable en la tabla de simbolos.
         * 2. Verificar si la variable existe en la tabla de simbolos.
         * 3. Valida que la variable no sea constante.
         * 4. Si es la variable no es constante procede a asignar el valor a la variable.
         * 5. Valida que la variable sea constante.
         * 6. Si es la variable constante procede a omitir la instruccion y retornar el error Semantico.
         */

        // Buscar la variable en la tabla de simbolos
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

        // Interpretar la expresion
        var valor = this.expresion.interpretar(arbol, tablaDeSimbolos);

        // Validar si la expresion es un error
        if (valor instanceof Errores) {
            return valor;
        }

        // tipos ->
        if (simbolo.getTipo().getTipo() != this.expresion.tipo.getTipo()) {
            return new Errores("SEMANTICO", "Tipos no compartibles",
                    this.linea, this.columna);
        }

        this.tipo.setTipo(simbolo.getTipo().getTipo());
        simbolo.setValor(valor);
        return null;
    }
}
