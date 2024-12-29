package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;
import com.julian.exception.Errores;

import java.util.LinkedList;

/**
 * Clase que ejecuta la instrucción de switch.
 * Ejemplo:
 * switch (expresion) {
 *    case 1: {
 *    // Instrucciones
 *    }
 *    case 2: {
 *    // Instrucciones
 *    }
 *    default: {
 *    // Instrucciones
 *    }
 */
public class Switch extends Instruccion {

    private Instruccion expresion;
    private LinkedList<Case> casos;
    private DefaultCase defecto;

    public Switch(Instruccion expresion, LinkedList<Case> casos, DefaultCase defecto, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.casos = casos;
        this.defecto = defecto;
    }

    public Switch(Instruccion expresion, LinkedList<Case> casos, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.casos = casos;
    }

    public Switch(Instruccion expresion, DefaultCase defecto, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.defecto = defecto;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se evalua la expresion del switch
        var exp = this.expresion.interpretar(arbol, tablaDeSimbolos);
        // Si la expresion es un error se retorna el error.
        if(exp instanceof Errores){
            return exp;
        }

        // Variable para validar que hiso match con algun caso
        boolean match = false;

        // tenemos un bloque de instrucciones para el switch
        var nuevaTabla = new tablaSimbolo(tablaDeSimbolos);

        // Se ejecutan los casos del switch, validando que la expresion sea igual a alguno de los casos
        if (this.casos != null) {
            for (Case caso : this.casos) {
                // Se extrae la expresion del caso
                var caseExp = caso.getExpresion();
                // Se evalua la expresion del caso
                var condExp = caseExp.interpretar(arbol, nuevaTabla);
                // Si la expresion es un error se retorna el error.
                if(condExp instanceof Errores){
                    return condExp;
                }

                // Se compara la expresion del switch con la expresion del caso
                if(this.expresion.tipo.getTipo() != caseExp.tipo.getTipo()){
                    semanticErrorManager.addError(new Errores("Semantico", "La expresion del switch y del case deben ser del mismo tipo", this.linea, this.columna));
                    return new Errores("Semantico", "La expresion del switch y del case deben ser del mismo tipo", this.linea, this.columna);
                }else {
                    // Si son del mismo tipo se comparan
                    switch (this.expresion.tipo.getTipo()) {
                        case ENTERO -> {
                            if ((int) exp == (int) condExp) {
                                match = true;
                                // Si son iguales se ejecutan las instrucciones del caso
                                for (Instruccion instruccion : caso.getInstrucciones()) {
                                    var result = instruccion.interpretar(arbol, nuevaTabla);
                                    if (result instanceof Errores) {
                                        arbol.addError((Errores) result);
                                    } else if (result instanceof Break) {
                                        return null; // Termina la ejecución del switch
                                    } else if (result instanceof Continue) {
                                        break; // Salta al siguiente caso
                                    } else if (result instanceof Return) {
                                        return result; // Retorna el valor de la expresion
                                    }
                                }
                            }
                        }
                        case DECIMAL -> {
                            if ((double) exp == (double) condExp) {
                                match = true;
                                // Si son iguales se ejecutan las instrucciones del caso
                                for (Instruccion instruccion : caso.getInstrucciones()) {
                                    var result = instruccion.interpretar(arbol, nuevaTabla);
                                    if (result instanceof Errores) {
                                        arbol.addError((Errores) result);
                                    } else if (result instanceof Break) {
                                        return null; // Termina la ejecución del switch
                                    } else if (result instanceof Continue) {
                                        break; // Salta al siguiente caso
                                    }
                                }
                            }
                        }
                        case CARACTER -> {
                            if ((char) exp == (char) condExp) {
                                match = true;
                                // Si son iguales se ejecutan las instrucciones del caso
                                for (Instruccion instruccion : caso.getInstrucciones()) {
                                    var result = instruccion.interpretar(arbol, nuevaTabla);
                                    if (result instanceof Errores) {
                                        arbol.addError((Errores) result);
                                    } else if (result instanceof Break) {
                                        return null; // Termina la ejecución del switch
                                    } else if (result instanceof Continue) {
                                        break; // Salta al siguiente caso
                                    }
                                }
                            }
                        }
                        case CADENA -> {
                            if (exp.equals(condExp)) {
                                match = true;
                                // Si son iguales se ejecutan las instrucciones del caso
                                for (Instruccion instruccion : caso.getInstrucciones()) {
                                    var result = instruccion.interpretar(arbol, nuevaTabla);
                                    if (result instanceof Errores) {
                                        arbol.addError((Errores) result);
                                    } else if (result instanceof Break) {
                                        return null; // Termina la ejecución del switch
                                    } else if (result instanceof Continue) {
                                        break; // Salta al siguiente caso
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Se ejecuta el caso por defecto
        if (!match && this.defecto != null) {
            var result = this.defecto.interpretar(arbol, nuevaTabla);
            if (result != null) {
                return result;
            }
        }

        return null;
    }
}
