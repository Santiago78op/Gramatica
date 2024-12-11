package com.julian.expresion;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

public class Not extends Instruccion {

    private Instruccion operando;

    public Not(Instruccion operando, int linea, int columna) {
        super(new Tipo(tipoDato.BOOLEANO), linea, columna);
        this.operando = operando;
    }


    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        var valor = operando.interpretar(arbol, tablaDeSimbolos);
        if(valor instanceof Error) return valor;

        var tipo = operando.tipo.getTipo();
        switch (tipo){
            case BOOLEANO -> {
                this.tipo.setTipo(tipoDato.BOOLEANO);
                return !(boolean)valor;
            }
            default -> {
                addSemanticError(tipo, linea, columna);
                return new Errores("Semantico", "Error de tipos en la operación not.", linea, columna);
            }
        }
    }

    // Metodo para agregar el error Semantico
    private void addSemanticError(tipoDato tipo, int linea, int columna) {
        semanticErrorManager errorSemantico = new semanticErrorManager();
        errorSemantico.addError(new Errores("Semantico", "Error de tipos en la operación Not. " +
                "\n No se puede realizar Not en\n" + tipo, linea, columna));
    }
}
