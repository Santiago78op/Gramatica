package com.julian.abstracto;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

/**
 * Instruccion -> Clase abstracta que representa una instrucción.
 * Una clase abstracta es una clase que no se puede instanciar, pero que puede tener métodos abstractos.
 * Un método abstracto es un método que no tiene cuerpo, es decir, no tiene implementación.
 * En Java, una clase abstracta se declara con la palabra clave abstract.
 * En Java, un método abstracto se declara con la palabra clave abstract.
 * En Java, una clase abstracta puede tener métodos abstractos y métodos concretos.
 */
public abstract class Instruccion {

    // Estos son atributos que todas nuestras clases van a tener.
    public Tipo tipo;
    public int linea;
    public int columna;
    //public Object resultado = new Enum<>() {};

    /**
     * Constructor de la clase Instruccion.
     * -> tipo Tipo de dato de la instrucción.
     * @param tipo Tipo de dato de la instrucción.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     */
    public Instruccion(Tipo tipo, int linea, int columna) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

    public Tipo getTipo() {
        return tipo;
    }


    // Clase abstracta que representa una instrucción.
    public abstract Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos);

}
