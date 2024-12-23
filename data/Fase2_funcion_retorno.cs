
void main(){
    console.log("----------------------------------\n");
    let var1: int = 20;
    let var2: int = 30;
    
    let ResultadoConversion :double = conversion (size:int = 10, tipo:string = "metro");
    
}


double conversion (size:int, tipo:string="metro"){
    if(tipo=="metro"){
       return size/3*3.281;
    } else{
       return -1.0; 
    }
}


run_main main();
