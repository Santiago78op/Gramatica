package com.julian.LinkedList;

import com.julian.exception.Errores;

import java.util.LinkedList;

public class semanticErrorManager {

    private LinkedList<Errores> erroresSemanticos;

    public semanticErrorManager() {
        erroresSemanticos = new LinkedList<>();
    }

    public void addError(Errores error) {
        erroresSemanticos.add(error);
    }

    public LinkedList<Errores> getErroresSemanticos() {
        return erroresSemanticos;
    }

    public void setErroresSemanticos(LinkedList<Errores> erroresSemanticos) {
        this.erroresSemanticos = erroresSemanticos;
    }
}
