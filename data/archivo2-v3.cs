console.log("\n----------- Rombo -----------");
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

console.log("----------- Piramide -----------");
//piramide
const z: int = 10;
let piramide: string = "";
i = 1;
do {
    let linea: string = "";
    let j: int = 1;
    do {
        linea = linea + " ";
        j++;
    } while (j <= z - i);
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
    } while (k <= 2 * i - 1);
    piramide = piramide + linea + "\n";
    i++;
} while (i <= z);
console.log(piramide);