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
import com.julian.symbol.Token;
import com.julian.exception.Errores;
import java_cup.runtime.*;
//importaciones si fuesen necesarias
import java_cup.runtime.Symbol;

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
      public List<Errores> errors = new ArrayList<Errores>();

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
          errors.add(new Errores(error, description, yyline, yycolumn));
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
decimal = {digit}+\.([eE]?{digit}+)?

entero = {digit}+([eE]?{digit}+)?

// Definimos una cadena como un conjunto de caracteres entre comillas dobles.
cadena = \"([^\"\\]|\\[btnfr\"\\]|\\u[0-9a-fA-F]{4})*\"

// Definimos un caracter como un valor que acepta un único carácter, incluyendo secuencias de escape.
caracter = \'([^\'\\]|\\[btnfr\"\'\\]|\\u[0-9a-fA-F]{4})\'

// Definimos un booleano como true o false.
boleano = (true|false)

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
<YYINITIAL> { int }    { addToken("INT",      yytext()); return new Symbol(sym.INT, yyline, yycolumn, yytext()); }
<YYINITIAL> { double } { addToken("FLOAT",    yytext()); return new Symbol(sym.FLOAT, yyline, yycolumn, yytext()); }
<YYINITIAL> { bool }   { addToken("BOOL",     yytext()); return new Symbol(sym.BOOL, yyline, yycolumn, yytext()); }
<YYINITIAL> { char }   { addToken("CHAR",     yytext()); return new Symbol(sym.CHAR, yyline, yycolumn, yytext()); }
<YYINITIAL> { string } { addToken("STRING",   yytext()); return new Symbol(sym.STRING, yyline, yycolumn, yytext()); }
<YYINITIAL> { cast }   { addToken("CAST",     yytext()); return new Symbol(sym.CAST, yyline, yycolumn, yytext()); }
<YYINITIAL> { let }    { addToken("LET",      yytext()); return new Symbol(sym.LET, yyline, yycolumn, yytext()); }
<YYINITIAL> { const }  { addToken("CONST",    yytext()); return new Symbol(sym.CONST, yyline, yycolumn, yytext()); }
<YYINITIAL> { as }     { addToken("AS",       yytext()); return new Symbol(sym.AS, yyline, yycolumn, yytext()); }
<YYINITIAL> { if }     { addToken("IF",       yytext()); return new Symbol(sym.IF, yyline, yycolumn, yytext()); }
<YYINITIAL> { else }   { addToken("ELSE",     yytext()); return new Symbol(sym.ELSE, yyline, yycolumn, yytext()); }
<YYINITIAL> { match }  { addToken("MATCH",    yytext()); return new Symbol(sym.MATCH, yyline, yycolumn, yytext()); }
<YYINITIAL> { def }    { addToken("DEFAULT",  yytext()); return new Symbol(sym.DEFAULT, yyline, yycolumn, yytext()); }
<YYINITIAL> { while }  { addToken("WHILE",    yytext()); return new Symbol(sym.WHILE, yyline, yycolumn,  yytext()); }
<YYINITIAL> { do }     { addToken("DO",       yytext()); return new Symbol(sym.DO, yyline, yycolumn, yytext()); }
<YYINITIAL> { for }    { addToken("FOR",      yytext()); return new Symbol(sym.FOR, yyline, yycolumn, yytext()); }
<YYINITIAL> { break }  { addToken("BREAK",    yytext()); return new Symbol(sym.BREAK, yyline, yycolumn, yytext()); }
<YYINITIAL> { consol } { addToken("CONSOLE",  yytext()); return new Symbol(sym.CONSOLE, yyline, yycolumn, yytext()); }
<YYINITIAL> { log }    { addToken("LOG",      yytext()); return new Symbol(sym.LOG, yyline, yycolumn, yytext()); }
<YYINITIAL> { conti }  { addToken("CONTINUE", yytext()); return new Symbol(sym.CONTINUE, yyline, yycolumn, yytext()); }
<YYINITIAL> { print }  { addToken("PRINT",    yytext()); return new Symbol(sym.PRINT, yyline, yycolumn, yytext()); }

<YYINITIAL>{
    /* number y boolean */
    { entero }  { addToken("NUM",     yytext()); return new Symbol(sym.ENTERO, yyline, yycolumn, yytext()); }
    { decimal } { addToken("DECIMAL", yytext()); return new Symbol(sym.DECIMAL, yyline, yycolumn, yytext()); }
    { boleano } { addToken("BOOLEANO",yytext()); return new Symbol(sym.BOOLEANO, yyline, yycolumn, yytext()); }

    /* arithmetic operators */
    "+" { addToken("ADD",  yytext()); return new Symbol(sym.ADD, yyline, yycolumn, yytext()); }
    "-" { addToken("SUB",  yytext()); return new Symbol(sym.SUB, yyline, yycolumn, yytext()); }
    "*" { addToken("MUL",  yytext()); return new Symbol(sym.MUL, yyline, yycolumn, yytext()); }
    "/" { addToken("DIV",  yytext()); return new Symbol(sym.DIV, yyline, yycolumn, yytext()); }
    "^" { addToken("POW",  yytext()); return new Symbol(sym.POW, yyline, yycolumn, yytext()); }
    "$" { addToken("ROOT", yytext()); return new Symbol(sym.ROOT, yyline, yycolumn, yytext()); }
    "%" { addToken("MOD",  yytext()); return new Symbol(sym.MOD, yyline, yycolumn, yytext()); }

    /* relational operators */
    "=="   { addToken("EQ", yytext()); return new Symbol(sym.EQ, yyline, yycolumn, yytext()); }
    "!="   { addToken("NE", yytext()); return new Symbol(sym.NE, yyline, yycolumn, yytext()); }
    "<"    { addToken("LT", yytext()); return new Symbol(sym.LT, yyline, yycolumn, yytext()); }
    "<="   { addToken("LE", yytext()); return new Symbol(sym.LE, yyline, yycolumn, yytext()); }
    ">"    { addToken("GT", yytext()); return new Symbol(sym.GT, yyline, yycolumn, yytext()); }
    ">="   { addToken("GE", yytext()); return new Symbol(sym.GE, yyline, yycolumn, yytext()); }

    /* logical operators */
    "||"   { addToken("OR",  yytext()); return new Symbol(sym.OR, yyline, yycolumn, yytext()); }
    "&&"   { addToken("AND", yytext()); return new Symbol(sym.AND, yyline, yycolumn, yytext()); }
    "!"    { addToken("NOT", yytext()); return new Symbol(sym.NOT, yyline, yycolumn, yytext()); }

    /* caracteres del lenguaje */
    "="    { addToken("ASSIGN",    yytext()); return new Symbol(sym.ASSIGN, yyline, yycolumn, yytext()); }
    "("    { addToken("LPAREN",    yytext()); return new Symbol(sym.LPAREN, yyline, yycolumn, yytext()); }
    ")"    { addToken("RPAREN",    yytext()); return new Symbol(sym.RPAREN, yyline, yycolumn, yytext()); }
    "{"    { addToken("LBRACE",    yytext()); return new Symbol(sym.LBRACE, yyline, yycolumn, yytext()); }
    "}"    { addToken("RBRACE",    yytext()); return new Symbol(sym.RBRACE, yyline, yycolumn, yytext()); }
    ";"    { addToken("SEMICOLON", yytext()); return new Symbol(sym.SEMICOLON, yyline, yycolumn, yytext()); }
    ":"    { addToken("COLON",     yytext()); return new Symbol(sym.COLON, yyline, yycolumn, yytext()); }
    "."    { addToken("DOT",       yytext()); return new Symbol(sym.DOT, yyline, yycolumn, yytext()); }

    // Detectar cadenas entre comillas
    { cadena } {
            String cadena = yytext();
            cadena = cadena.substring(1, cadena.length() - 1); // Quitar comillas
            addToken("CADENA", yytext());
            return new Symbol(sym.CADENA, yyline, yycolumn, cadena);
        }

    // Detectar caracteres entre comillas
    { caracter } {
            String caracter = yytext();
            caracter = caracter.substring(1, caracter.length() - 1); // Quitar comillas
            addToken("CARACTER", yytext());
            return new Symbol(sym.CARACTER, yyline, yycolumn, caracter);
        }

    // Identifier
    { id }      { addToken("ID",      yytext()); return new Symbol(sym.ID, yyline, yycolumn, yytext()); }

    // Detectar comentario
    { Comment } { /* ignore */ }

    // Detectar espacios en blanco
    { WhiteSpace } { /* ignore */ }
}

<YYINITIAL>{
    // Detectar errores lexicos
    . { addError("Caracter invalido: " + yytext()); }
}