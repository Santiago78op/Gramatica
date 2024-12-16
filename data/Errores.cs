/*
Errores lexicos
*/
°
let i: ?int = 10;

let nota: double = ¬ 100.0;

if (nota >= ´ 61) {
    console.log("Aprobado");
}else ¡ {
    console.log("Reprobado"); ~
}

while(i<12){
    console.log(° i);
    i = i + ? 1;
}

/*
Errores Sintacticos
*/
console.log("Errores Sintacticos");
console();
console.log("F");
if(10+10==20){
console.log("Veamos dentro de un if");
console.log("Me falta el ;")
console.log("F");
console.log("Nos recuperamos?");
}

/*
Errores Semanticos
*/
if (3 * 6 + 7) {
    console.log("No me recupere de errores semanticos :'v");
}else{
    console.log("No me recupere de errores semanticos :'v");
}

const constante: double = 61.0001;
constante++;

while(10+10){
    console.log("No me recupere de errores semanticos :'v");
}

let error2: int = "true";
const error2:string = "true";
let error3: bool = 10;
const error4: char = "Hola Mundo";

let miVariable: int = 10;
miVariable = "Hola Mundo";
miVariable = true;
miVariable = 'a';

let contador: int = 0;
while (contador < 5) {

    if (contador == 2) {
        contador = "Hola Mundo";
    } else if (contador == 3) {
        contador = true;
    } else {
        console.log("contador");
    }
    contador++;
}

