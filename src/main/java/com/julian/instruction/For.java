package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

import java.util.LinkedList;

/**
 * Clase que representa la instruccion For.
 * La instruccion For se compone de:
 * for ( <ASIGNACIÓN> ; <CONDICIÓN> ; <ACTUALIZACIÓN> ) {
 *      <INSTRUCCIONES>
 * }
 */
public class For extends Instruccion {

    private Instruccion asignacion;
    private Instruccion condicion;
    private Instruccion actualizacion;
    private LinkedList<Instruccion> instrucciones;

    /**
     * Constructor de la instruccion For.
     * @param tipo Tipo de dato de la instruccion.
     * @param linea Linea en la que se encuentra la instruccion.
     * @param columna Columna en la que se encuentra la instruccion.
     * @param asignacion Instruccion de asignacion.
     * @param condicion Instruccion de condicion.
     * @param actualizacion Instruccion de actualizacion.
     * @param instrucciones Lista de instrucciones.
     */
    public For(Instruccion asignacion, Instruccion condicion, Instruccion actualizacion, LinkedList<Instruccion> instrucciones, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.asignacion = asignacion;
        this.condicion = condicion;
        this.actualizacion = actualizacion;
        this.instrucciones = instrucciones;
    }

    /**
     * Metodo que se encarga de interpretar la instruccion For.
     * Se esta haciendo un recorrido en postorden para interpretar las instrucciones.
     * @param arbol Arbol de instrucciones.
     * @param tablaDeSimbolos Tabla de simbolos.
     * @return El resultado de la instruccion For.
     */
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Asignacion a la variable.
        var resultado1 = this.asignacion.interpretar(arbol, tablaDeSimbolos);
        if (resultado1 instanceof Errores) {
            return resultado1;
        }

        // Validar que la condicion sea de tipo booleano.
        var condicion = this.condicion.interpretar(arbol, tablaDeSimbolos);
        if (condicion instanceof Errores) {
            return condicion;
        }

        if(this.condicion.getTipo().getTipo() != tipoDato.BOOLEANO) {
            return new Errores("Semantico", "La condicion del for debe ser de tipo booleano.", this.linea, this.columna);
        }

        // Se crea un nuevo ambito.
        var tablaLocal = new tablaSimbolo(tablaDeSimbolos);

        // Se recorre el ciclo.
        while ((boolean) this.condicion.interpretar(arbol, tablaLocal)) {
            // Crear un nuevo ambito, para las instrucciones del for.
            var tablaLocalFor = new tablaSimbolo(tablaLocal);
            // Se recorre las instrucciones del for.
            for (Instruccion instruccion : this.instrucciones) {
                var resultado = instruccion.interpretar(arbol, tablaLocalFor);
                if (resultado instanceof Errores) {
                    arbol.addError((Errores) resultado);
                }

                // Actualizar la variable.
                var resultado2 = this.actualizacion.interpretar(arbol, tablaLocalFor);
                if (resultado2 instanceof Errores) {
                    return resultado2;
                }

            }
        }
        return null;
    }
}
