package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

public class While extends Instruccion {

        private Instruccion condicion;
        private LinkedList<Instruccion> instrucciones;

        public While(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int columna) {
            super(new Tipo(tipoDato.VOID), linea, columna);
            this.condicion = condicion;
            this.instrucciones = instrucciones;
        }


    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se evalua la condicion del while
        var condicion = this.condicion.interpretar(arbol, tablaDeSimbolos);
        // Si la condicion es un error se retorna el error.
        if(condicion instanceof Errores){
            return condicion;
        }

        // La condicion debe ser de tipo booleano
        if(this.condicion.tipo.getTipo() != tipoDato.BOOLEANO){
            semanticErrorManager.addError(new Errores("Semantico", "La condicion del while debe ser de tipo booleano", this.linea, this.columna));
            return new Errores("Semantico", "La condicion del while debe ser de tipo booleano", this.linea, this.columna);
        }

        // tenemos un bloque de instrucciones para el while
        var nuevaTabla = new tablaSimbolo(tablaDeSimbolos);

        // Mientras la condicion sea verdadera se ejecutan las instrucciones del while
        while((boolean) condicion) {
            // Crear un nuevo ambito, para las instrucciones del for.
            var tablaLocalWhile = new tablaSimbolo(nuevaTabla);
            for (Instruccion instruccion : this.instrucciones) {
                var result = instruccion.interpretar(arbol, tablaLocalWhile);
                if (result instanceof Errores) {
                    arbol.addError((Errores) result);
                }else if (result instanceof Break) {
                    return null; // Termina la ejecución del switch
                } else if (result instanceof Continue) {
                    break; // Salta al siguiente caso
                }
                // Se evalua la condicion del while, por si cambia en el transcurso de las instrucciones
                condicion = this.condicion.interpretar(arbol, tablaDeSimbolos);
            }
        }
        return null;
    }
}
