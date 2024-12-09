package com.julian.expresion;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class Modulo extends Instruccion {

    private Instruccion operIzq;
    private Instruccion operDer;

    public Modulo(Instruccion operIzq, Instruccion operDer, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.operIzq = operIzq;
        this.operDer = operDer;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        var valorIzq = operIzq.interpretar(arbol, tablaDeSimbolos);
        // Si el valor izquierdo es un error, se retorna.
        if(valorIzq instanceof Error) return valorIzq;

        var valorDer = operDer.interpretar(arbol, tablaDeSimbolos);
        if(valorDer instanceof Error) return valorDer;

        // Se obtiene el tipo de dato de los operandos.
        var tipoIzq = operIzq.tipo.getTipo();
        var tipoDer = operDer.tipo.getTipo();

        // Implementacion de tabla de operatorias para la potencia.
        switch (tipoIzq){
            case ENTERO -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)((int)valorIzq % (int)valorDer);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)valorIzq % (double)valorDer;
                    }
                    default -> {
                        return new Errores("Semantico", "Error en el modulo, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)valorIzq % (int)valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)valorIzq % (double)valorDer;
                    }
                    default -> {
                        return new Errores("Semantico", "Error en el modulo, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("Semantico", "Error en el modulo, tipo de dato no valido", this.linea, this.columna);
            }
        }
    }
}
