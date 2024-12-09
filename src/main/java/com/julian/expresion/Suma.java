package com.julian.expresion;

import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.Arbol;
import com.julian.symbol.Tipo;
import com.julian.symbol.tablaSimbolo;
import com.julian.symbol.tipoDato;

/**
 * Clase que representa una expresión de suma.
 * Valor = Valor1 + Valor2
 */
public class Suma extends Instruccion {

    private Instruccion operIzq;
    private Instruccion operDer;

    /**
     * Constructor de la clase Suma.
     * -> tipo Tipo de dato de la expresión.
     * @param linea Linea en la que se encuentra la expresión.
     * @param columna Columna en la que se encuentra la expresión.
     * @param operIzq Operando izquierdo de la expresión.
     * @param operDer Operando derecho de la expresión.
     */
    public Suma(Instruccion operIzq, Instruccion operDer, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.operIzq = operIzq;
        this.operDer = operDer;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Se interpreta el operando izquierdo y derecho de la expresión.
        var valorIzq = operIzq.interpretar(arbol, tablaDeSimbolos);
        // Si el valor izquierdo es un error, se retorna.
        if(valorIzq instanceof Error) return valorIzq;

        var valorDer = operDer.interpretar(arbol, tablaDeSimbolos);
        if(valorDer instanceof Error) return valorDer;

        // Se obtiene el tipo de dato de los operandos.
        var tipoIzq = operIzq.tipo.getTipo();
        var tipoDer = operDer.tipo.getTipo();

        // Implementacion de tabla de operatorias para la suma.
        switch (tipoIzq){
            case ENTERO -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)valorIzq + (int)valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)valorIzq + (double)valorDer;
                    }
                    case BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        if ((boolean)valorDer){
                            return (int)valorIzq + 1;
                        }else{
                            return valorIzq;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)valorIzq + (char)valorDer;
                    }
                    case CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return valorIzq.toString() + valorDer.toString();
                    }
                    default -> {
                        return new Errores("Semantico", "Error en la suma, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)valorIzq + (int)valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)valorIzq + (double)valorDer;
                    }
                    case BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        if ((boolean)valorDer){
                            return (double)valorIzq + 1;
                        }else{
                            return valorIzq;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)valorIzq + (char)valorDer;
                    }
                    case CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return valorIzq.toString() + valorDer.toString();
                    }
                    default -> {
                        return new Errores("Semantico", "Error en la suma, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        if ((boolean)valorIzq){
                            return 1 + (int)valorDer;
                        }else{
                            return valorDer;
                        }
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        if ((boolean)valorIzq){
                            return 1 + (double)valorDer;
                        }else{
                            return valorDer;
                        }
                    }
                    case CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        String cadenaBol = (boolean)valorIzq ? "true" : "false";
                        String cadenaBin;
                        if ((boolean)valorIzq){
                            cadenaBin = "1";
                        }else{
                            cadenaBin = "0";
                        }
                        cadenaBol = cadenaBol + valorDer.toString();
                        cadenaBin = cadenaBin + valorDer.toString();
                        return cadenaBol + "\n" + cadenaBin;
                    }
                    default -> {
                        return new Errores("Semantico", "Error en la suma, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipoDer){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);

                        char charValue = getCharValue(valorIzq);

                        return charValue  + (int)valorDer;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);

                        char charValue = getCharValue(valorIzq);

                        return charValue + (double)valorDer;
                    }
                    case CARACTER, CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return valorIzq.toString() + valorDer.toString();
                    }
                    default -> {
                        return new Errores("Semantico", "Error en la suma, tipo de dato no valido", this.linea, this.columna);
                    }
                }
            }
            case CADENA -> {
                this.tipo.setTipo(tipoDato.CADENA);
                return valorIzq.toString() + valorDer.toString();
            }
            default -> {
                return new Errores("Semantico", "Error en la suma, tipo de dato no valido", this.linea, this.columna);
            }
        }
    }

    private static char getCharValue(Object valorIzq) {
        char charValue;

        if (valorIzq instanceof String && ((String) valorIzq).length() == 1) {
            // Convierte un String de un solo carácter a un char
            charValue = ((String) valorIzq).charAt(0);
        } else if (valorIzq instanceof Character) {
            // Si ya es un Character
            charValue = (char) valorIzq;
        } else {
            throw new IllegalArgumentException("valorIzq debe ser un carácter o una cadena de un carácter");
        }
        return charValue;
    }
}
