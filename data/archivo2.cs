
let numeros: int[] = [91, 10, 77, 24, 96, 79, 82, 38, 58, 1, 8, 65, 32, 51, 86];

RUN_MAIN main();


void vectores_2_Dim(){
    console.log("*************** Area de Vectores 2 Dim ***************");
    console.log("========= Voltear Filas =========");
    console.log("Matriz Original");
    ImprimirMensaje();
    voltearFilas();
    console.log("Matriz Volteada");
    ImprimirMensaje();
    console.log("========= Multiplicacion de Matrices =========");
    multiplicacionMatrices();
}


void main(){
    console.log("-----------------CALIFICACION ARCHIVO 2-----------------\n");
    vectores_2_Dim();
}

void voltearFilas(){
    //logitud de la matriz
    const longitud: int = length(matrizMensaje);

    let x: int;
    //tomamos hasta la mitad del arreglo
    for (x = 0; x < CAST(longitud / 2 as int); x++) {
        //almacenamos temporal
        let temporal: char[] = matrizMensaje[x];
        // calcular el indice contrario
        let indiceContrario: int = longitud - x - 1;
        // en el actual ahora esta el del otro lado
        matrizMensaje[x] = matrizMensaje[indiceContrario];
        // y en el otro lado, el que estaba originalmente en actual
        matrizMensaje[indiceContrario] = temporal;
    }
}

let matrizMensaje: char[][] = [
    ['.', '.', '.', '.', '.', '.', '4', '.', '.', '1'],
    ['.', '.', '.', '.', '.', '2', '.', '.', 'I', '.'],
    ['.', '.', '.', '.', '.', '.', '.', 'P', '.', '.'],
    ['.', '.', '.', '.', '.', '.', 'M', '.', '.', '.'],
    ['.', '.', 'C', '.', '.', 'O', '.', '.', 'E', '.'],
    ['.', 'I', '.', '.', 'C', '.', '.', 'L', '.', '.'],
    ['D', '.', '.', '.', '.', '.', 'A', '.', '.', '.'],
    ['.', '.', 'B', '.', '.', 'S', '.', '.', 'I', '.'],
    ['.', 'A', '.', '.', '.', '.', '.', 'S', '.', '.'],
    ['L', '.', '.', '.', '.', '.', '.', '.', '.', '.']
];

void ImprimirMensaje(){
    let i: int;
    let j: int;
    for (i = 0; i < length(matrizMensaje); i++) {
        let linea: string = "";
        for (j = 0; j < length(matrizMensaje[i]); j++) {
            linea = linea + " " + matrizMensaje[i][j] + " ";
        }
        console.log(linea);
    }
}

void multiplicacionMatrices(){
    let matrizA: int[][] = [
        [5, 3, -4, -2],
        [8, -1, 0, -3]
    ];

    let matrizB: int[][] = [
        [1, 4, 0],
        [-5, 3, 7],
        [0, -9, 5],
        [5, 1, 4]
    ];

    let matrizResultado: int[][] = [
        [0, 0, 0],
        [0, 0, 0]
    ];

    let i: int;
    let j: int;
    let k: int;
    
    // multiplicar las matrices
    for (i = 0; i < length(matrizA); i++) {
        for (j = 0; j < length(matrizB[0]); j++) {
            for (k = 0; k < length(matrizA[0]); k++) {
                matrizResultado[i][j] = matrizResultado[i][j] + matrizA[i][k] * matrizB[k][j];
            }
        }
    }

}
