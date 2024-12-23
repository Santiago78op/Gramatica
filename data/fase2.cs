
void mF( a:int = 10, b:int = 20){
    console.log(var1);
    console.log(a + b);
    miLista.append(a);
    miLista.append(b);
    miLista.append('a');
    const valor:int = miLista.get(0);
    const valor2:int = miLista.get(1);
    console.log("Soy el valor 1 de la lista: " + valor);
    console.log("Soy el valor 2 de la lista: " + valor2);
    miLista.set(0,10);
    const valor4:int = miLista.get(0);
    console.log("Soy el valor modificado de la lista: " + valor4);
    let miVar:int = miLista.remove(0);
    console.log("Soy el valor removido de la lista: " + valor4);
    let miVar2:int = miLista.pop();
    console.log("Soy el valor ultimo de la lista: " + miVar2);
    miLista.append(1);
    miLista.append(0);
    const v1:int = miLista.get(0);
    const v2:int = miLista.get(1);
    const v3:int = miLista.get(2);
    console.log("Soy el valor 1 de la lista: " + v1);
    console.log("Soy el valor 2 de la lista: " + v2);
    console.log("Soy el valor 3 de la lista: " + v3);
    miLista.reverse();
    const v4:int = miLista.get(0);
    const v5:int = miLista.get(1);
    const v6:int = miLista.get(2);
    console.log("Soy el valor 1 de la lista: " + v4);
    console.log("Soy el valor 2 de la lista: " + v5);
    console.log("Soy el valor 3 de la lista: " + v6);
}

let var2:int = 100;
let var1:int = var2;
let miLista : List<int>;

run_main mF(a:int = 1, b:int = 2);
