package com.julian.symbol;

import java.util.HashMap;

/**
 * tablaSimbolo -> Clase que representa una tabla de simbolos.
 */
public class tablaSimbolo {

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

    public void agregar(String id, Object valorInterpretado, Tipo tipo, int linea, int columna, boolean mutabilidad) {
        Simbolo simbolo = new Simbolo(tipo, id, valorInterpretado, mutabilidad);
        this.tablaActual.put(id, simbolo);
    }
}
