package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

public class elseIf extends Instruccion {

    private Instruccion condicionIf;
    private LinkedList<Instruccion> instruccionsIf;
    private Instruccion condicionElseIf;
    private LinkedList<Instruccion> instruccionsElseIf;

    public elseIf(LinkedList<Instruccion> instruccionsIf, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicionIf = condicionIf;
        this.instruccionsIf = instruccionsIf;
        this.condicionElseIf = condicionElseIf;
        this.instruccionsElseIf = instruccionsElseIf;
    }



    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se evalua la condicion del if
        var condicionIf = this.condicionIf.interpretar(arbol, tablaDeSimbolos);
        // Si la condicion es un error se retorna el error.
        if (condicionIf instanceof Errores) return condicionIf;

        // La condicion debe ser de tipo booleano
        if(this.condicionIf.tipo.getTipo() != tipoDato.BOOLEANO){
            semanticErrorManager.addError(new Errores("Semantico", "La condicion del if debe ser de tipo booleano", this.linea, this.columna));
            return new Errores("Semantico", "La condicion del if debe ser de tipo booleano", this.linea, this.columna);
        }

        // Se evalua la condicion del if
        var condicionElseIf = this.condicionElseIf.interpretar(arbol, tablaDeSimbolos);
        // Si la condicion es un error se retorna el error.
        if (condicionElseIf instanceof Errores) return condicionElseIf;

        // La condicion debe ser de tipo booleano
        if(this.condicionElseIf.tipo.getTipo() != tipoDato.BOOLEANO){
            semanticErrorManager.addError(new Errores("Semantico", "La condicion del else if debe ser de tipo booleano", this.linea, this.columna));
            return new Errores("Semantico", "La condicion del if debe ser de tipo booleano", this.linea, this.columna);
        }

        // tenemos un bloque de instrucciones para el if y otro para el else
        var nuevaTabla = new tablaSimbolo(tablaDeSimbolos);

        // Si la condicionIf es verdadera se ejecutan las instrucciones del if
        if((boolean)condicionIf){
            for (Instruccion instruccion : instruccionsIf) {
                var result = instruccion.interpretar(arbol, nuevaTabla);
                if(result instanceof Errores){
                    return result;
                }
            }
        } else {
            // Si la condicionElseIf es verdadera se ejecutan las instrucciones del else if
            if ((boolean) condicionElseIf){
                for (Instruccion instruccion : instruccionsElseIf) {
                    var result = instruccion.interpretar(arbol, nuevaTabla);
                    if(result instanceof Errores){
                        return result;
                    }
                }
            } else {
                return null;
            }
        }
       return null;
    }

}