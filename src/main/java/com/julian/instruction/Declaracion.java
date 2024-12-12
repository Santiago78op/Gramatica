package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Simbolo;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;

/**
 * let / const -> id : int/double/string/...;
 * Mutabilidad -> id : tipo                 ;
 * o puede ser:
 * let / const -> id : tipo = expresion     ;
 * Mutabilidad -> id : tipo = expresion     ;
 *
 * La mutabilidad se refiere a la capacidad de cambiar el valor de la variable.
 * Se toma como un true o false. Si es true, la variable puede cambiar su valor.
 * Si es false, la variable no puede cambiar su valor.
 */
public class Declaracion extends Instruccion {

    private String id;
    private boolean constante;
    private Instruccion expresion;

    public Declaracion(boolean constante, String id, Tipo tipo, int linea, int columna,  Instruccion expresion) {
        super(tipo, linea, columna);
        this.id = id;
        this.constante = constante;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // valorInterpretado contiene el valor de la expresion
        var valorInterpretado = this.expresion.interpretar(arbol, tablaDeSimbolos);
        // Si la expresion es un error, se retorna el error
        if( valorInterpretado instanceof Errores ) {
            return valorInterpretado;
        }

        // Determinar si la variable es constante o no
        if (this.constante) {
            // Si es constante, se le asigna el valor y no se puede cambiar
            return tablaDeSimbolos.setVariable(new Simbolo(this.tipo, this.id, valorInterpretado, this.constante));
        }

        // Validamos el Tipo
        if (this.expresion.tipo.getTipo() != this.tipo.getTipo()) {
            semanticErrorManager.addError(new Errores("Semantico", "El tipo de la variable no coincide con el tipo de la expresion", this.linea, this.columna));
            return new Errores("Semantico", "El tipo de la variable no coincide con el tipo de la expresion", this.linea, this.columna);
        }

        // Validar la existencia de la variable y declararla la variable
        if (tablaDeSimbolos.setVariable(new Simbolo(this.tipo, this.id, valorInterpretado, this.constante))) {
            return null;
        } else {
            semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " ya existe en el ambito actual", this.linea, this.columna));
            return new Errores("Semantico", "La variable " + this.id + " ya existe en el ambito actual", this.linea, this.columna);
        }

    }
}
