package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class Return extends Instruccion {

    private final Instruccion expression;
    public Object valorRetorno;

    /**
     * Constructor de la instruccion Return.
     * @param expresion Expresion a retornar.
     * @param linea Linea en la que se encuentra la instruccion.
     * @param columna Columna en la que se encuentra la instruccion.
     */
    public Return(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expression = expresion;
    }

    public Instruccion getExpression() {
        return expression;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        if (expression != null) {
            // Interpretar la expresion.
            var result = expression.interpretar(arbol, tablaDeSimbolos);
            // valorRetorno almacena el result interpretado.
            valorRetorno = result;
            // El tipo de la expresion es el tipo de la instruccion Return.
            this.tipo = expression.getTipo();
            if (result instanceof Error) return result;
            return this;
        }else {
            // Necesitamos retornar un Break para que el switch se detenga.
            Object instruccion = null;
            return new Break(instruccion,this.linea, this.columna);
        }
    }
}
