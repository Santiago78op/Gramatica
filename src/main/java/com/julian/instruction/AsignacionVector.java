package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.expresion.Nativo;
import com.julian.expresion.Negacion;
import com.julian.symbol.*;

import java.util.LinkedList;

/**
 * La asignacion de Vectores se hace de la siguiente forma:
 * -> id [expresion] = expresion;
 * -> id [expresion][expresion] = expresion;
 * -> numeros[0] = 10;
 * -> numeros[1][2] = 17;
 */
public class AsignacionVector extends Instruccion {

    private final String id;
    private final Instruccion index;
    private final Instruccion nestedIndex;
    private final Instruccion value;

    /**
     * Constructor de la clase
     * @param id Identificador del vector
     * @param index Indice del vector
     * @param value Valor a asignar
     * @param linea Línea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     */
    public AsignacionVector(String id, Instruccion index, Instruccion value, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.index = index;
        this.nestedIndex = null;
        this.value = value;
    }

    /**
     * Constructor de la clase
     * @param id Identificador del vector
     * @param index Indice del vector
     * @param nestedIndex Indice anidado del vector
     * @param value Valor a asignar
     * @param linea Linea donde se encuentra la instrucción
     * @param columna Columna donde se encuentra la instrucción
     */
    public AsignacionVector(String id, Instruccion index, Instruccion nestedIndex, Instruccion value, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.index = index;
        this.nestedIndex = nestedIndex;
        this.value = value;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        var simbolo = tablaDeSimbolos.getVariable(this.id);
        if (simbolo == null) {
            return addSemanticError(this.id, this.linea, this.columna);
        }

        // Validar si el Vector es de tipo constante o no
        if (simbolo.isConstante()){
            semanticErrorManager.addError(new Errores("Semantico", "El Vector " + this.id + " es constante y no se puede modificar", this.linea, this.columna));
            return new Errores("Semantico", "El Vector " + this.id + " es constante y no se puede modificar", this.linea, this.columna);
        }

        var valor = simbolo.getValor();
        if (valor instanceof Vector) {
            asignarValorVector(arbol, tablaDeSimbolos, (Vector) valor);
            this.tipo.setTipo(simbolo.getTipo().getTipo());
            simbolo.setValor(valor);
            return null;
        } else if (valor instanceof MultiDimensionalVector) {
            asignarValorMultiDimensionalVector(arbol, tablaDeSimbolos, (MultiDimensionalVector) valor);
            this.tipo.setTipo(simbolo.getTipo().getTipo());
            simbolo.setValor(valor);
            return null;
        }

        return addSemanticError(this.id, this.linea, this.columna);
    }

    private Object asignarValorVector(Arbol arbol, tablaSimbolo tablaDeSimbolos, Vector vector) {
        var indice = this.index.interpretar(arbol, tablaDeSimbolos);
        if (indice instanceof Errores) {
            return indice;
        }
        if (!(indice instanceof Integer)) {
            return new Errores("Semantico", "El indice del vector debe ser de tipo entero", this.linea, this.columna);
        }
        var index = (int) indice;
        if (index < 0 || index >= vector.getValores().size()) {
            return new Errores("Semantico", "El indice del vector esta fuera de rango", this.linea, this.columna);
        }
        var valorAsignar = this.value.interpretar(arbol, tablaDeSimbolos);
        if (valorAsignar instanceof Errores) {
            return valorAsignar;
        }
        var valorVector = vector.getValores().get(index);
        if(valorVector instanceof Nativo){
            // Actualizamos el tipo
            this.tipo.setTipo(tipoDato.getType(valorVector));
            // Actualizar el valor del Nativo
            ((Nativo) valorVector).setValor(valorAsignar);
        }else if (valorVector instanceof Negacion) {
            var negacion = ((Negacion) valorVector).getOper();
            if (negacion instanceof Nativo) {
                this.tipo.setTipo(((Nativo) negacion).getTipo().getTipo());
            } else if (negacion instanceof Negacion) {
                this.tipo.setTipo(((Negacion) negacion).getTipo().getTipo());
            }
            // Actualizar el valor de la negacion
            ((Negacion) valorVector).setOper(valorAsignar);
        }
        return null;
    }

    private Object asignarValorMultiDimensionalVector(Arbol arbol, tablaSimbolo tablaDeSimbolos, MultiDimensionalVector vector) {
        var indice = this.index.interpretar(arbol, tablaDeSimbolos);
        if (indice instanceof Errores) {
            return indice;
        }
        if (!(indice instanceof Integer)) {
            return new Errores("Semantico", "El indice del vector debe ser de tipo entero", this.linea, this.columna);
        }
        var index = (int) indice;
        if (index < 0 || index >= vector.getValores().size()) {
            return new Errores("Semantico", "El indice del vector esta fuera de rango", this.linea, this.columna);
        }

        var valorVector = vector.getValores().get(index);
        if (valorVector instanceof LinkedList) {
            // Acceso a la lista
            var lista = valorVector;
            // Verificar si hay un indice anidado
            if (lista.get(0) instanceof Vector) {
                // Validamos el indice anidado
                if (this.nestedIndex != null) {
                   // Acceso a la listan del indice anidado
                    var nestedIndex = this.nestedIndex.interpretar(arbol, tablaDeSimbolos);

                    if (nestedIndex instanceof Errores) {
                        return nestedIndex;
                    }

                    if (!(nestedIndex instanceof Integer)) {
                        return new Errores("Semantico", "El indice del vector debe ser de tipo entero", this.linea, this.columna);
                    }

                    var lista2 =  lista.get(0);
                    if (lista2 instanceof Vector) {
                        var vector2 = (Vector) lista2;
                        var index2 = (int) nestedIndex;
                        if (index2 < 0 || index2 >= vector2.getValores().size()) {
                            return new Errores("Semantico", "El indice del vector esta fuera de rango", this.linea, this.columna);
                        }
                        var valorAsignar = this.value.interpretar(arbol, tablaDeSimbolos);
                        if (valorAsignar instanceof Errores) {
                            return valorAsignar;
                        }

                        var valorVector2 = vector2.getValores().get(index);
                        if(valorVector2 instanceof Nativo){
                            // Actualizamos el tipo
                            this.tipo.setTipo(tipoDato.getType(valorVector2));
                            // Actualizar el valor del Nativo
                            ((Nativo) valorVector2).setValor(valorAsignar);
                        } else if (valorVector2 instanceof Negacion) {
                            var negacion = ((Negacion) valorVector2).getOper();
                            if (negacion instanceof Nativo) {
                                this.tipo.setTipo(((Nativo) negacion).getTipo().getTipo());
                            } else if (negacion instanceof Negacion) {
                                this.tipo.setTipo(((Negacion) negacion).getTipo().getTipo());
                            }
                            // Actualizar el valor de la negacion
                            ((Negacion) valorVector2).setOper(valorAsignar);
                        }

                        return null;
                    }

                } else {
                    var valorAsignar = this.value.interpretar(arbol, tablaDeSimbolos);
                    if (valorAsignar instanceof Errores) {
                        return valorAsignar;
                    }
                    if (valorAsignar instanceof Vector) {
                        // Actualizamos el valor de valorVector
                        lista.set(0, valorAsignar);
                    }
                    return null;
                }
                return new Errores("Semantico", "El valor no es un vector multidimensional", this.linea, this.columna);
            }
        }
        return new Errores("Semantico", "El valor no es un vector multidimensional", this.linea, this.columna);
    }

    private Errores addSemanticError(String id, int linea, int columna) {
        Errores error = new Errores("Semantico", "La variable " + id + " no existe en la tabla de simbolos", linea, columna);
        semanticErrorManager.addError(error);
        return error;
    }
}
