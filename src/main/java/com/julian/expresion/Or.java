package com.julian.expresion;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * Clase que representa una expresión de comparación de or.
 * Valor1 || Valor2 = 1 si es verdadero, 0 si es falso. 
 */
public class Or extends Instruccion {
    
    private Instruccion expIzq;
    private Instruccion expDer;

    /**
     * Constructor de la clase Or.
     * @param linea Linea en la que se encuentra la expresión.
     * @param columna Columna en la que se encuentra la expresión.
     * @param expIzq Operando izquierdo de la expresión.
     * @param expDer Operando derecho de la expresión.
     * */
    public Or(Instruccion expIzq, Instruccion expDer, int linea, int columna) {
        super(new Tipo(tipoDato.BOOLEANO), linea, columna);
        this.expIzq = expIzq;
        this.expDer = expDer;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se interpreta el operando de la expresión.
        var valorIzq = expIzq.interpretar(arbol, tablaDeSimbolos);
        // Si el valor izquierdo es un error, se retorna.
        if(valorIzq instanceof Error) return valorIzq;

        var valorDer = expDer.interpretar(arbol, tablaDeSimbolos);
        if(valorDer instanceof Error) return valorDer;

        // Se obtiene el tipo de dato de los operandos.
        var tipoIzq = this.expIzq.tipo.getTipo();
        var tipoDer = this.expDer.tipo.getTipo();
        
        // Implementacion de tabla de operatorias para la potencia.
        switch (tipoIzq){
            case BOOLEANO -> {
                switch (tipoDer){
                    case BOOLEANO -> {
                        return (boolean)valorIzq || (boolean)valorDer;
                    }
                    default -> {
                        return new Errores("Semantico", "Error de tipos en la operación or.", linea, columna);
                    }
                }
            }
            default -> {
                return new Errores("Semantico", "Error de tipos en la operación or.", linea, columna);
            }
        }
    }
}
