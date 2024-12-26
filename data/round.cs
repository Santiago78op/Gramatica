
void mF( a:int = 10, b:int = 20){
    let var1:double = 2.3;
    let var2:double = 2.5;
    let var3:double = 2.7;
    
    round(var1);

    console.log("Redondeo de " + var1 + ": ");
}

let var2:int = 100;
let var1:int = var2;
let miLista : List<int>;

run_main mF(a = 1, b = 2);
