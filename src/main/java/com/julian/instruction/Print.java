package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * Print -> Clase que hereda de Instruccion y que se encarga de imprimir en consola.
 */
public class Print extends Instruccion {

    private Instruccion expresion;

    /**
     * Constructor de la clase Print.
     * -> expresion Expresión a imprimir.
     * @param expresion Expresión a imprimir.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     */
    public Print(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        var valor = this.expresion.interpretar(arbol, tablaDeSimbolos);
        if(valor instanceof Errores) return valor;
        arbol.Print(valor.toString());
        return null;
    }
}
