package com.julian.expresion;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class Negacion extends Instruccion {

    private Instruccion oper;

    public Negacion(Instruccion oper, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.oper = oper;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se interpreta el operando de la expresión.
        var valorOper = oper.interpretar(arbol, tablaDeSimbolos);
        // Si el valor del operando es un error, se retorna.
        if (valorOper instanceof Error) return valorOper;

        // Se obtiene el tipo de dato del operando.
        var tipoOper = oper.tipo.getTipo();

        // Implementacion de tabla de operatorias para la negación.
        switch (tipoOper) {
            case ENTERO -> {
                this.tipo.setTipo(tipoDato.ENTERO);
                return -(int) valorOper;
            }
            case DECIMAL -> {
                this.tipo.setTipo(tipoDato.DECIMAL);
                return -(double) valorOper;
            }
            default -> {
                // Se retorna un error si el tipo de dato no es booleano.
                return new Errores("Semantico", "No se puede negar el tipo de dato " + tipoOper, linea, columna);
            }
        }
    }
}
