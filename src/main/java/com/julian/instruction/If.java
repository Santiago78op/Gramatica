package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

/**
 * Clase que representa la instruccion If.
 * Los tres tipos de instrucciones If que contempla esta funcion son:
 * If simple:
 * 1. if ( <EXPRESION> ) {
 *          <INSTRUCCIONES>
 *      }
 * If con else:
 * 2. if ( <EXPRESION> ) {
 *          <INSTRUCCIONES>
 *    } else {
 *          <INSTRUCCIONES>
 *    }
 * If con else if:
 * 3. if ( <EXPRESION> ) {
 *          <INSTRUCCIONES>
 *      } else <IF>
 */
public class If extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> instruccionesIf;
    private LinkedList<Instruccion> instruccionesElse;

    public If(Instruccion condicion, LinkedList<Instruccion> instruccionesIf, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instruccionesIf = instruccionesIf;
    }

    public If(Instruccion condicion, LinkedList<Instruccion> instruccionesIf, LinkedList<Instruccion> instruccionesElse, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instruccionesIf = instruccionesIf;
        this.instruccionesElse = instruccionesElse;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se evalua la condicion del if
        var condicion = this.condicion.interpretar(arbol, tablaDeSimbolos);
        // Si la condicion es un error se retorna el error.
        if(condicion instanceof Errores){
            return condicion;
        }

        // La condicion debe ser de tipo booleano
        if(this.condicion.tipo.getTipo() != tipoDato.BOOLEANO){
            semanticErrorManager.addError(new Errores("Semantico", "La condicion del if debe ser de tipo booleano", this.linea, this.columna));
            return new Errores("Semantico", "La condicion del if debe ser de tipo booleano", this.linea, this.columna);
        }

        // tenemos un bloque de instrucciones para el if y otro para el else
        var nuevaTabla = new tablaSimbolo(tablaDeSimbolos);

        // Si la condicion es verdadera se ejecutan las instrucciones del if
        if((boolean)condicion){
            for (Instruccion instruccion : instruccionesIf) {
                var result = instruccion.interpretar(arbol, nuevaTabla);
                if(result instanceof Errores){
                    return result;
                }
            }
        } else {
            // Si la condicion es falsa se ejecutan las instrucciones del else
            for (Instruccion instruccion : instruccionesElse) {
                var result = instruccion.interpretar(arbol, nuevaTabla);
                if(result instanceof Errores){
                    arbol.addError((Errores) result);
                }
            }
        }
        return null;
    }
}
