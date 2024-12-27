
void mF( a:int = 10, b:int = 20){
    let valor3 : string = vector1[0];
    const valor4:int = vector2[0][0];
    console.log(valor3);
    console.log(valor4);
    vector1[0] = "OLC1";
    vector2[0][2] = 52;
    console.log(vector1[0]);
    console.log(vector2[0][2]);

}

let vector1 : string [] = ["Hola", "Mundo"];
let vector2 : int [][] = [ [1, 2, 3], [4, 5, 6] ];

run_main mF(a = 1, b = 2);
