package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * El incremento se da unicamente cuando venga el indentificador de la variable.
 * Ejemplo: let edad:int = (10 + 10) - 5;
 * Ejemplo: edad++;
 */
public class Incremento extends Instruccion {

    private String id;

    /**
     * Constructor de la clase Incremento.
     * @param id Identificador de la variable.
     * @param linea Linea en la que se encuentra la expresión.
     * @param columna Columna en la que se encuentra la expresión.
     */
    public Incremento( String id, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
    }


    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Buscar la variable en la tabla de simbolos.
        var simbolo = tablaDeSimbolos.getVariable(this.id);
        // Si la variable no existe, se retorna un error.
        if (simbolo == null) {
            return addSemanticError(this.id, this.linea,  this.columna);
        }

        // Validaciones del tipo de varible, que esta sea de tipo entero o flotante.
        if(simbolo.getTipo().getTipo() != tipoDato.ENTERO || simbolo.getTipo().getTipo() != tipoDato.DECIMAL){
            // Validaciones del tipo de varible -> let edad:int = (10 + 10) - 5;
            // El if valida si lo que entro no es una constante se actualiza el valor.
            if(!simbolo.isConstante()){
                // Actulizar el valor de la variable.
                simbolo.setValor((int) simbolo.getValor() + 1);

                // Se retorna el valor de la variable.
                return simbolo.getValor();
            }else{
                // El if valida si lo que entro si es contante entonces no se actualiza el valor.
                return addSemanticError(this.id, this.linea,  this.columna);
            }
        }else{
            return addSemanticError(this.id, this.linea,  this.columna);
        }
    }

    // Metodo para agregar el error Semantico
    private Errores addSemanticError(String id, int linea, int columna) {
        Errores error = new Errores("Semantico",
                "Error de tipos en la operación Incremento. " + "\n No se puede realizar el Incremento de\n" +
                        id, linea, columna);
        semanticErrorManager.addError(error);
        return error;
    }
}
