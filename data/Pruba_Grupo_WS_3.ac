let i: int = 0;

while (i < 5) {
    console.log("Iteración no." + i);

    match i {
        1 => {
            console.log("Figura: Cuadrado Hueco");
            let j: int = 0;
            while (j < 5) {
                let linea: String = "";
                let k: int = 0;
                while (k < 5) {
                    if (j == 0 || j == 4 || k == 0 || k == 4) {
                        linea = linea + "* ";
                    } else {
                        linea = linea + "  ";
                    }
                    k++;
                }
                console.log(linea);
                j++;
            }
        }
        2 => {
            console.log("Figura: Triángulo Invertido");
            let j: int = 5;
            while (j > 0) {
                let linea: String = "";
                let k: int = 0;
                while (k < j) {
                    linea = linea + "* ";
                    k++;
                }
                console.log(linea);
                j--;
            }
        }
        3 => {
            console.log("Figura: Rombo");
            let n: int = 5;
            let j: int = 0;
            while (j < n) {
                let linea: String = "";
                let k: int = 0;
                while (k < n - j - 1) {
                    linea = linea + " ";
                    k++;
                }
                k = 0;
                while (k < 2 * j + 1) {
                    linea = linea + "*";
                    k++;
                }
                console.log(linea);
                j++;
            }
            j = n - 2;
            while (j >= 0) {
                let linea: String = "";
                let k: int = 0;
                while (k < n - j - 1) {
                    linea = linea + " ";
                    k++;
                }
                k = 0;
                while (k < 2 * j + 1) {
                    linea = linea + "*";
                    k++;
                }
                console.log(linea);
                j--;
            }
        }
        4 => {
            console.log("Figura: Pirámide");
            let n: int = 5;
            let j: int = 0;
            while (j < n) {
                let linea: String = "";
                let k: int = 0;
                while (k < n - j - 1) {
                    linea = linea + " ";
                    k++;
                }
                k = 0;
                while (k < 2 * j + 1) {
                    linea = linea + "*";
                    k++;
                }
                console.log(linea);
                j++;
            }
        }
        default => {
            console.log("Valor por defecto");
        }
    }
    i++;
}


/*
iteración no.0
valor por defecto
iteración no.1
figura: cuadrado hueco
* * * * * 
*       * 
*       * 
*       * 
* * * * * 
iteración no.2
figura: triángulo invertido
* * * * * 
* * * * 
* * * 
* * 
* 
iteración no.3
figura: rombo
    *
   ***
  *****
 *******
*********
 *******
  *****
   ***
    *
iteración no.4
figura: pirámide
    *
   ***
  *****
 *******
*********

*/