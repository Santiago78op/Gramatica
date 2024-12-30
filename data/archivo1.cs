const var1: int = 1;
let nota: double = 0.0;

RUN_MAIN principal();

void principal(){
    // recursividad
    recursividad();

}



void recursividad(){
    console.log("========= Metodo Recursividad =========");
    let numSumDigitos: int = 123456789;
    let numFibonacci: int = 20;
    let numAckermann1: int = 3;
    let numAckermann2: int = 4;
    parImpar();
    
}

int par(nump:int){
    if(nump==0){
        return 1;
    }
    return impar(numi=nump-1);
}

int impar(numi:int){
    if(numi==0){
        return 0;
    }
    return par(nump=numi-1);
}

void parImpar(){
    let numero:int = 70;
    if(par(nump=numero)==1){
        console.log("El numero "+numero+" es par");
    }else{
        console.log("El numero "+numero+" es impar");
    }
}