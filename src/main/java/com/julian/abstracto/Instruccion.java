package com.julian.abstracto;

import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;

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

    public Instruccion(Tipo tipo, int linea, int columna) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

    // Clase abstracta que representa una instrucción.
    public abstract Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos);
}
