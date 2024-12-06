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
    Ignorar mayusculas y minusculas en las expresiones
    regulares.
*/
%ignorecase

/*
    El debug activa la generacion de un archivo .dbg,
    esto para depurar el analizador lexico.
*/
%debug

/*
    Activamos la compatibilidad con Java CUP para analizadores
    sintacticos(parser).
*/
%cup

/*
    Activar el contador de lineas, variable yyline.
    Activar el contador de columna, variable yycolumn.
*/
%line
%column

/*
    El código encerrado en %init{ y %} se copia literalmente en el
    constructor de la clase generada. Aquí, las variables miembro
    declaradas en la directiva pueden ser inicializadas.
*/
%init{
    yyline = 1;
    yycolumn = 1;
%init}

/*
    Declaraciones

    El codigo entre %{ y %} sera copiado integramente en el
    analizador generado.
*/
%{

  // StringBuffer para almacenar los lexemas.
  StringBuffer lexeme = new StringBuffer();

  // Lista de tokens
  public List<Token> tokens = new ArrayList<Token>();
  // Lista de errores lexicos
  public List<LexError> errors = new ArrayList<LexError>();

  /*
    Metodo symbol, parametro token: su funcion es
    crear un objeto Symbol con los parametros token,
    yyline y yycolumn.
   */
  private Symbol symbol(int type){
    return new Symbol(type, yyline, yycolumn);
  }

  /*
    Metodo symbol, parametros token y value: su funcion es
    crear un objeto Symbol con los parametros token,
    yyline, yycolumn y value.
   */
  private Symbol symbol(int type, Object value){
    return new Symbol(type, yyline, yycolumn, value);
  }

  // Metodo para agregar tokens a la lista de tokens.
  private void addToken(String type, String value){
      tokens.add(new Token(type, value, yyline, yycolumn, value.length()));
  }

  // Metodo para agregar errores lexicos.
  private void addError(String error){
      String description = "";
      errors.add(new LexError(error, description, yyline, yycolumn));
  }

%}

/*
    Macro declaraciones

    Declaramos expresiones regulares que despues usaremos en las
    reglas lexicas.
*/

// Definimos comentarios
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"

// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?


// Letra es un caracter entre a y z o entre A y Z.
letter = [a-zA-Z]

// Definimos un digito como un caracter entre 0 y 9.
digit = [0-9]

/*
    Definimos un identificador como una letra seguida de cero o mas
    letras o digitos.
*/
guion = _
id = {guion}*{letter}({letter}|{digit}|{guion})*

// Definimos un numero como uno o mas digitos.
decimal = {digit}+(\.{digit}+)?([eE]?{digit}+)?

num = {digit}+(\.{digit}+)?([eE]?{digit}+)?

// Definimos un booleano como true o false.
bool = (true|false)

// Definimos un char como un valor que acepta un único carácter, incluyendo secuencias de escape.
char = \'([^\'\\]|\\[btnfr\"\'\\]|\\u[0-9a-fA-F]{4})\'

// Keywords
// int -> Int, int, inT, INt, ...
int    = "int"
double = "double"
bool   = "bool"
char   = "char"
string = "string"
cast   = "cast"
let    = "let"
const  = "const"
as     = "as"
if     = "if"
else   = "else"
match  = "match"
def    = "default"
while  = "while"
do     = "do"
for    = "for"
break  = "break"
consol = "console"
log    = "log"
conti  = "continue"
print  = "print"

// Estados del analizador lexico.
%state STRING_STATE
%state CHAR_STATE

%% // fin de opciones.

/* ------------------- Reglas Lexicas ------------------- */

/*
    Esta seccion contiene expresiones regulares y acciones.
    Las acciones son código en Java que se ejecutara cuando se
    encuentre una entrada valida para la expresion regular
    correspondiente.
*/

/* keywords */
<YYINITIAL> { int }    { addToken("INT",      yytext()); return symbol(sym.INT, yytext()); }
<YYINITIAL> { double } { addToken("FLOAT",    yytext()); return symbol(sym.FLOAT, yytext()); }
<YYINITIAL> { bool }   { addToken("BOOL",     yytext()); return symbol(sym.BOOL, yytext()); }
<YYINITIAL> { char }   { addToken("CHAR",     yytext()); return symbol(sym.CHAR, yytext()); }
<YYINITIAL> { string } { addToken("STRING",   yytext()); return symbol(sym.STRING, yytext()); }
<YYINITIAL> { cast }   { addToken("CAST",     yytext()); return symbol(sym.CAST, yytext()); }
<YYINITIAL> { let }    { addToken("LET",      yytext()); return symbol(sym.LET, yytext()); }
<YYINITIAL> { const }  { addToken("CONST",    yytext()); return symbol(sym.CONST, yytext()); }
<YYINITIAL> { as }     { addToken("AS",       yytext()); return symbol(sym.AS, yytext()); }
<YYINITIAL> { if }     { addToken("IF",       yytext()); return symbol(sym.IF, yytext()); }
<YYINITIAL> { else }   { addToken("ELSE",     yytext()); return symbol(sym.ELSE, yytext()); }
<YYINITIAL> { match }  { addToken("MATCH",    yytext()); return symbol(sym.MATCH, yytext()); }
<YYINITIAL> { def }    { addToken("DEFAULT",  yytext()); return symbol(sym.DEFAULT, yytext()); }
<YYINITIAL> { while }  { addToken("WHILE",    yytext()); return symbol(sym.WHILE, yytext()); }
<YYINITIAL> { do }     { addToken("DO",       yytext()); return symbol(sym.DO, yytext()); }
<YYINITIAL> { for }    { addToken("FOR",      yytext()); return symbol(sym.FOR, yytext()); }
<YYINITIAL> { break }  { addToken("BREAK",    yytext()); return symbol(sym.BREAK, yytext()); }
<YYINITIAL> { consol } { addToken("CONSOLE",  yytext()); return symbol(sym.CONSOLE, yytext()); }
<YYINITIAL> { log }    { addToken("LOG",      yytext()); return symbol(sym.LOG, yytext()); }
<YYINITIAL> { conti }  { addToken("CONTINUE", yytext()); return symbol(sym.CONTINUE, yytext()); }
<YYINITIAL> { print }  { addToken("PRINT",    yytext()); return symbol(sym.PRINT, yytext()); }

<YYINITIAL>{
    /* identifier, number y boolean */
    { id }      { addToken("ID",      yytext()); return symbol(sym.ID, yytext()); }
    { num }     { addToken("NUM",     yytext()); return symbol(sym.NUM, Integer.parseInt(yytext())); }
    { decimal } { addToken("DECIMAL", yytext()); return symbol(sym.DECIMAL, Double.parseDouble(yytext())); }
    { bool }    { addToken("BOOLEANO",yytext()); return symbol(sym.BOOLEANO, yytext()); }

    /* arithmetic operators */
    "+" { addToken("ADD",  yytext()); return symbol(sym.ADD, yytext()); }
    "-" { addToken("SUB",  yytext()); return symbol(sym.SUB, yytext()); }
    "*" { addToken("MUL",  yytext()); return symbol(sym.MUL, yytext()); }
    "/" { addToken("DIV",  yytext()); return symbol(sym.DIV, yytext()); }
    "^" { addToken("POW",  yytext()); return symbol(sym.POW, yytext()); }
    "$" { addToken("ROOT", yytext()); return symbol(sym.ROOT, yytext()); }
    "%" { addToken("MOD",  yytext()); return symbol(sym.MOD, yytext()); }

    /* relational operators */
    "="    { addToken("EQ", yytext()); return symbol(sym.EQ, yytext()); }
    "!="   { addToken("NE", yytext()); return symbol(sym.NE, yytext()); }
    "<"    { addToken("LT", yytext()); return symbol(sym.LT, yytext()); }
    "<="   { addToken("LE", yytext()); return symbol(sym.LE, yytext()); }
    ">"    { addToken("GT", yytext()); return symbol(sym.GT, yytext()); }
    ">="   { addToken("GE", yytext()); return symbol(sym.GE, yytext()); }

    /* logical operators */
    "||"   { addToken("OR",  yytext()); return symbol(sym.OR, yytext()); }
    "&&"   { addToken("AND", yytext()); return symbol(sym.AND, yytext()); }
    "!"    { addToken("NOT", yytext()); return symbol(sym.NOT, yytext()); }

    /* caracteres del lenguaje */
    "("    { addToken("LPAREN",    yytext()); return symbol(sym.LPAREN, yytext()); }
    ")"    { addToken("RPAREN",    yytext()); return symbol(sym.RPAREN, yytext()); }
    "{"    { addToken("LBRACE",    yytext()); return symbol(sym.LBRACE, yytext()); }
    "}"    { addToken("RBRACE",    yytext()); return symbol(sym.RBRACE, yytext()); }
    ";"    { addToken("SEMICOLON", yytext()); return symbol(sym.SEMICOLON, yytext()); }
    ":"    { addToken("COLON",     yytext()); return symbol(sym.COLON, yytext()); }
    "."    { addToken("DOT",       yytext()); return symbol(sym.DOT, yytext()); }

    // Detectar incio de un cadena
    "\""     { yybegin(STRING_STATE); }

    // Detectar inicio de un char.
    "\'"     { yybegin(CHAR_STATE); }

    // Detectar comentario
    { Comment } { /* ignore */ }

    // Detectar espacios en blanco
    { WhiteSpace } { /* ignore */ }
}

<STRING_STATE>{
    // Detectar fin de una cadena
    "\""        { yybegin(YYINITIAL); addToken("CADENA", lexeme.toString()); return symbol(sym.CADENA, lexeme.toString()); }
    // Caracteres validos en una cadena
    [^\n\r\"\\] { lexeme.append(yytext()); }
    // Secuencias de escape
    "\\".       { lexeme.append(yytext()); yybegin(STRING_STATE); }
    // Error en una cadena
    [\n\r]      { addError("Error: Caracter invalido en una cadena"); }
}

<CHAR_STATE>{
    // Detectar fin de un char
    "\'"      { yybegin(YYINITIAL); addToken("CARACTER", lexeme.toString()); return symbol(sym.CARACTER, lexeme.toString()); }
    // Caracteres validos en un char
    { char }  { lexeme.append(yytext()); }
    // Secuencias de escape
    "\\".     { lexeme.append(yytext()); yybegin(CHAR_STATE); }
    // Error en un char
    [\n\r]    { addError("Error: Caracter invalido en un char"); }
}