package com.julian.symbol;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.instruction.Funcion;
import com.julian.instruction.Metodo;
import com.julian.instruction.Struct;

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
    // A nivel global tambien se puede tener metodos
    private LinkedList<Instruccion> metodos;
    // A nivel global tambien se puede tener las funciones
    private LinkedList<Instruccion> funciones;
    // A nivel global tambien se puede tener las estructuras
    private LinkedList<Instruccion> estructuras;


    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        consola = "";
        this.errores = new LinkedList<>();
        // Inicializamos una lista que tiene funciones
        this.funciones = new LinkedList<>();
        // Inicializamos una lista que tiene metodos
        this.metodos = new LinkedList<>();
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

    // Metodo para agregar metodos
    public void addMetodo(Instruccion metodos){
        // validamos que no exista la funcion
        boolean existe = false;
        String id = "";
        for (Instruccion f: this.metodos){
            if (f instanceof Metodo metodo){
                if (metodo.getId().equalsIgnoreCase(((Metodo) metodos).getId())){
                    id = metodo.getId();
                    existe = true;
                    break;
                }
            }
        }

        if (!existe){
            this.metodos.add(metodos);
        }else{
            // Error semantico
            semanticErrorManager.addError(new Errores("Semantico", "El metodo " + id + " ya existe", 0, 0));
            Errores error = new Errores("Semantico", "El metodo " + id + " ya existe", 0, 0);
            this.errores.add(error);
        }
    }

    // Metodo para obtener las metodos
    public Instruccion getMetodo(String id){
        for( var i: this.metodos){
            if (i instanceof Metodo metodo){
                if (metodo.getId().equalsIgnoreCase(id)){
                    return i;
                }
            }
        }
        return null;
    }

    // Metodo para agregar funciones
    public void addFuncion(Instruccion funciones){
        // validamos que no exista la funcion
        boolean existe = false;
        String id = "";
        for (Instruccion f: this.funciones){
            if (f instanceof Funcion funcion){
                if (funcion.getId().equalsIgnoreCase(((Funcion) funciones).getId())){
                    id = funcion.getId();
                    existe = true;
                    break;
                }
            }
        }

        if (!existe){
            this.funciones.add(funciones);
        }else{
            // Error semantico
            semanticErrorManager.addError(new Errores("Semantico", "La funcion " + id + " ya existe", 0, 0));
            Errores error = new Errores("Semantico", "La funcion " + id + " ya existe", 0, 0);
            this.errores.add(error);
        }
    }

    // Metodo para obtener las funciones
    public Instruccion getFuncion(String id){
        for( var i: this.funciones){
            if (i instanceof Funcion funcion){
                if (funcion.getId().equalsIgnoreCase(id)){
                    return i;
                }
            }
        }
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

    public void addStruct(Instruccion estructura){
        // validamos que no exista la funcion
        boolean existe = false;
        String id = "";
        for (Instruccion f: this.estructuras){
            if (f instanceof Struct struct){
                if (struct.getId().equalsIgnoreCase(((Struct) struct).getId())){
                    id = struct.getId();
                    existe = true;
                    break;
                }
            }
        }

        if (!existe){
            this.estructuras.add(estructura);
        }else{
            // Error semantico
            semanticErrorManager.addError(new Errores("Semantico", "La estructura " + id + " ya existe", 0, 0));
            Errores error = new Errores("Semantico", "La estructura " + id + " ya existe", 0, 0);
            this.errores.add(error);
        }
        this.estructuras.add(estructura);
    }

    public Instruccion getEstructuras(String id) {
        {
            for (var i : this.estructuras) {
                if (i instanceof Struct struct) {
                    if (struct.getId().equalsIgnoreCase(id)) {
                        return i;
                    }
                }
            }
            return null;
        }
    }
}
