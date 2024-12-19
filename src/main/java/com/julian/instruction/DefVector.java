package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

/**
 * Clase que define un vector.
 * La declaracion del vector se hace de la siguiente forma:
 *  -> mutabilidad id : tipo [] = [valor1, valor2, ...];
 *  -> let    numeros : int []  = [1, 2, 3, 4, 5];
 *  -> const  letras  : char [] = ['a', 'b', 'c', 'd', 'e'];
 */
public class DefVector extends Instruccion {

    private String id;
    private LinkedList<Instruccion> valores;
    private int mutabilidad;

    public DefVector(Tipo tipo, int linea, int columna, String id, LinkedList<Instruccion> valores, int mutabilidad) {
        super(tipo, linea, columna);
        this.id = id;
        this.valores = valores;
        this.mutabilidad = mutabilidad;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se crea una lista con los valores del vector,
        LinkedList<Object> valoresInterpretados = new LinkedList<>();
        // Se recorre la lista de valores del vector.
        for (Instruccion valor : valores) {
            // Se interpreta el valor. El valrInterpretado puede ser un valor primitivo o un error.
            // En este caso el valorInterpretado es la Expresion.
            Object valorInterpretado = valor.interpretar(arbol, tablaDeSimbolos);
            // Se validan errores semánticos.
            if (valorInterpretado instanceof Errores) return valorInterpretado;
            // Se valida que cada valor sea del tipo correcto.
            if (this.tipo.getTipo() != valorInterpretado.tipo.getTipo()) {
                return new Errores(Errores.TipoError.SEMANTICO, "El valor del vector no es del tipo correcto.", linea, columna);
            }
            // Se agrega el valor interpretado a la lista de valores.
            valoresInterpretados.add(valorInterpretado);
        }

    }
}
