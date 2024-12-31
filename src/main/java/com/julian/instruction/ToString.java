package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class ToString extends Instruccion {

    private final Instruccion expression;

    public ToString(Instruccion expression, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
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

        if (this.expression.tipo.getTipo() == tipoDato.STRUCT)

        // Verificamos el tipo de la expresion
        switch (this.expression.tipo.getTipo()) {
            case ENTERO:
            case DECIMAL:
            case BOOLEANO:
            case CARACTER:
                // Actulizar el tipo de la variable
                this.tipo.setTipo(tipoDato.CADENA);
                return valor.toString();
            case STRUCT:
                this.tipo.setTipo(tipoDato.CADENA);
                return valor.toString(); // Assuming the struct has a proper toString() method
            default:
                return new Errores("Semantico", "La funcion toString solo se puede aplicar a tipos numericos, caracter, booleano o struct", this.linea, this.columna);
        }
    }
}
