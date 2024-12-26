package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;

public class ToString extends Instruccion {

    private final Instruccion expression;

    public ToString(Tipo tipo, int linea, int columna, Instruccion expression) {
        super(tipo, linea, columna);
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
            case ENTERO:
            case DECIMAL:
            case BOOLEANO:
            case CARACTER:
                return valor.toString();
            case STRUCT:
                return valor.toString(); // Assuming the struct has a proper toString() method
            default:
                return new Errores("Semantico", "La funcion toString solo se puede aplicar a tipos numericos, caracter, booleano o struct", this.linea, this.columna);
        }
    }
}
