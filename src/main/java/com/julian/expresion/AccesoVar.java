package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.instruction.Struct;
import com.julian.symbol.*;

/**
 * El acceso se da unicamente cuando venga el indentificador de la variable.
 * Ejemplo: let edad:int = (10 + 10) - 5;
 */
public class AccesoVar extends Instruccion {

    private String id;
    private Object valorStruct;

    /**
     * Constructor de la clase AccesoVar.
     * @param id Identificador de la variable.
     * @param linea Linea en la que se encuentra la expresión.
     * @param columna Columna en la que se encuentra la expresión.
     */
    public AccesoVar(String id, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Buscar la variable en la tabla de simbolos
        var simbolo = tablaDeSimbolos.getVariable(id);
        // Si la variable no existe, se retorna un error
        if (simbolo == null) {
            return addSemanticError(this.id, this.linea,  this.columna);
        }

        // Actulizar el tipo de la variable
        this.tipo.setTipo(simbolo.getTipo().getTipo());
        // Se retorna el valor de la variable
        return simbolo.getValor();
    }

    // Metodo para agregar el error Semantico
    private Errores addSemanticError(String id, int linea, int columna) {
        Errores error = new Errores("Semantico",
                "La variable " + id + " es constante", linea, columna);
        semanticErrorManager.addError(error);
        return error;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }
}
