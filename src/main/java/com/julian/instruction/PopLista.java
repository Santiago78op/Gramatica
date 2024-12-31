package com.julian.instruction;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.io.CharArrayReader;
import java.util.LinkedList;

public class PopLista extends Instruccion {

    private String id;

    public PopLista(String id, int linea, int columna) {
        super(new Tipo(tipoDato.LISTA), linea, columna);
        this.id = id;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Obtener la lista de la tabla de símbolos
        Simbolo simbolo = tablaDeSimbolos.getVariable(id);
        if (simbolo == null) {
            return new Errores("Semantico", "La lista " + id + " no existe", linea, columna);
        }

        // Verificar que el símbolo sea una lista
        if (!(simbolo.getValor() instanceof LinkedList)) {
            return new Errores("Semantico", id + " no es una lista", linea, columna);
        }

        LinkedList<Object> lista = (LinkedList<Object>) simbolo.getValor();

        // Verificar que la lista no esté vacía
        if (lista.isEmpty()) {
            return new Errores("Semantico", "La lista " + id + " está vacía", linea, columna);
        }


        // Obtenemos el ultimo elemento de la lista
        Object valor = lista.getLast();
        Object nuevoTipo = null;
        nuevoTipo = Tipo.validarTipo(simbolo.getTipoDato().toString());
        // Actulizar el tipo de la variable
        this.tipo.setTipo(((Tipo) nuevoTipo).getTipo());
        // Remover el último elemento de la lista
        lista.removeLast();
        return valor;
    }
}
