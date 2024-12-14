package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;
import com.julian.exception.Errores;

/**
 * Clase que ejecuta la instrucción de switch.
 * Ejemplo:
 * switch (expresion) {
 *    case 1: {
 *    // Instrucciones
 *    }
 *    case 2: {
 *    // Instrucciones
 *    }
 *    default: {
 *    // Instrucciones
 *    }
 */
public class Switch extends Instruccion {

    private Instruccion expresion;
    private Instruccion casos;
    private Instruccion defecto;

    /**
     * Constructor de la clase Switch.
     * @param expresion Expresión a evaluar.
     * @param casos Casos a evaluar.
     * @param defecto Caso por defecto.
     * @param linea Linea en la que se encuentra el switch.
     * @param columna Columna en la que se encuentra el switch.
     */
    public Switch(Instruccion expresion, Instruccion casos, Instruccion defecto, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.casos = casos;
        this.defecto = defecto;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se evalua la expresion del switch
        var exp = this.expresion.interpretar(arbol, tablaDeSimbolos);
        // Si la expresion es un error se retorna el error.
        if(exp instanceof Errores){
            return exp;
        }

        // tenemos un bloque de instrucciones para el switch y otro para el default
        var nuevaTabla = new tablaSimbolo(tablaDeSimbolos);

        // Se ejecutan los casos del switch
        var result = this.casos.interpretar(arbol, nuevaTabla);
        // Si el resultado es un error se retorna el error.
        if (result instanceof Errores) {
            return result;
        }

        // Se ejecuta el caso por defecto
        result = this.defecto.interpretar(arbol, nuevaTabla);
        // Si el resultado es un error se retorna el error.
        if (result instanceof Errores) {
            return result;
        }




        return null;
    }
}
