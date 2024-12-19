package com.julian.expresion;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/*
    * Clase que representa un valor nativo.
    * valor Valor del nativo.
 */
public class Nativo extends Instruccion {

    public Object valor;

    /**
     * Constructor de la clase Nativo.
     * -> valor Valor del nativo.
     * -> tipo Tipo de dato del nativo.
     * @param valor Valor del nativo.
     * @param tipo Tipo de dato del nativo.
     * @param linea Linea en la que se encuentra el nativo.
     * @param columna Columna en la que se encuentra el nativo.
     */
    public Nativo(Object valor, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Secuencias de escape.
        if (this.tipo.getTipo() == tipoDato.CADENA) {
            String cadena = (String) this.valor;
            cadena = cadena.replace("\\n", "\n");
            cadena = cadena.replace("\\t", "\t");
            cadena = cadena.replace("\\r", "\r");
            cadena = cadena.replace("\\\"", "\"");
            cadena = cadena.replace("\\\'", "\'");
            cadena = cadena.replace("\\\\", "\\");
            this.valor = cadena;
        }
        return this.valor;
    }
}
