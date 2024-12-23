
void main(){
    console.log("----------------------------------\n");
    let var1: int = 20;
    let var2: int = 30;
    
    let ResultadoConversion :double = conversion (a:int = var1, tipo:string = "metro");
}


double conversion (size:int, tipo:string="metro"){
    if(tipo=="metro"){
       return size/3*3.281;
    } else{
       return -1; 
    }
}


run_main main();
