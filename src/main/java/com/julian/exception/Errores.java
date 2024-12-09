package com.julian.exception;

/**
 * Error -> Clase que representa dos tipos de Error.
 * lexico y sintactico.
 */
public class Errores {

    private String tipo;
    private String desc;
    private int linea;
    private int columna;

    /**
     * Constructor de la clase Error.
     * @param tipo Tipo de error.
     * @param desc Descripción del error.
     * @param linea Linea en la que se encuentra el error.
     * @param columna Columna en la que se encuentra el error.
     */
    public Errores(String tipo, String desc, int linea, int columna) {
        this.tipo = tipo;
        this.desc = desc;
        this.linea = linea;
        this.columna = columna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
        return "Error{" +
                "tipo='" + tipo + '\'' +
                ", desc='" + desc + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                '}';
    }
}