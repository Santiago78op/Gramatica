package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/*
 * Clase que representa una expresión de negación de un identificador.
 * Ejemplo: -x
 */
public class NegacionId extends Instruccion {

    private String id;

    /**
     * Constructor de la clase NegacionID.
     * @param id Identificador de la variable.
     * @param linea Linea en la que se encuentra la expresión.
     * @param columna Columna en la que se encuentra la expresión.
     */
    public NegacionId(String id, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
    }


    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Buscar la variable en la tabla de simbolos
        var simbolo = tablaDeSimbolos.getVariable(this.id);
        // Si la variable no existe, se retorna un error
        if (simbolo == null) {
            Errores error = new Errores("Semantico",
                    "La variable " + id + "no existe", linea, columna);
            semanticErrorManager.addError(error);
            return error;
        }

        // Validaciones del tipo de varible -> let edad:int = (10 + 10) - 5; o const edad:int = (10 + 10) - 5;
        if (!simbolo.isConstante()) {
            // El if valida si lo que entro no es una constante se actualiza el valor.
            // Actulizar el tipo de la variable
            var valorOper = simbolo.getValor();
            var tipoOper = simbolo.getTipo().getTipo();

            // Implementacion de tabla de operatorias para la negación.
            switch (tipoOper) {
                case ENTERO -> {
                    this.tipo.setTipo(tipoDato.ENTERO);
                    return -(int) valorOper;
                }
                case DECIMAL -> {
                    this.tipo.setTipo(tipoDato.DECIMAL);
                    return -(double) valorOper;
                }
                default -> {
                    return addSemanticError("Error de tipos en la operación Negacion. No se puede realizar la Negacion con " + tipoOper, this.linea, this.columna);
                }
            }
        }else {
            return addSemanticError(this.id, this.linea,  this.columna);
        }
    }

    // Metodo para agregar el error Semantico
    // Metodo para agregar el error Semantico
    private Errores addSemanticError(String id, int linea, int columna) {
        Errores error = new Errores("Semantico",
                "La variable " + id + "es constante", linea, columna);
        semanticErrorManager.addError(error);
        return error;
    }
}
