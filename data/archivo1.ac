const nota: int = 100;
console.log("Operaciones aritméticas");
let cadena: string = "Voy a sacar " + nota + " en el lab de compi1";
console.log(cadena);

let operacion1: double = 3 + 7 - 8 * (1 + 1) / (8 / 2) % 2 + (5 ^ 2);
console.log("El resultado de la operación 1 es: " + operacion1);

console.log("");

console.log("Operaciones relacionales");
let operacion2: bool = 10 - 9 < 8 / 4;
console.log("El resultado de la operación 2 es: " + operacion2);

let operacion3: bool = 30 * 10 / 2 == 150;
console.log("El resultado de la operación 3 es: " + operacion3);

console.log("");

console.log("Operaciones lógicas");

let operacion4: bool = !!!false;
console.log("El resultado de la operación 4 es: " + operacion4);

let operacion5: bool = (true && false) || (false || false);
console.log("El resultado de la operación 5 es: " + operacion5);

console.log("");

console.log("Ciclos, condicionales y saltos de control");
let i: int = 0;
const tabla: int = 7;
let contador: int = 0;
while (i < 16) {
    if (i == 14) {
        console.log("Terminamos el ciclo while");
        break;
        console.log("Esto no se va a imprimir");
    }
    if (i == 15) {
        console.log("Esto no debio de impimirse");
        console.log("Fallo en el break");
    }

    if (i > 10) {
        console.log("Ahora vamos con un continue");
        i++;
        continue;
        console.log("Esto no se va a imprimir");
    }
    
    match i {
        1 => {
            console.log("" + tabla + " x " + i + " = " + (tabla * i));
        }
        2 => {
            console.log("" + tabla + " x " + i + " = " + (tabla * i));
        }
        3 => {
            console.log("" + tabla + " x " + i + " = " + (tabla * i));
        }
        4 => {
            console.log("" + tabla + " x " + i + " = " + (tabla * i));
        }
        5 => {
            console.log("" + tabla + " x " + i + " = " + (tabla * i));
        }
        default => {
            contador++;
            i++;
            continue;
            console.log("Esto no se va a imprimir");
        }
    }
    i++;

}

console.log("Se hicieron " + contador + " iteraciones de la tabla del " + tabla + " que no se imprimieron");

// SALIDA ESPERADA
/*
Operaciones aritméticas
Voy a sacar 100 en el lab de compi1
El resultado de la operación 1 es: 35

Operaciones relacionales
El resultado de la operación 2 es: true
El resultado de la operación 3 es: true

Operaciones lógicas
El resultado de la operación 4 es: true
El resultado de la operación 5 es: false

Ciclos, condicionales y saltos de control
7 x 1 = 7
7 x 2 = 14
7 x 3 = 21
7 x 4 = 28
7 x 5 = 35
Ahora vamos con un continue
Ahora vamos con un continue
Ahora vamos con un continue
Terminal el ciclo while
Se hicieron 5 iteraciones de la tabla del 7 que no se imprimieron
*/