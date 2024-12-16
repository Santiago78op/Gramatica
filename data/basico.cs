/*
Archivo de calificación 1
Escuela de vacaciones Diciembre 2024
Organización de Lenguajes y Compiladores 1
Ing. Mario Bautista
Aux. Fabian Reyna
*/

console.log("***\t\tCalificacion Fase 1\t\t***");
console.log("\n");


// *************** declaracion de variables ***************
console.log("\"Declaracion de Variables\"");
let varIablE1: int;
let variable2: double;
let VaRiaBLe3: bool;
lEt vAriAblE4: char;
LeT variable5: String;
let var6: int = 100;
let var7: double = 61.000123;
let var8: bool = false;
let var9: char = 'R';
let var10: string = "Mi primer interprete con JFLEX Y CUP";

console.log("Default de los enteros: " + varIablE1);
console.log("Default de los decimales: " + variable2);
console.log("Default de los booleanos: " + varIablE3);
console.log("Default de los caracteres: " + vAriAblE4);
console.log("Default de las cadenas: " + VARIABLE5);
console.log("Variable inicializada entera: " + var6);
console.log("Variable inicializada decimal: " + var7);
console.log("Variable inicializada booleana: " + var8);
console.log("Variable inicializada caracter: " + var9);
console.log("Variable inicializada cadena: " + var10);
console.log("\n");


// *************** asignacion de variables ***************
console.log("\'Asignacion de Variables\'");
var6 = 61;
var7 = 99.912356;
var8 = true;
var9 = 'F';
var10 = "Diciembre 2024";
console.log("Asignacion entera: " + var6);
console.log("Asignacion decimal: " + var7);
console.log("Asignacion booleana: " + var8);
console.log("Asignacion caracter: " + var9);
console.log("Asignacion cadena: " + var10);
console.log("\n");


// *************** declaracion de constantes ***************
console.log("\"Declaracion de Constantes\"");
const constante1: int;
const constante2: double;
const constante3: bool;
const constante4: char;
const constante5: string;
const constante6: int = 100;
const constante7: double = 61.000123;
const constante8: bool = false;
const constante9: char = 'R';
const constante10: string = "Mi primer interprete con JFLEX Y CUP";

console.log("Default de las constantes enteras: " + constante1);
console.log("Default de las constantes decimales: " + constante2);
console.log("Default de las constantes booleanas: " + constante3);
console.log("Default de las constantes caracteres: " + constante4);
console.log("Default de las constantes cadenas: " + constante5);
console.log("Constante inicializada entera: " + constante6);
console.log("Constante inicializada decimal: " + constante7);
console.log("Constante inicializada booleana: " + constante8);
console.log("Constante inicializada caracter: " + constante9);
console.log("Constante inicializada cadena: " + constante10);
console.log("\n");


// *************** validacion de constantes ***************
console.log("\'Validacion de Constantes\'");
constante6 = 61;
constante7 = 99.912356;
constante8 = true;
constante9 = 'F';
constante10 = "Diciembre 2024";
console.log("Constante6: " + constante6);
console.log("Constante7: " + constante7);
console.log("Constante8: " + constante8);
console.log("Constante9: " + constante9);
console.log("Constante10: " + constante10);
console.log("\n");


// *************** operaciones aritmeticas ***************
console.log("\\Operaciones Aritmeticas\\");

//  cadena + (entero, decimal, caracter y cadena)
let sumaCadenas: string;
let sumas: int;
sumaCadenas = "Voy a ganar " + 'C' + "OM" + 'P' + 'I' + 1.00 + " en diciembre " + 2024 + "!!!";
console.log(sumaCadenas);

// entero + (entero, decimal, booleano y caracter)
sumas = 5 +
    CAST(10.25 + 5 AS int) + //15
    (2 + true + false + true + true) + //5
    (0 + '$'); // el ascii de $ es 36 
console.log("El primer resultado de las sumas es " + sumas);

// decimal + (entero, decimal, booleano, caracter)
let sumas2: double = 12.25 +
    (5.25 + 5) + //10.25
    (2.25 + true + false + true + false + false) + 
    (0.50 + 'A'); // el ascii de A es 65
console.log("El segundo resultado de las sumas es " + sumas2); 
console.log("\n");

let restas: double = (100 - 1 - 0 - 1 - '2') - 25.25; // el ascii de 2 es 50
console.log("El resultado de las restas es " + restas); 

let multiplicacion: double;
multiplicacion = '+' * 2 * 0.5 * 10 * 0.025; // el ascii de + es 43
console.log("El resultado de la multiplicacion es " + multiplicacion); 

let division: double;
division = (10 / 2) / (('R' / 1) / (90.2 / 2.2)); // el ascii de R es 82
console.log("El resultado de la division es " + division); 

let potencia: double = (2 ^ 3.0) ^ (2.0 ^ 1);
console.log("El resultado de la potencia es " + potencia); 

let raiz: double = (4096.0 $ 4) $ 3;
console.log("El resultado de la raiz es " + raiz); 

let modulo: double = (100 % 24) % 3.0;
console.log("El resultado del modulo es " + modulo); 

let negacionUnaria: double = - - - - - - - - (-10.0 + -20 + -30 + -40);
console.log("El resultado de la negacion unaria es " + negacionUnaria); 

// aritmeticas complejas y combinadas
let aritmeticaCompleja1: double = 20 - 10 + 8 / 2 * 3 + 10 - 10 - 10 + 50; 
let aritmeticaCompleja2: double = 100 / 20 * 9 - 78 + 6 - 7 + 8 - 7 + 7 * 1 * 2 * 3 / 3; 
let val1: double = -2.0;
let val2: double = -10;
let val3: double = val2 * val1 + 10 - 10 * 100 / 100; 
val1 = val3 / val1 + 50 ^ 2 / 50 * 2 + 100 / 100 - 0; 

console.log("El resultado de la aritmetica compleja 1 es " + aritmeticaCompleja1); 
console.log("El resultado de la aritmetica compleja 2 es " + aritmeticaCompleja2); 
console.log("El resultado de la aritmetica compleja 3 es " + val1); 
console.log("\n");


// *************** operaciones relacionales ***************
console.log("\\Operaciones Relacionales\\");
let relacional1: bool = 22 - 10 + 8 / 2 * 3 + 10 - 10 - 10 + 50 > 61; 
console.log("El resultado de > es " + relacional1); 

let relacional2: bool = 12 + 5 * 3 >= 56 * 5 / 21; 
console.log("El resultado de >= es " + relacional2); 

let relacional3: bool = 70 - 10 * 2 < 10 * 7; 
console.log("El resultado de la < es " + relacional3); 

let relacional4: bool = 55 / 5 <= 10 + 5; 
console.log("El resultado de <= es " + relacional4); 

let relacional5: bool = (17 * 86 - 25 + 22 / 11) == 20; 
console.log("El resultado de == es " + relacional5); 

let relacional6: bool = 'a' != 20 * 4; //el ascii de a es 97 y da false
console.log("El resultado de != es " + relacional6); 
console.log("\n");


// *************** operaciones logicas ***************
console.log("\\Operaciones Logicas\\");
let logica1: bool = !!!!!!!!!!!!!!!!!!!!true; 
let logica2:bool = ((true && true) || ((false && false) && (false == true))) || (!true); 
let logica3: bool = (true || false) && (true || false) || (true || false); 
//logica con aritmetica, relacionales y logicas
let logicaCompleja: bool = (10 + 5 * 3 >= 56 * 5 / 21) && (70 - 10 * 2 < 10 * 7) || (55 / 5 <= 10 + 5) && (17 * 86 - 25 + 22 / 11) == 20 || 'a' != 20 * 4; //el ascii de a es 97 

console.log("El resultado de logica 1 es " + logica1); 
console.log("El resultado de logica 2 es " + logica2); 
console.log("El resultado de logica 3 es " + logica3); 
console.log("El resultado de logica compleja es " + logicaCompleja); 
console.log("\n");
