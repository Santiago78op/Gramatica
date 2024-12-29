// Definición de estructuras
Struct estudiante {
    nombre: string;
    carnet: string;
    promedio: double;
};

Struct curso {
    codigo: string;
    nombre: string;
    estudiante: estudiante;
};

RUN_MAIN main();

// Variables globales
let contador: int = 0;
const PI: double = 3.14159;
const listaDeCursos: List<curso>;

void main() {
    console.log("=== Inicio de Programa de Prueba ===");
    
    // Prueba de estructuras y asignaciones
    let est1: estudiante = { nombre: "Juan Perez", carnet: "201800123", promedio: 85.5 };
    const curso1: curso = { 
        codigo: "777", 
        nombre: "Compiladores 1",
        estudiante: { nombre: "Maria Lopez", carnet: "201900456", promedio: 90.7 }
    };

    // Llamada a funciones
    pruebaCalculos();
    pruebaEstructuras(est=est1);
    pruebaArreglos();
    baseDeDatos(l=listaDeCursos);
    mostrarCursos(l=listaDeCursos);

    console.log("Fibonacci de 5: " + fibonacci(n=5));
    
    // Nuevas pruebas
    pruebaWhile();
    pruebaDoWhile(); 
    pruebaFor();
    pruebaNested();
    pruebaFibonacci();

    console.log("=== Fin de Programa de Prueba ===");
}

void pruebaCalculos() {
    let resultado: double = calcularArea(radio=5.0);
    console.log("El área del círculo es: " + resultado);
    
    // Prueba de condicionales
    if (resultado > 50.0) {
        console.log("El área es mayor a 50 unidades cuadradas");
    } else {
        console.log("El área es menor o igual a 50 unidades cuadradas");
    }
}

double calcularArea(radio: double) {
    return PI * radio * radio;
}

void pruebaEstructuras(est: estudiante) {
    console.log("=== Datos del Estudiante ===");
    console.log("Nombre: " + est.nombre);
    console.log("Carnet: " + est.carnet);
    console.log("Promedio: " + est.promedio);
}

void pruebaArreglos() {
    const numeros: int[] = [10, 20, 30, 40, 50];
    let suma: int = 0;
    let i: int = 0;
    
    console.log("=== Recorrido de Arreglo ===");
    for (i = 0; i < length(numeros); i++) {
        suma = suma + numeros[i];
        console.log("Posición " + i + ": " + numeros[i]);
    }
    
    console.log("La suma total es: " + suma);
}

List<curso> baseDeDatos( l: List<curso>) {

    l.append({ 
        codigo: "888", 
        nombre: "Compiladores 2",
        estudiante: { nombre: "Pedro Ramirez", carnet: "201700789", promedio: 88.2 }
    });

    l.append({ 
        codigo: "999", 
        nombre: "Compiladores 3",
        estudiante: { nombre: "Ana Garcia", carnet: "201600234", promedio: 92.1 }
    });

    l.append({ 
        codigo: "1010", 
        nombre: "Compiladores 4",
        estudiante: { nombre: "Carlos Soto", carnet: "201500567", promedio: 85.0 }
    });


    l.reverse();

    return l;
}

void mostrarCursos(l: List<curso>) {
    console.log("=== Listado de Cursos ===");
    let i: int = 0;
    for (i = 0; i < length(l); i++) {
        console.log(l.get(i).nombre);
    }
}

int fibonacci(n: int) {
    if (n <= 1) {
        return n;
    }
    return fibonacci(n=n - 1) + fibonacci(n=n - 2);
}

void pruebaWhile() {
    console.log("=== Prueba While ===");
    let i: int = 0;
    while (i < 3) {
        console.log("Iteración while: " + i);
        i = i + 1;
    }
}

void pruebaDoWhile() {
    console.log("=== Prueba Do-While ===");
    let j: int = 0;
    do {
        console.log("Iteración do-while: " + j);
        j = j + 1;
    } while (j < 3);
}

void pruebaFor() {
    console.log("=== Prueba For con Break ===");
    let sum: int = 0;
    for (let k: int = 0; k < 5; k = k + 1) {
        if (k == 3) {
            break;
        }
        sum = sum + k;
        console.log("Suma actual: " + sum);
    }
}

void pruebaNested() {
    console.log("=== Prueba Ciclos Anidados ===");
    for (let i: int = 0; i < 2; i = i + 1) {
        for (let j: int = 0; j < 2; j = j + 1) {
            console.log("i: " + i + ", j: " + j);
        }
    }
}

void pruebaFibonacci() {
    console.log("=== Serie Fibonacci ===");
    let i: int = 0;
    for (i = 0; i < 7; i = i + 1) {
        console.log("Fibonacci de " + i + ": " + fibonacci(n=i));
    }
}

/* Salida esperada:
=== Inicio de Programa de Prueba ===
El área del círculo es: 78.53975
El área es mayor a 50 unidades cuadradas
=== Datos del Estudiante ===
Nombre: Juan Perez
Carnet: 201800123
Promedio: 85.5
=== Recorrido de Arreglo ===
Posición 0: 10
Posición 1: 20
Posición 2: 30
Posición 3: 40
Posición 4: 50
La suma total es: 150
== Listado de Cursos ===
Compiladores 4
Compiladores 3
Compiladores 2
Fibonacci de 5: 5

=== Prueba While ===
Iteración while: 0
Iteración while: 1
Iteración while: 2

=== Prueba Do-While ===
Iteración do-while: 0
Iteración do-while: 1
Iteración do-while: 2

=== Prueba For con Break ===
Suma actual: 0
Suma actual: 1
Suma actual: 3

=== Prueba Ciclos Anidados ===
i: 0, j: 0
i: 0, j: 1
i: 1, j: 0
i: 1, j: 1

=== Serie Fibonacci ===
Fibonacci de 0: 0
Fibonacci de 1: 1
Fibonacci de 2: 1
Fibonacci de 3: 2
Fibonacci de 4: 3
Fibonacci de 5: 5
Fibonacci de 6: 8

=== Fin de Programa de Prueba ===
*/