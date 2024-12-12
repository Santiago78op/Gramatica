package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
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
    private boolean mutable;
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
    public Declaracion(Tipo tipo, int linea, int columna, String id, Instruccion expresion, boolean mutable) {
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
    public Declaracion(Tipo tipo, int linea, int columna, String id, boolean mutable) {
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
        /**
         * 1. Realiza las validaciones necesarias para la declaración de la variable.
         * 2. Se debe validar si la variable ya existe en la tabla de simbolos.
         * 3. Si la variable ya existe, se retorna un error.
         * 4. Si la variable no existe, se agrega a la tabla de simbolos.
         * 5. Si la variable es constante, no se puede modificar su valor.
         * 6. Si la variable no es constante, se puede modificar su valor.
         * 7. Se retorna null si la declaración se realizó correctamente.
         * 8. Se retorna un error si la declaración no se realizó correctamente.
         * 9. Se retorna un error si la variable ya existe en la tabla de simbolos.
         * 10.Se retorna un error si la variable es constante y se intenta modificar su valor.
         * 11.Se retorna un error si el tipo de la variable no coincide con el tipo de la expresión.
         * 12.Se retorna un error si la variable no existe en la tabla de simbolos.
         * 13.Ya que hay dos tipos -> let / const -> id : tipo = expresion; o let / const -> id : tipo;
         */

        // 1. Realiza las validaciones necesarias para la declaración de la variable.
        if (this.expresion != null) {
            // Validar la expresion recibida
            var valorInterpretado = this.expresion.interpretar(arbol, tablaDeSimbolos);
            if (valorInterpretado instanceof Errores) return valorInterpretado;

            // Valida el tipo de la Variable
            if (this.expresion.tipo.getTipo() != this.tipo.getTipo()) {
                semanticErrorManager.addError(new Errores("Semantico", "El tipo de la variable no coincide con el tipo de la expresion", this.linea, this.columna));
                return new Errores("Semantico", "El tipo de la variable no coincide con el tipo de la expresion", this.linea, this.columna);
            }

            // 2. Se debe validar si la variable ya existe en la tabla de simbolos.
            Simbolo busqueda = tablaDeSimbolos.getVariable(this.id);
            // 3. Si la variable ya existe, se retorna un error.
            if (busqueda != null) {
                semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna));
                return new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna);
            }
            // 4. Si la variable no existe, se agrega a la tabla de simbolos.
            Simbolo simbolo = new Simbolo(this.tipo, this.id, valorInterpretado, !this.mutable);
            // 5. Si la variable es constante, no se puede modificar su valor.
            if (!this.mutable) {
                simbolo.setConstante(true);
            }
            // 6. Si la variable no es constante, se puede modificar su valor.
            else {
                var valor = this.expresion.interpretar(arbol, tablaDeSimbolos);
                if (valor instanceof Errores) return valor;
                if (this.tipo.getTipo() != this.expresion.tipo.getTipo()) {
                    semanticErrorManager.addError(new Errores("Semantico", "El tipo de la variable no coincide con el tipo de la expresion", this.linea, this.columna));
                    return new Errores("Semantico", "El tipo de la variable no coincide con el tipo de la expresion", this.linea, this.columna);
                }
                simbolo.setValor(valor);
            }
            tablaDeSimbolos.setVariable(simbolo);
        } else {
            // validamos el tipo de la variable, para devolver un valor por defecto
            setExpresion(this.tipo);
            // 2. Se debe validar si la variable ya existe en la tabla de simbolos.
            Simbolo busqueda = tablaDeSimbolos.getVariable(this.id);
            // 3. Si la variable ya existe, se retorna un error.
            if (busqueda != null) {
                semanticErrorManager.addError(new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna));
                return new Errores("Semantico", "La variable " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna);
            }
            // 4. Si la variable no existe, se agrega a la tabla de simbolos.
            Simbolo simbolo = new Simbolo(this.tipo, this.id, this.valueExpresion, !this.mutable);
            // 5. Si la variable es constante, no se puede modificar su valor.
            if (!this.mutable) {
                simbolo.setConstante(true);
            }
            tablaDeSimbolos.setVariable(simbolo);
        }
        return null;
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
                this.valueExpresion = 0;
                break;
            case DECIMAL:
                this.valueExpresion = 0.0;
                break;
            case CADENA:
                this.valueExpresion = "";
                break;
            case CARACTER:
                this.valueExpresion = '\u0000';
                break;
            case BOOLEANO:
                this.valueExpresion = false;
                break;
        }
    }
}
