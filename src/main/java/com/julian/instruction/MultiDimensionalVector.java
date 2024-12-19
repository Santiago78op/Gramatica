package com.julian.instruction;

import java.util.LinkedList;
import java.util.List;

/**
 * Esta clase esta diseñada para manejar vectores multidimensionales.
 * Ejemplo:
 * 1. Una dimencion    -> [1, 2, 3, 4, 5]
 * 2. Dos dimenciones  -> [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
 * 3. Tres dimenciones -> [[[1, 2, 3], [4, 5, 6], [7, 8, 9]], [[10, 11, 12], [13, 14, 15], [16, 17, 18]]]
 */
public class MultiDimensionalVector extends Vector{

    private LinkedList<Object> valores;

    public MultiDimensionalVector(LinkedList<Object> valores, int linea, int columna) {
        super(valores, linea, columna);
        this.valores = valores;
    }

    @Override
    public LinkedList<Object> getValores() {
        return valores;
    }

    @Override
    public void setValores(LinkedList<Object> valores) {
        this.valores = valores;
    }

    public List<Object> getVectors() {
        return valores;
    }
}
