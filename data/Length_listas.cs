
void mF( a:int = 10, b:int = 20){
    console.log(var1);
    console.log(a + b);
    miLista.append(a);
    miLista.append(b);
    miLista.append('a');
  
    let valor:int = length(miLista);
    let valor1:int = length(vector);
    console.log("Soy el numero de elementos en la Lista: " + valor);
    console.log("Soy el numero de elementos en el Vector: " + valor1);

}

let var2:int = 100;
let var1:int = var2;
let miLista : List<int>;
let vector:string[] = ["Hola", "Mundo"];

run_main mF(a = 1, b = 2);
