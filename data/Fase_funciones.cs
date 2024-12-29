
void main(){
    console.log("----------------------------------\n");
    let var1: int = 20;
    let var2: int = 30;

    //var1 deberia ser 20, sino es porque se tomo el entorno global sobre el local
    if (var1 != 20) {
        console.log("Manejo de ambitos erroneo :(");
    } else {
        console.log("Manejo de ambitos correcto :D");
        nota = nota + 0.5;
    }

    //declaracion de variables
    declaracion();
    // Saludar
    saludar();
    
    let ResultadoConversion :double = conversion (size = var1, tipo= "metro");
}

void declaracion(){
    console.log("========= Metodo Declaracion =========");
    let num1: int;
    const num2: int;
    let num3: int;

    let cadena1: string = "Si sale compi en vacas";
    const cadena2: string = "No sale compi2 en vacas";
    let cadena3: string = "No sale compi2 en vacas";
    const cadena4: string = "Si sale compi1 en vacas";

    if (cadena1 == cadena4 && cadena2 != cadena4) {
        console.log("Declaracion Correcta");
        nota = nota + 1;
    } else {
        console.log("Declaracion de variables erronea :(");
    }
}

void saludar(){
    console.log("========= Saludando =========");
    let valor3 : string = vector1[0];
    let valor4 : string = vector1[1];
    console.log(valor3 + " " + valor4);
}

double conversion (size:int, tipo:string="metro"){
    if(tipo=="metro"){
       return size/3*3.281;
    } else{
       return -1; 
    }
}

const var1: int = 1;
let nota: double = 0.0;
let vector1 : string [] = ["Hola", "Mundo"];

run_main main();
