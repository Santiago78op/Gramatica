package com.julian.symbol;

import java.util.HashMap;

/**
 * tablaSimbolo -> Clase que representa una tabla de simbolos.
 */
public class tablaSimbolo {

    private HashMap<String, Simbolo> tablaActual;
    private String nombre;

    public tablaSimbolo() {
        this.tablaActual = new HashMap<>();
        this.nombre = "";
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
        Simbolo busqueda = (Simbolo) this.tablaActual.get(simbolo.getId().toLowerCase());
        if (busqueda == null) {
            this.tablaActual.put(simbolo.getId().toLowerCase(), simbolo);
            return true;
        }
        return false;
    }
}
