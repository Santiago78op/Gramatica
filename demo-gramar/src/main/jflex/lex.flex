
/*
 Siemprer se debe definir el paquete
 en el que se encuentra la clase Lexer,
 esto es importante para que el analizador
 sintactico pueda encontrar la clase Lexer.
*/
package com.julian;

/*
    Importamos las clases necesarias para el
    funcionamiento del analizador lexico.
*/

// Cup es la clase que contiene los simbolos que
// se usan en el analizador sintactico.
import java_cup.runtime.*;

// Estas listas son necesarias para guardar los
// tokens y errores lexicos.
import java.util.ArrayList;
import java.util.List;

%% //inicio de opciones.

/* ----------- Seccion de opciones y declaraciones de JFlex -------------- */

%public // Hace que la clase sea publica.

/*
    Cambiamos el nombre de la clase del analizador a Lexer.
*/
%class Lexer

/*
    Usar Unicode para las expresiones regulares y
    el codigo generado por JFlex .
*/
%unicode

/*
    Activar el contador de lineas, variable yyline.
    Activar el contador de columna, variable yycolumn.
*/
%line
%column

/*
    Activamos la compatibilidad con Java CUP para analizadores
    sintacticos(parser).
*/

%cup

/*
    Declaraciones

    El codigo entre %{ y %} sera copiado integramente en el
    analizador generado.
*/
%{

    StringBuffer string = new StringBuffer();

    public List<Token> tokens = new ArrayList<>();
    public List<String> lexicalErrors = new ArrayList<>();

    /*
        Generamos un java_cup.Symbol para guardar el tipo de token
        encontrado.
     */
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }

    /*
        Generamos un Symbol para el tipo de token encontrado
        junto con su valor.
     */
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }

    private void addToken(String type, String value) {
        tokens.add(new Token(type, value, yyline+1, yycolumn+1, value.length()));
    }

    private void addLexicalError(String message) {
       lexicalErrors.add("Lexical error: " + message + " at line " + yyline + ", column " + yycolumn);
     }

%}

/*
    Macro declaraciones

    Declaramos expresiones regulares que despues usaremos en las
    reglas lexicas.
*/

/*
    Definimos un salto de linea como \n, \r o \r\n dependiendo del SO.
*/
delim = [ \t\n\r]
ws = {delim}+
/*
    Letra es un caracter entre a y z o entre A y Z.
*/
letter = [a-zA-Z]
/*
    Definimos un digito como un caracter entre 0 y 9.
*/
digit = [0-9]
/*
    Definimos un identificador como una letra seguida de cero o mas
    letras o digitos.
*/
id = {letter}({letter}|{digit})*
/*
    Definimos un numero como uno o mas digitos.
*/
num = {digit}+(\.{digit}+)?([eE][+-]?{digit}+)?

%% // fin de opciones.

/* ------------------- Reglas Lexicas ------------------- */

/*
    Esta seccion contiene expresiones regulares y acciones.
    Las acciones son código en Java que se ejecutara cuando se
    encuentre una entrada valida para la expresion regular
    correspondiente.
*/

/*
    YYINITIAL es el estado inicial del analizador lexico al escanear.
    Las expresiones regulares solo serán comparadas si se encuentra
    en ese estado inicial. Es decir, cada vez que se encuentra una
    coincidencia el scanner vuelve al estado inicial. Por lo cual se
    ignoran estados intermedios.
*/

<YYINITIAL> {
    {ws}    { /* ignore whitespace */ }
    CONJ    { addToken("CONJ", yytext()); return symbol(sym.CONJ); }
    OPERA   { addToken("OPERA", yytext()); return symbol(sym.OPERA); }
    EVALUAR { addToken("EVALUAR", yytext()); return symbol(sym.EVALUAR); }
    {id}    { addToken("ID", yytext()); return symbol(sym.ID, yytext()); }
    {num}   { addToken("NUM", yytext()); return symbol(sym.NUM, Double.parseDouble(yytext())); }
    "{"     { addToken("{", yytext()); return symbol(sym.LBRACE); }
    "}"     { addToken("}", yytext()); return symbol(sym.RBRACE); }
    "("     { addToken("(", yytext()); return symbol(sym.LPAREN); }
    ")"     { addToken(")", yytext()); return symbol(sym.RPAREN); }
    "#"     { addToken("#", yytext()); return symbol(sym.HASH); }
    "<!"    { addToken("<!", yytext()); return symbol(sym.LT_EXCL); }
    "!>"    { addToken("!>", yytext()); return symbol(sym.EXCL_GT); }
    "->"    { addToken("->", yytext()); return symbol(sym.ARROW); }
    ";"     { addToken(";", yytext()); return symbol(sym.SEMICOLON); }
    ":"     { addToken(":", yytext()); return symbol(sym.COLON); }
    "~"     { addToken("~", yytext()); return symbol(sym.VIRGULILLA); }
    ","     { addToken(",", yytext()); return symbol(sym.COMMA); }
    "U"     { addToken("U", yytext()); return symbol(sym.UNION); }
    "&"     { addToken("&", yytext()); return symbol(sym.INTERSECCION); }
    "^"     { addToken("!", yytext()); return symbol(sym.COMPLEMENTO); }
    "-"     { addToken("^", yytext()); return symbol(sym.DIFERENCIA); }
}

/*
    Si el token contenido en la entrada no coincide con ninguna regla
    entonces se marca un token ilegal
*/
[^] { addLexicalError("Illegal character: " + yytext()); }