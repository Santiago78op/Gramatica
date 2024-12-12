package com.julian.symbol;

import java.util.HashMap;

/**
 * tablaSimbolo -> Clase que representa una tabla de simbolos.
 * Ejemplo de como esta estructyrada la tabla:
 * tablaGlobal -> { id: nombreVariable, tipo: int, valor: 10 }
 */
public class tablaSimbolo {

    /**
     * El uso de HashMap permite almacenar los simbolos de una tabla de simbolos,
     * en donde la clave es el nombre de la variable y el valor es el simbolo.
     * Ejemplo: nombreVariable -> Simbolo -> {id: nombreVariable, tipo: int, valor: 10}
     *
     * El uso de String nombre permite identificar la tabla de simbolos.
     * Ejemplo: tablaGlobal, tablaLocal, tablaFuncion, etc.
     *
     * El uso de tablaSimbolo tablaSimboloAnterior permite almacenar la tabla de simbolos,
     * en donde se puede acceder a la tabla de simbolos anterior.
     * Ejemplo: tablaGlobal -> tablaLocal -> tablaFuncion -> tablaLocal
     * -> { id: nombreVariable, tipo: int, valor: 10 } -> { id: nombreVariable, tipo: int, valor: 10 }
     */
    private HashMap<String, Simbolo> tablaActual;
    private String nombre;
    private tablaSimbolo tablaSimboloAnterior;

    public tablaSimbolo() {
        this.tablaActual = new HashMap<>();
        this.nombre = "";
    }

    public tablaSimbolo(tablaSimbolo tablaSimboloAnterior) {
        this.tablaActual = new HashMap<>();
        this.nombre = "";
        this.tablaSimboloAnterior = tablaSimboloAnterior;
    }

    public HashMap<String, Simbolo> getTablaActual() {
        return tablaActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setTablaActual(HashMap<String, Simbolo> tablaActual) {
        this.tablaActual = tablaActual;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean setVariable(Simbolo simbolo){
        // Verificar si la variable ya existe en la tabla de simbolos.
        Simbolo busqueda = this.tablaActual.get(simbolo.getId().toLowerCase());
        // Si la variable no existe, se agrega a la tabla de simbolos.
        if (busqueda == null) {
            // Se agrega la variable a la tabla de simbolos.
            this.tablaActual.put(simbolo.getId().toLowerCase(), simbolo);
            // Se retorna true.
            return true;
        }
        // Si la variable ya existe, se retorna false.
        return false;
    }

    public Simbolo getVariable(String id){
        Simbolo busqueda = this.tablaActual.get(id.toLowerCase());
        if (busqueda != null) {
            return busqueda;
        }
        return null;
    }
}
