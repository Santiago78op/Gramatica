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
 * Clase que representa la instruccion Else.
 * La instruccion Else se compone de:
 * If con else:
 * 2. if ( <EXPRESION> ) {
 *          <INSTRUCCIONES>
 *    } else {
 *          <INSTRUCCIONES>
 *    }
 */
public class Else extends Instruccion {

    private Instruccion condicionIf;
    private LinkedList<Instruccion> instruccionesIf;
    private LinkedList<Instruccion> instruccionesElse;

    /**
     * Constructor de la instruccion Else.
     * @param condicionIf Condicion del if.
     * @param instruccionesIf Lista de instrucciones del if.
     * @param instruccionesElse Lista de instrucciones del else.
     * @param linea Linea en la que se encuentra la instruccion.
     * @param columna Columna en la que se encuentra la instruccion.
     */
    public Else(Instruccion condicionIf, LinkedList<Instruccion> instruccionesIf, LinkedList<Instruccion> instruccionesElse, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicionIf = condicionIf;
        this.instruccionesIf = instruccionesIf;
        this.instruccionesElse = instruccionesElse;
    }


    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se evalua la condicion del if
        var condIf = this.condicionIf.interpretar(arbol, tablaDeSimbolos);
        // Si la condicion es un error se retorna el error.
        if (condIf instanceof Errores) return condIf;

        // La condicion debe ser de tipo booleano
        if(this.condicionIf.tipo.getTipo() != tipoDato.BOOLEANO){
            semanticErrorManager.addError(new Errores("Semantico", "La condicion del if debe ser de tipo booleano", this.linea, this.columna));
            return new Errores("Semantico", "La condicion del if debe ser de tipo booleano", this.linea, this.columna);
        }

        // tenemos un bloque de instrucciones para el if y otro para el else
        var nuevaTabla = new tablaSimbolo(tablaDeSimbolos);

        // Si la condicionIf es verdadera se ejecutan las instrucciones del if
        if((boolean)condIf) {
            for (Instruccion instruccion : instruccionesIf) {
                var result = instruccion.interpretar(arbol, nuevaTabla);
                if (result instanceof Errores) {
                    arbol.addError((Errores) result);
                }else if (result instanceof Break) {
                    return null; // Termina la ejecución del switch
                } else if (result instanceof Continue) {
                    break; // Salta al siguiente caso
                }
            }
        } else {
            // Si la condicionIf es falsa se ejecutan las instrucciones del else
            for (Instruccion instruccion : instruccionesElse) {
                var result = instruccion.interpretar(arbol, nuevaTabla);
                if (result instanceof Errores) {
                    arbol.addError((Errores) result);
                }else if (result instanceof Break) {
                    return result; // Termina la ejecución del switch
                } else if (result instanceof Continue) {
                    return result; // Salta al siguiente caso
                }
            }
        }
        return null;
    }
}
