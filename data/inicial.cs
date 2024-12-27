RUN_MAIN main();

let var1: int = 0;


const arreglo2: int[] = [0, 0, 1, 2, 0, 0, 5, 1, 0, 0, 8, 0, 0];

void main(){
    console.log("Archivo de prueba");
    console.log("Si sale compi1");

    let var1: int = 10;
    let a: int = 10;
    let b: int = 10;

    if (var1 == 0) {
        console.log("Manejo de ambitos erroneo :'(");
    } else {
        console.log("Manejo de ambitos correcto");
    }

    // tabla de multiplicar
    tablaMultiplicar(valor=5);

    // recursividad
    recursividadBasica();

    // arreglos
    AnalizarArreglo();

    //Suma
    const resultado: int = suma(a=7, b=10);
    console.log(resultado);
    console.log("Fin de la prueba");
}


void tablaMultiplicar(valor:int = 0){
    const cadenaSalida: string = "Final de la tabla de multiplicar";
    let i: int = 0;
    for (i = 1; i <= 11; i++) {
        console.log(valor + " x " + i + " = " + valor * i);
        if (i == 10) {
            console.log(cadenaSalida);
            break;
        }
    }
}

// probando una funcion recursiva
double mcd(a:double, b:double){
    if (b == 0.0) {
        return a;
    } else {
        return mcd(a=b, b=a % b);
    }
}

void recursividadBasica(){
    const resultado: double = mcd(a=48.0, b=18.0);

    if (resultado == 6) {
        console.log("Funcion recursiva correcta");
        return;
    }
    console.log("Funcion recursiva incorrecta");
}

// viendo arreglos
void AnalizarArreglo(){
    let temporal: int = 0;
    let suma: int = 0;
    let ceros: int = 0;
    let i: int = 0;
    for (i = 0; i < length(arreglo2); i++) {
        temporal = arreglo2[i];
        if (temporal == 0) {
            ceros = ceros + 1;
            continue;
        }
        suma = suma + temporal;
    }
    console.log("La suma de los elementos del arreglo es: " + suma);
    console.log("La cantidad de ceros en el arreglo es: " + ceros);
}

int suma(a:int, b:int){
    return a + b;
}

// Salida de archivo de prueba
/*
Archivo de prueba
Si sale compi1
Manejo de ambitos correcto
5 x 1 = 5
5 x 2 = 10
5 x 3 = 15
5 x 4 = 20
5 x 5 = 25
5 x 6 = 30
5 x 7 = 35
5 x 8 = 40
5 x 9 = 45
5 x 10 = 50
Final de la tabla de multiplicar
Funcion recursiva correcta
La suma de los elementos del arreglo es: 17
La cantidad de ceros en el arreglo es: 8
Fin de la prueba
*/