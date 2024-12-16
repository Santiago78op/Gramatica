/*
Archivo de calificación 2
Escuela de vacaciones Diciembre 2024
Organización de Lenguajes y Compiladores 1
Ing. Mario Bautista
Aux. Fabian Reyna
*/

console.log("***\t\tCalificacion Fase 1\t\t***");
console.log("\n");

// *************** Manejo de entornos ***************
console.log("\"Manejo de Entornos\"");
let entornos: int = 10;
let entornos2: int = 20;

if (entornos == 10) {
    let entornos: char = 'a';
    if (entornos == 10) {
        let entornos2: char = 'F';
        console.log("Entornos erroneos");
    } else {
        let entornos: bool = true;
        if (entornos2 == 'F') {
            console.log("Entornos erroneos");
        } else {
            console.log("Entornos correctos");
        }
    }
}
console.log("\n");


// *************** While ***************
console.log("\"While\"");
let factorial: int = 1;
while (factorial <= 7) {
    let i: int = factorial;
    let fact: int = 1;
    while (i > 0) {
        fact = fact * i;
        i--;
    }
    console.log("El factorial de " + factorial + " es: " + fact);
    factorial++;
}

console.log("\n");


// *************** While con If ***************
console.log("\"While con If\"");
console.log("\n----------- While con if -----------");
const n: int = 7;
let i: double = -3 * n / 2;
while (i <= n) {
    let cadenaFigura: string = "";
    let j: double = -3 * n / 2;

    while (j <= 3 * n / 2) {
        let absolutoi: double = i;
        let absolutoj: double = j;

        if (i < 0) {
            absolutoi = -i;
        }
        if (j < 0) {
            absolutoj = -j;
        }

        if ((absolutoi + absolutoj) <= n) {
            cadenaFigura = cadenaFigura + "* ";
        }
        if ((absolutoi + absolutoj) > n) {
            cadenaFigura = cadenaFigura + ". ";
        }
        j++;
    }
    console.log(cadenaFigura);
    i++;
}
console.log("\n");

// *************** Sentencias de transferencia ***************
console.log("\"Sentencias de Transferencia\"");
let x: int = 0; //nivel 1
while (x < 3) {
    console.log("Nivel 1 (x = " + x + ")");
    let y: int = 0; //nivel 2
    while (y < 3) {
        let z: int = 0; //nivel 3
        while (z < 3) {
            // condicion para romper el ciclo de nivel 3
            if (x == 1 && y == 1 && z == 1) {
                console.log("    Break: Salimos del Nivel 3 en x = 1, y = 1, z = 1");
                break;
                console.log("\tSi esto se imprime es un error del break");
            }
            // condicion para continuar en el nivel 3
            if ((x == 1 && z == 2) || (y == 2 && z == 0)) {
                console.log("\tContinue: Saltamos lógica en Nivel 3 para x = " + x + ", y = " + y + ", z = " + z + "");
                z++;
                continue;
                console.log("\tSi esto se imprime es un error del continue");
            }
            console.log("\tProcesando combinacion (x = " + x + ", y = " + y + ", z = " + z + ")");
            z++;
        }
        // condicion para continuar en el nivel 2
        if (x == 2 && y == 0) {
            console.log("  Continue: Saltamos lógica en Nivel 2 para x = " + x + ", y = " + y + ")");
            y++;
            continue;
            console.log("\tSi esto se imprime es un error del continue");
        }
        // condicion para romper el ciclo de nivel 2 desde nivel 1
        if (x == 2 && y == 1) {
            console.log("  Break: Salimos del Nivel 2 en x = 2, y = 1");
            break;
            console.log("\tSi esto se imprime es un error del break");
        }
        y++;
    }
    if (x == 2) {
        console.log("    Break: Salimos Completamente del Nivel 1 en x = 2");
        break;
        console.log("\tSi esto se imprime es un error del break");
    }
    x++;
}
console.log("Salimos de todos los niveles");
console.log("\n");

// casteos
console.log("\"Casteos\"");
let c1: int = 75;
let c2: double = 10.5;
let c3: char = 'A';
console.log(CAST(c1 as double)); //int a double
console.log(CAST(c2 as int)); // double a int
console.log(CAST(c1 as char)); // int a char 
console.log(CAST(c3 as int)); // char a int
console.log(CAST(c3 as double)); // char a double