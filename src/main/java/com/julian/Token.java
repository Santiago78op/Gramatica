package com.julian;

public class Token {

    private String type;
    private String value;
    private int line;
    private int column;
    private int length;

    public Token(String type, String value, int line, int column, int length) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
        this.length = length;
    }

    public String getType() {
        return type;
    }
    public String getValue() {
        return value;
    }
    public int getLine() {
        return line;
    }
    public int getColumn() {
        return column;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", line=" + line +
                ", column=" + column +
                ", length=" + length +
                '}';
    }

}
