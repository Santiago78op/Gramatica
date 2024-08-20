package com.julian;

public class LexError {

    private String tipo;
    private String message;
    private int line;
    private int column;

    public LexError(String tipo, String message, int line, int column) {
        this.tipo = tipo;
        this.message = message;
        this.line = line;
        this.column = column;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "LexicalError{" +
                "tipo='" + tipo + '\'' +
                ", message='" + message + '\'' +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
