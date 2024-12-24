RUN_MAIN main();

void main(){


    // recursividad
    recursividadBasica();

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

