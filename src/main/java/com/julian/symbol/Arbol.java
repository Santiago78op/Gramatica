package com.julian.symbol;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;

import java.util.LinkedList;

/**
 * Arbol -> Clase que representa el árbol de instrucciones.
 */
public class Arbol {

    // Constructor
    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private LinkedList<Errores> errores;
    // tabla de simbolos (global)
    private tablaSimbolo tablaSimbolosGlobal;
    // A nivel global tambien se puede tener las funciones
    private LinkedList<Instruccion> funciones;


    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        consola = "";
        this.errores = new LinkedList<>();
        // Inicializamos una lista que tiene funciones
        this.funciones = new LinkedList<>();
    }

    public tablaSimbolo getTablaSimbolosGlobal() {
        return tablaSimbolosGlobal;
    }

    public void setTablaSimbolosGlobal(tablaSimbolo tablaSimbolosGlobal) {
        this.tablaSimbolosGlobal = tablaSimbolosGlobal;
    }

    public LinkedList<Instruccion> getFunciones() {
        return funciones;
    }

    public void setFunciones(LinkedList<Instruccion> funciones) {
        this.funciones = funciones;
    }

    // Metodo para agregar funciones
    public void addFuncion(Instruccion funcion){
        // validamos que no exista la funcion
        /**
        boolean existe = false;
        for (Instruccion f: this.funciones){
            if (f.getId().equals(funcion.getId())){
                existe = true;
                break;
            }
        }

        if (!existe){
            this.funciones.add(funcion);
        }else{
            // Error semantico
            Errores error = new Errores("Semantico", "La funcion " + funcion.getId() + " ya existe", 0, 0);
            this.errores.add(error);
        }
*/
        this.funciones.add(funcion);
    }

    // Metodo para obtener las funciones
    public Instruccion getFuncion(String id){
        /**
        for (Instruccion f: this.funciones){
            if (f.getId().equals(id)){
                return f;
            }
        }
         */
        return null;
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public String getConsola() {
        return consola;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public void Print(String valor) {
        this.consola += valor + "\n";
    }

    public void addError(Errores error){
        this.errores.add(error);
    }
}
