package com.julian.LinkedList;

import com.julian.exception.Errores;

import java.util.LinkedList;

public class semanticErrorManager {

    private static LinkedList<Errores> erroresSemanticos = new LinkedList<>();

    public static void addError(Errores error) {
        erroresSemanticos.add(error);
    }

    public static LinkedList<Errores> getErrors() {
        return erroresSemanticos;
    }
}
