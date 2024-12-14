package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

public class IfElseIf extends Instruccion {

    private Instruccion condicionIf;
    private LinkedList<Instruccion> instruccionsIf;
    private Instruccion instruccionsElseIf;

    public IfElseIf(Instruccion condicionIf, LinkedList<Instruccion> instruccionsIf, Instruccion instruccionsElseIf, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicionIf = condicionIf;
        this.instruccionsIf = instruccionsIf;
        this.instruccionsElseIf = instruccionsElseIf;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se evalua la condicion del if
        var condIf = this.condicionIf.interpretar(arbol, tablaDeSimbolos);
        // Si la condicion es un error se retorna el error.
        if(condIf instanceof Errores){
            return condIf;
        }

        // La condicion debe ser de tipo booleano
        if(this.condicionIf.tipo.getTipo() != tipoDato.BOOLEANO){
            semanticErrorManager.addError(new Errores("Semantico", "La condicion del if debe ser de tipo booleano", this.linea, this.columna));
            return new Errores("Semantico", "La condicion del if debe ser de tipo booleano", this.linea, this.columna);
        }

        // tenemos un bloque de instrucciones para el if y otro para el else if
        var nuevaTabla = new tablaSimbolo(tablaDeSimbolos);

        // Si la condicion es verdadera se ejecutan las instrucciones del if
        if((boolean) condIf) {
            for (Instruccion instruccion : this.instruccionsIf) {
                var result = instruccion.interpretar(arbol, nuevaTabla);
                if (result instanceof Errores) {
                    arbol.addError((Errores) result);
                }
            }
        } else {
            var result = this.instruccionsElseIf.interpretar(arbol, nuevaTabla);
            if (result instanceof Errores) {
                return result;
            }
        }
        return null;
    }

}