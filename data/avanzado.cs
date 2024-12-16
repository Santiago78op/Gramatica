/*
Archivo de calificación 3
Escuela de vacaciones Diciembre 2024
Organización de Lenguajes y Compiladores 1
Ing. Mario Bautista
Aux. Fabian Reyna
*/

console.log("***\t\tCalificacion Fase 1\t\t***");
console.log("\n");


const var1: int = 49;
let punteo: int = 0;
let mensaje: string = "No voy a ganar compiladores 1";

let opcion: int = 1;
do {
    let mensaje: string = "Voy a ganar compiladores 1";
    match(opcion) {
        1 => {
            console.log("----------- Opcion 1 -----------");
            // validamos entornos
            if (mensaje != "Voy a ganar compiladores 1") {
                console.log("Entornos erroneos");
                let punteo: int = 99;
            } else {
                if (punteo == 99) {
                    console.log("Entornos erroneos");
                } else if (punteo != 0) {
                    console.log("Entornos erroneos");
                } else {
                    console.log("Entornos correctos");
                }
            }
        }
        2 => {
            console.log("----------- Opcion 2 -----------");
            // par e impar
            let numero: int = var1;
            do {
                numero--;
                numero--;
                if (numero == 0) {
                    console.log("El numero " + var1 + " es par");
                    break;
                } else if (numero == 1 || numero < 0) {
                    console.log("El numero " + var1 + " es impar");
                    break;
                }
            } while (true);
        }
        3 => {
            console.log("----------- Opcion 3 -----------");
            // corazon con bucle for
            let corazon: string = "";
            let i: int = 0;
            const n: int = 10;
            for (i = CAST(-3 * n / 2 as int); i <= n; i++) {
                corazon = "";
                let j: int;
                for (j = CAST(-3 * n / 2 as int); j <= 3 * n / 2; j++) {
                    let absolutoi: int = i;
                    let absolutoj: int = j;
                    if (i < 0) {
                        absolutoi = -i;
                    }
                    if (j < 0) {
                        absolutoj = -j;
                    }
                    if ((absolutoi + absolutoj < n)
                        || ((-n / 2 - i) * (-n / 2 - i) + (n / 2 - j) * (n / 2 - j) <= n * n / 2)
                        || ((-n / 2 - i) * (-n / 2 - i) + (-n / 2 - j) * (-n / 2 - j) <= n * n / 2)) {
                        corazon = corazon + "* ";
                    } else {
                        corazon = corazon + ". ";
                    }
                }
                console.log(corazon);
            }
        }
        4 => {
            console.log("----------- Opcion 4 -----------");
            // reloj de arena
            const n: int = 8;
            let reloj: string = "";
            let i: int;
            for (i = n; i >= 1; i--) {
                let linea: string = "";
                let j: int = n;
                for (j = n; j > i; j--) {
                    linea = linea + " ";
                }
                let k: int = 1;
                for (k = 1; k <= (2 * i - 1); k++) {
                    linea = linea + "*";
                }
                reloj = reloj + linea + "\n";
            }

            for (i = 2; i <= n; i++) {
                let linea: string = "";
                let j: int = n;
                for (j = n; j > i; j--) {
                    linea = linea + " ";
                }
                let k: int = 1;
                for (k = 1; k <= (2 * i - 1); k++) {
                    linea = linea + "*";
                }
                reloj = reloj + linea + "\n";
            }
            console.log(reloj);
        }
        5 => {
            console.log("----------- Opcion 5 -----------");
            // arbol de navidad
            let altura: int = 10;
            let ancho: int = 1;
            let cadenaFigura: string = "";
            let c: string = "* ";
            let b: string = "  ";
            let i: int;
            for (i = 0; i < altura / 4; i++) {
                let k: int;
                for (k = 0; k < altura - i; k++) {
                    cadenaFigura = cadenaFigura + b;
                }
                let j: int;
                for (j = 0; j < i * 2 + ancho; j++) {
                    cadenaFigura = cadenaFigura + c;
                }
                console.log(cadenaFigura);
                cadenaFigura = "";
            }
            cadenaFigura = "";

            for (i = 0; i < altura / 4; i++) {
                let k: int;
                for (k = 0; k < (altura - i) - 2; k++) {
                    cadenaFigura = cadenaFigura + b;
                }
                let j: int;
                for (j = 0; j < i * 2 + 5; j++) {
                    cadenaFigura = cadenaFigura + c;
                }
                console.log(cadenaFigura);
                cadenaFigura = "";
            }
            cadenaFigura = "";
            for (i = 0; i < altura / 4; i++) {
                let k: int;
                for (k = 0; k < (altura - i) - 4; k++) {
                    cadenaFigura = cadenaFigura + b;
                }
                let j: int;
                for (j = 0; j < i * 2 + 9; j++) {
                    cadenaFigura = cadenaFigura + c;
                }
                console.log(cadenaFigura);
                cadenaFigura = "";
            }
            cadenaFigura = "";
            for (i = 0; i < altura / 4; i++) {
                let k: int;
                for (k = 0; k < (altura - i) - 6; k++) {
                    cadenaFigura = cadenaFigura + b;
                }
                let j: int;
                for (j = 0; j < i * 2 + 13; j++) {
                    cadenaFigura = cadenaFigura + c;
                }
                console.log(cadenaFigura);
                cadenaFigura = "";
            }
            cadenaFigura = "";
            for (i = 0; i < altura / 4; i++) {
                let k: int;
                for (k = 0; k < (altura - 2); k++) {
                    cadenaFigura = cadenaFigura + b;
                }
                let j: int;
                for (j = 0; j < 5; j++) {
                    cadenaFigura = cadenaFigura + c;
                }
                console.log(cadenaFigura);
                cadenaFigura = "";
            }
        }

        default => {
            console.log("----------- Opcion Default -----------");
            //piramide
            const n: int = 10;
            let piramide: string = "";
            let i: int = 1;
            do {
                let linea: string = "";
                let j: int = 1;
                do {
                    linea = linea + " ";
                    j++;
                } while (j <= n - i);
                let k: int = 1;
                do {
                    if (k <= i) {
                        linea = linea + k;
                    } else if (k > i && k < 2 * i - 1) {
                        linea = linea + (2 * i - k);
                    } else if (k == 2 * i - 1) {
                        linea = linea + 1;
                    } else {
                        break;
                    }
                    if (k < 2 * i - 1) {
                        linea = linea + " ";
                    }
                    k++;
                } while (k <= 2 * i - 1);
                piramide = piramide + linea + "\n";
                i++;
            } while (i <= n);
            console.log(piramide);
        }
    }
    opcion++;
    console.log("\n");
} while (opcion <= 6);

console.log("Esta vez si sale compi1 :D");
console.log("Ustedes pueden :3");