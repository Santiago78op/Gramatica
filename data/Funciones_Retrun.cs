let global : int = 10;

RUN_MAIN miFuncion(za=global, ma=20 );

void miFuncion(za:int , ma:int){
	
	let p:int  = 20;
	let a:int = miFuncion2(p=10, global=20);
	console.log(a);
}

int miFuncion2(p:int, global:int){
	if(p >= 10){
		return p + global; 
	} else {
		return global - p;
	}
}
