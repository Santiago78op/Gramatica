Struct persona{
    nombre: string;
    edad: int;
    estatura: double;
};



RUN_MAIN main();

let global: int = 10;

void main(){
    console.log("Archivo de prueba 2 - Fase 2");
    console.log("");
    //hacemos una suma con la funcion
    let a: int = suma(a=1, b=global); //debe almacenar 11
    console.log("El resultado de la suma es: " + a);
    console.log("");

    miMetodo();

}



int suma(a:int, b:int){
    return a + b;
}

void miMetodo(){
    //aqui vamos a tener los structs
    let p1: persona = { nombre: "Luis", edad: 20, estatura: 1.60 };


    let id1: identificacion = { cui: "3548265470101", p: p1 };

     console.log("La persona " + id1.p.nombre + " se identifica con el cui " + id1.cui);
    
}

Struct identificacion{
    cui: string;
    p: persona;
};

// Salida esperada
/*
Archivo de prueba 2 - Fase 2

El resultado de la suma es: 11

La persona Luis tiene 20 años
La persona Maria tiene 25 años
La persona Luis se identifica con el cui 3548265470101
La persona Jorge se identifica con el cui 3748614591201

*/
