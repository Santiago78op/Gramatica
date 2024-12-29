int fibonacci(n: int = 0){
    if (n > 1) {
        return fibonacci(n = n - 1) + fibonacci(n = n - 2);
    } else if (n == 1) {
        return 1;
    } else if (n == 0) {
        return 0;
    } else {
        console.log("Error en el calculo de fibonacci");
        return -1;
    }
}


void principal(){
    console.log("Fibonacci");
    let n: int = 5;
    let resultado1: int = fibonacci(n = n);
    n = 10;
    let resultado2: int = fibonacci(n = n);
    if (resultado1 == 5 && resultado2 == 55) {
        console.log("Fibonacci correcto");
    } else {
        console.log("Ya no sale compi1 :'v");
        console.log("Mejor me voy a industrial :(");
    }
}

RUN_MAIN principal();

// salida esperada
/*
fibonacci
Fibonacci correcto
*/