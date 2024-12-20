package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.expresion.AccesoVector;
import com.julian.expresion.Nativo;
import com.julian.symbol.Arbol;
import com.julian.symbol.Simbolo;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;

import static com.julian.symbol.tipoDato.*;
/**
 * Esta clase se encarga de declarar una variable.
 * let / const -> id : int/double/string/...;
 * Mutabilidad -> id : tipo                 ;
 * o puede ser:
 * let / const -> id : tipo = expresion     ;
 * Mutabilidad -> id : tipo = expresion     ;
 *
 * La mutabilidad se refiere a la capacidad de cambiar el valor de la variable.
 * Se toma como un true o false. Si es true, la variable puede cambiar su valor.
 * Si es false, la variable no puede cambiar su valor.
 */
public class Declaracion extends Instruccion {

    private String id;
    private Instruccion expresion;
    private int mutable;
    private Object valueExpresion;

    /**
     * Se crearon dos constructores para la clase Simbolo.
     * El primer constructor recibe el tipo, el id, el valor y si es constante.
     *      Formato: let / const -> id : tipo = expresion;
     *             : let         numero: int  = 10;
     *      Ejemplo: Simbolo simbolo = new Simbolo(new Tipo(tipoDato.INT), "numero", 10, false);
     * El segundo constructor recibe el tipo, el id y si es constante.
     *      Formato: let / const -> id : tipo;
     *             : let         numero_int;
     *      Ejemplo: Simbolo simbolo = new Simbolo(new Tipo(tipoDato.INT), "numero", false);
     */

    /**
     * Constructor de la clase Declaracion.
     * @param tipo Tipo de dato.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     * @param id Nombre de la variable.
     * @param expresion Expresión a asignar a la variable.
     * @param mutable Si la variable es mutable o no.
     */
    public Declaracion(Tipo tipo, int linea, int columna, String id, Instruccion expresion, int mutable) {
        super(tipo, linea, columna);
        this.id = id;
        this.expresion = expresion;
        this.mutable = mutable;
    }

    /**
     * Segundo Constructor de la clase Declaracion
     * Este constructor se utiliza cuando no se asigna un valor a la variable.
     * @param tipo Tipo de dato.
     * @param linea Linea en la que se encuentra la instrucción.
     * @param columna Columna en la que se encuentra la instrucción.
     * @param id Nombre de la variable.
     * @param mutable Si la variable es mutable o no.
     */
    public Declaracion(Tipo tipo, int linea, int columna, String id, int mutable) {
        super(tipo, linea, columna);
        this.id = id;
        this.mutable = mutable;
    }

    /**
     * Metodo que se encarga de interpretar la instrucción de declaración.
     * @param arbol Arbol que contiene las variables y funciones.
     * @param tablaDeSimbolos Tabla de simbolos del ambito actual.
     * @return null si la declaración se realizó correctamente, un error en caso contrario.
     */
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // 1. Validamos si es una declaracion con expresion.
        if (this.expresion != null) {
            // Validar la expresion recibida
            valueExpresion = this.expresion.interpretar(arbol, tablaDeSimbolos);
            if (valueExpresion instanceof Errores) return valueExpresion;

            // Validar que el tipo de la variable sea igual al tipo de la expresion.
            if(this.tipo.getTipo() != this.expresion.tipo.getTipo()){
                semanticErrorManager.addError(new Errores("Semantico", "Error de tipos en la declaración de la variable " + this.id + ".\n" +
                        "El tipo de la variable no coincide con el tipo de la expresión.", this.linea, this.columna));
                return new Errores("Semantico", "Error de tipos en la declaración de la variable " + this.id + ".\n" +
                        "El tipo de la variable no coincide con el tipo de la expresión.", this.linea, this.columna);
            }

            // Validamos la existencia de la variable en la tabla de simbolos, y la agregamos.
            Simbolo simbolo = new Simbolo(this.tipo, this.id, valueExpresion, false, "Externo", "",this.linea, this.columna);
            if (tablaDeSimbolos.setVariable(simbolo)) {
                // 1 no es constante, 0 es constante
                if (this.mutable == 0) {
                    simbolo.setConstante(false);
                } else {
                    simbolo.setConstante(true);
                }
                return null;
            }
        }else{
            // Si no se asigna una expresion a la variable, se le asigna un valor por defecto.
            setExpresion(this.tipo);
            // Validamos la existencia de la variable en la tabla de simbolos, y la agregamos.
            // Validamos la existencia de la variable en la tabla de simbolos, y la agregamos.
            Simbolo simbolo = new Simbolo(this.tipo, this.id, valueExpresion, false, "Externo", "",this.linea, this.columna);
            if (tablaDeSimbolos.setVariable(simbolo)) {
                // 0 no es constante, 1 es constante
                if (this.mutable == 0) {
                    simbolo.setConstante(false);
                } else {
                    simbolo.setConstante(true);
                }
                return null;
            }
        }

        // Retornamos Error si la variable ya existe en la tabla de simbolos.
        semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna));
        return new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna);
    }

    /**
     * La siguiente funcion setExpresion, tiene como funcion asignar una expresion a la variable.
     * ya que esta se declara sin expresion, entonces se le debe dar un por defecto dependioendo
     * del tipo que tenga su delaracion.
     *
     * Formato: let / const -> id : tipo;
     *        : let         numero_int;
     */
    public void setExpresion(Tipo tipo) {
        switch (tipo.getTipo()){
            case ENTERO:
                valueExpresion = 0;
                break;
            case DECIMAL:
                valueExpresion = 0.0;
                break;
            case CADENA:
                valueExpresion = "";
                break;
            case CARACTER:
                valueExpresion = '\u0000';
                break;
            case BOOLEANO:
                valueExpresion = true;
                break;
        }
    }
}
