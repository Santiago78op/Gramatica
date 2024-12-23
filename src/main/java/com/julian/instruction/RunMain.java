package com.julian.instruction;

import com.julian.LinkedList.semanticErrorManager;
import com.julian.abstracto.Instruccion;
import com.julian.exception.Errores;
import com.julian.symbol.*;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * RunMain -> Clase que representa la instrucción de ejecutar el método principal.
 * Esta clase esta encargada de ejecutar el codigo generado dentro del lenguaje
 * de programación.
 *
 * Indica que metodo o funcion es la que incia la logica del programa.
 *
 * Ejemplo:
 *  RUN_MAIN <ID> ( ) ;
 *  RUN_MAIN <ID> ( <PARAMETROS> ) ;
 */
public class RunMain extends Instruccion {

    private String id;
    private LinkedList<HashMap> parametros;

    /**
     * RunMain -> Constructor de la clase.
     * @param id Identificador del método.
     * @param parametros Lista de parámetros del método.
     * @param linea Linea del método.
     * @param columna Columna del método.
     */
    public RunMain(String id, LinkedList<HashMap> parametros, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolo tablaDeSimbolos) {
        // Validamos la existencia de la funcion en el arbol
        var busquedaFuncion = arbol.getFuncion(this.id);
        // Si la funcion no existe
        if (busquedaFuncion == null){
            semanticErrorManager.addError(new Errores("Semantico", "La Funcion " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna));
            return new Errores("Semantico", "La Funcion " + this.id + " ya existe en la tabla de simbolos", this.linea, this.columna);
        }
        // Si la funcion existe
        // Validamos que venga Metodo
        if (busquedaFuncion instanceof Metodo metodo){
            // Ejecutamos el metodo
            var newTabla = new tablaSimbolo(arbol.getTablaSimbolosGlobal());
            newTabla.setNombre(this.id);

             /*En un lenguaje convencional aqui se deberia validar que la cantidad
        de parametros coincida, pero en el proyecto los parametros pueden tener
        un valor por defecto, por lo tanto no es 100 obligatorio que la cantidad
        de parametros de la funcion y de su llamada coincidan*/

            // for para recorrer los parametros
            for (int i = 0; i < metodo.getParametros().size(); i++) {
                // Obtenemos el identificador
                var identificador = metodo.getParametros().get(i).get("id").toString();
                // Obtenemos el tipo
                var tipo = (Tipo) metodo.getParametros().get(i).get("tipo");
                // Obtenemos el valor
                var valor = (Instruccion) this.parametros.get(i).get("valor");
                // Creamos el simbolo
                var simbolo = new Simbolo(tipo, identificador, valor, false, "Externo", "", this.linea, this.columna);
                // Agregamos el simbolo a la tabla de simbolos
                if (!newTabla.setVariable(simbolo)){
                    semanticErrorManager.addError(new Errores("Semantico", "La variable " + identificador + " ya existe en la tabla de simbolos", this.linea, this.columna));
                    return new Errores("Semantico", "La variable " + identificador + " ya existe en la tabla de simbolos", this.linea, this.columna);
                }
            }

            // Registramos el valor
            for (int i = 0; i < this.parametros.size(); i++){
                // Obtenemos el identificador
                var identificador = newTabla.getVariable(this.parametros.get(i).get("id").toString());
                if (identificador == null){
                    semanticErrorManager.addError(new Errores("Semantico", "El identificador de la variable " + identificador + " no puede ser nulo", this.linea, this.columna));
                    return new Errores("Semantico", "El identificador de la variable " + identificador + " no puede ser nulo", this.linea, this.columna);
                }
                // Obtenemos el valor
                var valor = (Instruccion) this.parametros.get(i).get("valor");
                if ( valor == null){
                    semanticErrorManager.addError(new Errores("Semantico", "El valor de la variable " + identificador + " no puede ser nulo", this.linea, this.columna));
                    return new Errores("Semantico", "El valor de la variable " + identificador + " no puede ser nulo", this.linea, this.columna);
                }

                var resValor = valor.interpretar(arbol, newTabla);
                if (resValor instanceof Errores) return resValor;

                // Validamos tipos
                if (valor.tipo.getTipo() != identificador.getTipo().getTipo()){
                    semanticErrorManager.addError(new Errores("Semantico", "Error de tipos en la declaración de la variable " + identificador.getId() + ".\n" +
                            "El tipo de la variable no coincide con el tipo de la expresión.", this.linea, this.columna));
                    return new Errores("Semantico", "Error de tipos en la declaración de la variable " + identificador.getId() + ".\n" +
                            "El tipo de la variable no coincide con el tipo de la expresión.", this.linea, this.columna);
                }

                identificador.setValor(resValor);
            }

            // Validar parametros vacios o null
            // recorremos los parametros del metodo
            for (int i = 0; i < metodo.getParametros().size(); i++) {
                var identificar = metodo.getParametros().get(i).get("id").toString();
                var resultado = newTabla.getVariable(identificar);
                if (resultado == null) {
                    semanticErrorManager.addError(new Errores("Semantico", "El parametro " + identificar + " no puede ser nulo", this.linea, this.columna));
                    return new Errores("Semantico", "El parametro " + identificar + " no puede ser nulo", this.linea, this.columna);
                }

                if (resultado.getValor() == null) {
                    semanticErrorManager.addError(new Errores("Semantico", "El parametro " + identificar + " no puede ser nulo", this.linea, this.columna));
                    return new Errores("Semantico", "El parametro " + identificar + " no puede ser nulo", this.linea, this.columna);
                }
            }
            var resultadoMetodo = metodo.interpretar(arbol, newTabla);
            if (resultadoMetodo instanceof Errores) return resultadoMetodo;
        }
        return null;
    }
}


