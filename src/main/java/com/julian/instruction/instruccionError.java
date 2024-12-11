package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class instruccionError extends Instruccion {

    private String mensaje;

    public instruccionError(String mensaje){
        super(new Tipo(tipoDato.ERROR),0,0);
        this.mensaje = mensaje;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        return this.mensaje;
    }
}