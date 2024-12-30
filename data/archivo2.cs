
let numeros: int[] = [91, 10, 77, 24, 96, 79, 82, 38, 58, 1, 8, 65, 32, 51, 86];

RUN_MAIN main();


void vectores_2_Dim(){
    console.log("*************** Area de Vectores 2 Dim ***************");
    console.log("========= Voltear Filas =========");
    console.log("Matriz Original");
    ImprimirMensaje();
    voltearFilas();
    console.log("Matriz Volteada");
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
