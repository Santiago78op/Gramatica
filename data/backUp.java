package com.julian;

import java.util.*;

public class TreeNode<T> {
    T data;
    List<TreeNode<T>> children;

    // Variable para almacenar los conjuntos definidos
    Map<String, Set<String>> conjuntosDefinidos;

    TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
        this.conjuntosDefinidos = new HashMap<>();
    }

    void addChild(TreeNode<T> child) {
        this.children.add(child);
    }

    List<TreeNode<T>> getChildren() {
        return children;
    }

    T getData() {
        return data;
    }

    public void printTree(String appender) {
        System.out.println(appender + this.getData());
        for (TreeNode<T> each : this.getChildren()) {
            each.printTree(appender + appender);
        }
    }

    // Método principal para evaluar las operaciones y las evaluaciones de conjuntos
    public void evaluateOperations() {
        // Obtener los conjuntos definidos antes de evaluar las operaciones
        this.conjuntosDefinidos = obtenerConjuntosDefinidos();

        if (data.toString().equals("BLOCK")) {
            // Recorrer los nodos hijos de BLOCK para encontrar OPER_LIST y EVAL_LIST
            for (TreeNode<T> child : children) {
                if (child.getData().toString().equals("OPER_LIST")) {
                    // Evaluar las operaciones definidas en OPER_LIST
                    for (TreeNode<T> operPartNode : child.getChildren()) {
                        if (operPartNode.getData().toString().equals("OPER_PART")) {
                            // Encontrar el nodo OPER_DEF dentro de OPER_PART
                            TreeNode<T> operDefNode = operPartNode.getChildren().get(2); // El tercer hijo es OPER_DEF
                            if (operDefNode.getData().toString().equals("OPER_DEF")) {
                                // Obtener el nombre de la operación
                                String operationName = operDefNode.getChildren().get(0).getData().toString(); // Primer hijo es ID
                                System.out.println("Evaluando operación: " + operationName);

                                // Evaluar la expresión de la operación
                                Set<String> resultadoOperacion = evaluarOperacion(operDefNode);
                                System.out.println("Resultado de la operación '" + operationName + "': " + resultadoOperacion);
                            }
                        }
                    }
                }
                if (child.getData().toString().equals("EVAL_LIST")) {
                    // Evaluar las evaluaciones de conjuntos
                    for (TreeNode<T> evalChild : child.getChildren()) {
                        Set<String> evalResult = evaluateEvaluation(evalChild);
                        System.out.println("Resultado de la evaluación '" + evalChild.getChildren().get(0).getData() + "': " + evalResult);
                    }
                }
            }
        }
    }

    // Método para evaluar una operación a partir de su nodo OPER_DEF
    private Set<String> evaluarOperacion(TreeNode<T> operDefNode) {
        TreeNode<T> conjExprNode = operDefNode.getChildren().get(2); // El tercer hijo es CONJ_EXPR
        return evaluarConjExpr(conjExprNode);
    }

    // Método para evaluar un nodo CONJ_EXPR de manera recursiva
    private Set<String> evaluarConjExpr(TreeNode<T> conjExprNode) {
        // Verifica si el nodo actual es de tipo "CONJ_EXPR"
        if (conjExprNode.getData().equals("CONJ_EXPR")) {
            Set<String> result = new HashSet<>();
            String operator = "";  // Inicializamos el operador como vacío
            // Iteramos sobre los hijos del nodo "CONJ_EXPR"
            for (TreeNode<T> child : conjExprNode.getChildren()) {
                // Verifica si el hijo es un nodo de operador (OPER_EXPR)
                if (child.getData().equals("OPER_EXPR")) {
                    operator = child.getChildren().get(0).getData().toString();  // Obtiene el operador
                }
                // Verifica si el hijo es un nodo de conjuntos (EXPRE_CONJ)
                else if (child.getData().equals("EXPRE_CONJ")) {
                    Set<String> currentSet = obtenerConjuntoDeExprConj(child);  // Obtiene el conjunto
                    // Si ya tenemos un operador, aplicamos la operación
                    if (!operator.isEmpty()) {
                        result = aplicarOperacion(operator, result, currentSet);
                    } else {
                        // Si es el primer conjunto, lo asignamos al resultado inicial
                        result = currentSet;
                    }
                }
            }
            return result;
        }
        // Si no es un nodo "CONJ_EXPR", verificar si es "EXPRE_CONJ" para procesarlo
        else if (conjExprNode.getData().equals("EXPRE_CONJ")) {
            return obtenerConjuntoDeExprConj(conjExprNode);
        }
        return new HashSet<>();
    }
    // Método para aplicar la operación al conjunto
    private Set<String> aplicarOperacion(String operator, Set<String> set1, Set<String> set2) {
        switch (operator) {
            case "U":
                return union(set1, set2);
            case "&":
                return intersection(set1, set2);
            case "^":
                return complement(set1);
            case "-":
                return difference(set1, set2);
            default:
                return new HashSet<>();
        }
    }

    // Método para obtener el conjunto de un nodo EXPR_CONJ
    private Set<String> obtenerConjuntoDeExprConj(TreeNode<T> exprConjNode) {
        if (exprConjNode.getData().equals("EXPRE_CONJ")) {
            // Verifica si el nodo tiene hijos y si son otros conjuntos o nombres de conjuntos
            Set<String> conjuntoElementos = new HashSet<>();
            for (TreeNode<T> child : exprConjNode.getChildren()) {
                if (child.getData().equals("EXPRE_CONJ")) {
                    conjuntoElementos.addAll(obtenerConjuntoDeExprConj(child));
                } else {
                    conjuntoElementos.add(child.getData().toString());
                }
            }
            return conjuntoElementos;
        }
        return new HashSet<>();
    }

    // Método para evaluar una evaluación de conjuntos
    private Set<String> evaluateEvaluation(TreeNode<T> evalNode) {
        String evalType = evalNode.getData().toString();
        if (evalType.equals("EVAL_PART")) {
            String operationName = evalNode.getChildren().get(6).getData().toString(); // Nombre de la operación
            Set<String> conjunto = parseSet(evalNode.getChildren().get(3).getData().toString()); // El conjunto que se evalúa
            System.out.println("Aplicando operación '" + operationName + "' al conjunto: " + conjunto);
            return conjunto; // Esto es solo un ejemplo, ajusta según tu lógica
        }
        return new HashSet<>();
    }

    // Métodos para las operaciones de conjuntos (intersección, unión, complemento, diferencia)
    private Set<String> intersection(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }

    private Set<String> union(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }

    private Set<String> complement(Set<String> set) {
        Set<String> universe = new HashSet<>();
        for (int i = 33; i <= 126; i++) {
            universe.add(String.valueOf((char) i));
        }
        Set<String> result = new HashSet<>(universe);
        result.removeAll(set);
        return result;
    }

    private Set<String> difference(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>(set1);
        result.removeAll(set2);
        return result;
    }

    // Método auxiliar para convertir un valor de nodo a un conjunto
    private Set<String> parseSet(String setString) {
        Set<String> result = new HashSet<>();
        if (setString.contains(",")) {
            String[] elements = setString.split(",");
            for (String element : elements) {
                result.add(element.trim());
            }
        } else if (setString.contains("~")) {
            String[] range = setString.split("~");
            String start = range[0].trim();
            String end = range[1].trim();
            if (isNumeric(start) && isNumeric(end)) {
                int startNum = Integer.parseInt(start);
                int endNum = Integer.parseInt(end);
                for (int i = startNum; i <= endNum; i++) {
                    result.add(String.valueOf(i));
                }
            } else if (start.length() == 1 && end.length() == 1) {
                char startChar = start.charAt(0);
                char endChar = end.charAt(0);
                for (char ch = startChar; ch <= endChar; ch++) {
                    result.add(String.valueOf(ch));
                }
            }
        } else {
            result.add(setString.trim());
        }
        return result;
    }

    // Método auxiliar para verificar si una cadena es numérica
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*
     * Metodo recursivo para recorrer el arbol y obtener los
     * conjuntos definidos con ID y BODY
     * */
    // Método para recorrer el árbol y obtener los conjuntos definidos
    public Map<String, Set<String>> obtenerConjuntosDefinidos() {
        Map<String, Set<String>> conjuntos = new HashMap<>();
        // Llamar al método recursivo para procesar el nodo raíz
        obtenerConjuntosRecursivo(this, conjuntos);
        return conjuntos;
    }

    // Método recursivo para procesar nodos y extraer conjuntos definidos
    private void obtenerConjuntosRecursivo(TreeNode<T> node, Map<String, Set<String>> conjuntos) {
        if (node == null) return;
        // Verificar si el nodo actual es de tipo EXPR_DEF
        if (node.getData().equals("EXPR_DEF")) {
            // Obtener los hijos de EXPR_DEF
            List<TreeNode<T>> children = node.getChildren();
            if (children.size() >= 3) {
                String conjuntoId = children.get(0).getData().toString(); // Nombre del conjunto (ID)
                TreeNode<T> exprDefNode = children.get(2); // Nodo CONJUNTO_EXPR

                // Procesar el nodo CONJUNTO_EXPR para obtener la definición del conjunto
                Set<String> conjuntoValores = procesarExprDef(exprDefNode);

                System.out.println("Nombre: " + conjuntoId + " datos " + conjuntoValores);
                conjuntos.put(conjuntoId, conjuntoValores);
            }
        }
        // Recorrer recursivamente los nodos hijos
        for (TreeNode<T> child : node.getChildren()) {
            obtenerConjuntosRecursivo(child, conjuntos);
        }
    }

    // Método para procesar el nodo CONJUNTO_EXPR de forma recursiva y devolver un conjunto de elementos
    private Set<String> procesarExprDef(TreeNode<T> conjuntoExprNode) {
        Set<String> conjuntoElementos = new HashSet<>();
        if (conjuntoExprNode == null || !conjuntoExprNode.getData().equals("CONJUNTO_EXPR")) {
            return conjuntoElementos;
        }
        // Recorrer recursivamente los hijos de CONJUNTO_EXPR para obtener los elementos del conjunto
        for (TreeNode<T> child : conjuntoExprNode.getChildren()) {
            if (child.getData().equals("CONJUNTO_EXPR")) {
                // Si es otro nodo CONJUNTO_EXPR, procesarlo recursivamente
                conjuntoElementos.addAll(procesarExprDef(child));
            } else if (child.getData().equals("EXPR")) {
                // Si es un nodo EXPR, procesar la expresión y añadir los elementos al conjunto
                conjuntoElementos.addAll(procesarExpr(child));
            }
        }
        return conjuntoElementos;
    }

    // Método para procesar un nodo EXPR y convertirlo en un conjunto de elementos
    private Set<String> procesarExpr(TreeNode<T> exprNode) {
        Set<String> exprElementos = new HashSet<>();
        if (exprNode == null || !exprNode.getData().equals("EXPR")) {
            return exprElementos;
        }
        // Recorrer los hijos de EXPR para construir su representación
        StringBuilder exprValores = new StringBuilder();
        for (TreeNode<T> child : exprNode.getChildren()) {
            exprValores.append(child.getData().toString());
        }
        // Procesar el rango o los elementos individuales
        String exprStr = exprValores.toString();
        exprElementos.addAll(parseSet(exprStr));
        return exprElementos;
    }
}
