package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Esta clase se encarga de declarar una lista.
 * -> Las listas dinámicas son una estructura de datos de tamaño variable que
 * -> pueden almacenar valores de forma ilimitada.
 * Ejemplo:
 *      let <ID> : List< <TIPO> > ;
 *      // Ejemplo
 *      let miLista : List<int>;
 */
public class DeclaracionLista extends Instruccion {

    private Tipo tipoLista;
    private String id;

    /**
     * Constructor de la clase
     * @param id Identificador de la lista
     * @param tipoLista Tipo de la lista
     * @param tipo Tipo de dato de la lista
     * @param linea Linea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     */
    public DeclaracionLista(String id,Tipo tipoLista, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.tipoLista = tipoLista;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Validar que el tipo sea de tipo lista
        if (this.tipoLista.getTipo() != tipoDato.LISTA) {
            semanticErrorManager.addError(new Errores("Semantico", "El tipo de dato de la lista " + this.id + " no es correcto", this.linea, this.columna));
            return new Errores("Semantico", "El tipo de dato de la lista " + this.id + " no es correcto", this.linea, this.columna);
        }

        // Se instancia el simbolo  de la lista
        Simbolo simbolo = new Simbolo(this.tipoLista, this.id, new LinkedList<HashMap>(), false, "Externo", "",this.linea, this.columna);
        // Se agrega el simbolo a la tabla de simbolos
        if (tablaDeSimbolos.setVariable(simbolo)) {
            return null;
        }else {
            // Return an error if the variable already exists
            semanticErrorManager.addError(new Errores("Semantico", "La lista " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna));
            return new Errores("Semantico", "La lista " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna);
        }
    }
}
