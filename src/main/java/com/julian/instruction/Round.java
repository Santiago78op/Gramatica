package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * Clase que representa una instrucción de redondeo.
 * round(Expresion)
 * -> Si el decimal es mayor o igual a 0.5, se aproxima al entero superior.
 * -> Si el decimal es menor que 0.5, se aproxima al número inferior.
 */
public class Round extends Instruccion {

    private final Instruccion expression;

    /**
     * Constructor de la clase Round.
     * @param expression Expresion a redondear.
     * @param linea Linea en la que se encuentra la expresion.
     * @param columna Columna en la que se encuentra la expresion.
     */
    public Round(Instruccion expression, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expression = expression;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Interpretamos la expresion
        var valor = this.expression.interpretar(arbol, tablaDeSimbolos);
        // Si la expresion es un error se retorna el error.
        if(valor instanceof Errores){
            return valor;
        }

        // La expresion debe ser de tipo decimal
        if(this.expression.tipo.getTipo() != tipoDato.DECIMAL){
            return new Errores("Semantico", "La expresion del round debe ser de tipo decimal", this.linea, this.columna);
        }

        // Se redondea el valor
        if (valor instanceof Double) {
            double numero = (Double) valor;
            int resultado = (numero - Math.floor(numero) >= 0.5) ? (int) Math.ceil(numero) : (int) Math.floor(numero);
            // Actualizar el tipo de la expresion
            this.tipo.setTipo(tipoDato.ENTERO);
            return resultado;
        } else {
            return new Errores("Semantico", "La expresion del round debe ser de tipo decimal", this.linea, this.columna);
        }

    }
}
