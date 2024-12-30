void mF( a:int = 10, b:int = 20){
    console.log(var1);
    console.log(a + b);
    miLista.append(a);
    miLista.append(b);
    miLista.append('a');
  
    let valor:int = length(miLista);
    let valor1:int = length(vector[0]);
    let valor2:int = length(vector4);
    let valor3:int = length(vector4[0]);
    console.log("Soy el numero de elementos en la Lista: " + valor);
    console.log("Soy el numero de elementos en el Vector: " + valor1);
    console.log("Soy el numero de elementos en el Vector2: " + valor2);
    console.log("Soy el numero de elementos en el Vector3: " + valor3);

}

let var2:int = 100;
let var1:int = var2;
let miLista : List<int>;
let vector:string[] = ["Hola", "Mundo"];
const vector4:int [][] = [ [1, 2], [3, 4] ];

run_main mF(a=1,b=2);