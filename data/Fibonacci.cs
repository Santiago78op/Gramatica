RUN_MAIN run();

void run() {
    console.log("Calculando Fibonacci...");
    let resultado : int = fibonacci(n=10);
    console.log("El décimo número de Fibonacci es: " + resultado);
}

int fibonacci(n: int) {
    if (n == 0) {
        return 0;
    } else if (n == 1) {
        return 1;
    } else {
        return fibonacci(n=n - 1) + fibonacci(n=n-2);
    }
}