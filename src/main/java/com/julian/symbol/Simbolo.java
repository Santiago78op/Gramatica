package com.julian.symbol;

/**
 * Simbolo -> Clase que representa un simbolo en la tabla de simbolos.
 * Ejemplo: nombreVariable -> Simbolo -> {id: nombreVariable, tipo: int, valor: 10}
 */
public class Simbolo {

    /**
     * El uso de la clase Tipo permite almacenar el tipo de dato de una variable.
     * El Tipo -> tipo permite almacenar si es Int, Double, String, Boolean, etc.
     * El String id permite almacenar el nombre de la variable.
     * El Object valor permite almacenar el valor de la variable.
     *      Este se declara como Object para poder almacenar cualquier tipo de dato.
     * El boolean constante permite almacenar si la variable es constante o no.
     *      Se inicializa en false, lo que significa que la variable no es constante.
     */
    private Tipo tipo;
    private String id;
    private Object valor;
    private String ambito;
    private String tipoDato;
    private int linea;
    private int columna;
    private boolean constante;

    /**
     * Constructor de la clase Simbolo.
     * @param tipo Tipo de dato.
     * @param id Nombre de la variable.
     * @param valor Valor de la variable.
     * @param constante Si la variable es constante o no.
     */
    public Simbolo(Tipo tipo, String id, Object valor, boolean constante, String ambito, String tipoDato, int linea, int columna) {
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
        this.constante = constante;
        this.ambito = ambito;
        this.tipoDato = tipoDato;
        this.linea = linea;
        this.columna = columna;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public void setConstante(boolean constante) {
        this.constante = constante;
    }

    public boolean isConstante() {
        return constante;
    }

    public boolean isMutable() {
        return !constante;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "Simbolo{" +
                "tipo=" + tipo +
                ", id='" + id + '\'' +
                ", valor=" + valor +
                ", ambito='" + ambito + '\'' +
                ", tipoDato='" + tipoDato + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                ", constante=" + constante +
                '}';
    }
}
