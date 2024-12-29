
Struct curso {
    codigo: string;
    nombre: string;
    estudiante: estudiante;
};

List<curso> baseDeDatos( l: List<curso>) {

    l.append({
        codigo: "888",
        nombre: "Compiladores 2",
        estudiante: { nombre: "Pedro Ramirez", carnet: "201700789", promedio: 88.2 }
    });

    l.append({
        codigo: "999",
        nombre: "Compiladores 3",
        estudiante: { nombre: "Ana Garcia", carnet: "201600234", promedio: 92.1 }
    });

    l.append({
        codigo: "1010",
        nombre: "Compiladores 4",
        estudiante: { nombre: "Carlos Soto", carnet: "201500567", promedio: 85.0 }
    });


    l.reverse();

    return l;
}