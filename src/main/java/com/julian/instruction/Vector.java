package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;
import java.util.List;

/**
 * La clase Vector es la encargada de almacenar los valores de un vector.
 * La gramatica para la creacion de un vector es:
 * VECTOR ::= LBRACKET LISTA_VALORES:v RBRACKET  {: RESULT = new Vector(v, vleft, vright); :}
 *          | LBRACKET LISTA_VECTORES:v RBRACKET {: RESULT = new MultiDimensionalVector(v, vleft, vright); :}
 * ;
 *
 * LISTA_VALORES ::= LISTA_VALORES:v COMMA EXPRESION:e {: v.add(e); RESULT = v; :}
 *                 | EXPRESION:e                       {: RESULT = new LinkedList<Object>(); RESULT.add(e); :}
 * ;
 *
 * LISTA_VECTORES ::= LISTA_VECTORES:v COMMA VECTOR:e {: v.add(e); RESULT = v; :}
 *                  | VECTOR:e                        {: RESULT = new LinkedList<Object>(); RESULT.add(e); :}
 * ;
 */
public class Vector extends Instruccion {

    protected LinkedList<Object> valores;

    /**
     * Constructor de la clase Vector.
     * @param valores Valores del vector.
     * @param linea Linea en la que se encuentra el vector.
     * @param columna Columna en la que se encuentra el vector.
     */
    public Vector(LinkedList<Object> valores, int linea, int columna) {
        super(new Tipo(tipoDato.VECTOR), linea, columna);
        this.valores = valores;
    }

    public LinkedList<Object> getValores() {
        return valores;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        return this.valores;
    }

    public List<Object> getValues() {
        return valores;
    }

    public void setValores(LinkedList<Object> valores) {
        this.valores = valores;
    }
}
