
let i:int = 6;
const tabla: int = 7;

match i {
        1 => {
            console.log("" + tabla + " x " + i + " = " + (tabla * i));
        }
        2 => {
            console.log("" + tabla + " x " + i + " = " + (tabla * i));
        }
        3 => {
            console.log("" + tabla + " x " + i + " = " + (tabla * i));
        }
        4 => {
            console.log("" + tabla + " x " + i + " = " + (tabla * i));
        }
        5 => {
            console.log("" + tabla + " x " + i + " = " + (tabla * i));
        }
        default => {
	    continue;
            console.log("Esto no se va a imprimir");
        }
    }