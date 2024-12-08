package com.julian.expresion;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class Nativo extends Instruccion {

    public Object valor;

    public Nativo(Tipo tipo, int linea, int columna, Object valor) {
        super(tipo, linea, columna);
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        /*
        if (this.tipo.getTipoDato() == tipoDato.BOOLEANO) {
            if (this.valor.toString().equals("true")) {
                return true;
            }
        }
        */
        return this.valor;
    }
}
