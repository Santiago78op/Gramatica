package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;
import com.julian.exception.Errores;

import java.util.LinkedList;

public class doWhile extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public doWhile(LinkedList<Instruccion> instrucciones, Instruccion condicion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se evalua la condicion del do while
        var condicion = this.condicion.interpretar(arbol, tablaDeSimbolos);
        // Si la condicion es un error se retorna el error.
        if(condicion instanceof Errores){
            return condicion;
        }

        // La condicion debe ser de tipo booleano
        if(this.condicion.tipo.getTipo() != tipoDato.BOOLEANO){
            return new Errores("Semantico", "La condicion del do while debe ser de tipo booleano", this.linea, this.columna);
        }

        // tenemos un bloque de instrucciones para el do while
        var nuevaTabla = new tablaSimbolo(tablaDeSimbolos);

        // Mientras la condicion sea verdadera se ejecutan las instrucciones del do while
        do {
            // Crear un nuevo ambito, para las instrucciones del do while.
            var tablaLocalDoWhile = new tablaSimbolo(nuevaTabla);

            for (Instruccion instruccion : this.instrucciones) {
                var result = instruccion.interpretar(arbol, tablaLocalDoWhile);
                if (result instanceof Errores) {
                    arbol.addError((Errores) result);
                }else if (result instanceof Break) {
                    return null; // Termina la ejecución del do while
                } else if (result instanceof Continue) {
                    break; // Salta al siguiente caso
                }
                // Se evalua la condicion del do while, por si cambia en el transcurso de las instrucciones
                condicion = this.condicion.interpretar(arbol, tablaDeSimbolos);
            }

        } while ((boolean) condicion);

        return null;
    }
}
