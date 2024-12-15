package com.julian.LinkedList;

import com.julian.tablaSimbolo.TablaSimbolo;

import java.util.LinkedList;

public class tableSimbolManage {

    private static LinkedList<TablaSimbolo> tablaSimbolos = new LinkedList<>();

    public static void addSimbol(TablaSimbolo simbol) {
        tablaSimbolos.add(simbol);
    }

    public static LinkedList<TablaSimbolo> getSimbols() {
        return tablaSimbolos;
    }

    public static void clearSimbols() {
        tablaSimbolos.clear();
    }
}
