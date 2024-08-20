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
    The code enclosed in and is copied verbatim into the
    constructor of the generated class. Here, member variables
     declared in the directive can be initialised.*/
%init{
    yycolumn = 1;
%init}

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

    public List<Token>       tokens = new ArrayList<>();
    public List<LexError> lexErrors = new ArrayList<>();

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
        tokens.add(new Token(type, value, yyline, yycolumn, value.length()));
    }

    private void addLexicalError(String message) {
       String description = "El caracter " + message + " no pertenece al lenguaje.";
       lexErrors.add(new LexError("Lexico", description, yyline, yycolumn));
     }

%}

%eofval{
    return symbol(sym.EOF);
%eofval}

/*
    Macro declaraciones

    Declaramos expresiones regulares que despues usaremos en las
    reglas lexicas.
*/

// Definimos comentarios
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
ws             = {LineTerminator} | [ \t\f]

/* comments */
Comment = {LineComment} | {MultiLineComment}

// Comentarios de una solo linea con #
LineComment      = "#" {InputCharacter}* {LineTerminator}?
MultiLineComment = "<!" {CommentContent} "!>"
CommentContent   = ([^!] | \!+ [^>])*

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

%state STRING

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

/* Reglas de Operaciones */
<YYINITIAL> CONJ    { addToken("CONJ", yytext()); return symbol(sym.CONJ); }
<YYINITIAL> OPERA   { addToken("OPERA", yytext()); return symbol(sym.OPERA); }
<YYINITIAL> EVALUAR { addToken("EVALUAR", yytext()); return symbol(sym.EVALUAR); }

/* Reglas de opereaciones Conjuntos */
<YYINITIAL>  U      { addToken("U", yytext()); return symbol(sym.UNION); }
<YYINITIAL>  &      { addToken("&", yytext()); return symbol(sym.INTERSECCION); }
<YYINITIAL> \^      { addToken("^", yytext()); return symbol(sym.COMPLEMENTO); }
<YYINITIAL>  -      { addToken("-", yytext()); return symbol(sym.DIFERENCIA); }

<YYINITIAL>{
    /* Reglas de Caracteres */
    {id}        { addToken("ID", yytext()); return symbol(sym.ID, yytext()); }
    {num}       { addToken("NUM", yytext()); return symbol(sym.NUM, Double.parseDouble(yytext())); }

    /* Operadores */
    "{"         { addToken("{", yytext()); return symbol(sym.LBRACE); }
    "}"         { addToken("}", yytext()); return symbol(sym.RBRACE); }
    "("         { addToken("(", yytext()); return symbol(sym.LPAREN); }
    ")"         { addToken(")", yytext()); return symbol(sym.RPAREN); }
    "->"        { addToken("->", yytext()); return symbol(sym.ARROW); }
    ";"         { addToken(";", yytext()); return symbol(sym.SEMICOLON); }
    ":"         { addToken(":", yytext()); return symbol(sym.COLON); }
    "~"         { addToken("~", yytext()); return symbol(sym.VIRGULILLA); }
    ","         { addToken(",", yytext()); return symbol(sym.COMMA); }

    /* Reglas de Comentarios */
    {Comment}    { /* ignora comentarios de una linea */ }

    /* Reglas de espcios */
    {ws}        { /* ignore whitespace */ }
}

/* Reglas para cadenas de caracteres */
<STRING> {
    \"                             { yybegin(YYINITIAL);
                                   return symbol(sym.STRING_LITERAL,
                                   string.toString()); }
    [^\n\r\"\\]+                   { string.append( yytext() ); }
    \\t                            { string.append('\t'); }
    \\n                            { string.append('\n'); }

    \\r                            { string.append('\r'); }
    \\\"                           { string.append('\"'); }
    \\                             { string.append('\\'); }
}

/*
    Si el token contenido en la entrada no coincide con ninguna regla
    entonces se marca un token ilegal
*/
[^] { addLexicalError("Illegal character: " + yytext()); }