package com.julian;

import java.util.*;

public class TreeNode<T> {
    T data;
    List<TreeNode<T>> children;

    // Variable para almacenar los conjuntos definidos
    Map<String, Set<String>> conjuntosDefinidos;
    // Variable para almacenar las operaciones definidas
    Map<String, Set<String>> operacionesDefinidas;
    // Variable para almacenar las operaciones definidas
    Map<String, Set<String>> conjuntosOperacionesId;

    TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
        this.conjuntosDefinidos = new HashMap<>();
        this.operacionesDefinidas = new HashMap<>();
        this.conjuntosOperacionesId = new HashMap<>();
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
        // Obtener los conjuntos definidos antes de realizar las operaciones
        this.conjuntosDefinidos = obtenerConjuntosDefinidos();

        // Obtener las operaciones definidas antes de evaluar las operaciones
        this.operacionesDefinidas = operacionesDefinidas();
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
            String currentOperator = "";  // Operador actual

            for (TreeNode<T> child : conjExprNode.getChildren()) {
                // Verifica si el hijo es un nodo de operador (OPER_EXPR)
                if (child.getData().equals("OPER_EXPR")) {
                    currentOperator = obtenerOperadorDeExpr(child);  // Obtiene el operador
                }
                // Verifica si el hijo es un nodo de conjuntos (EXPRE_CONJ)
                else if (child.getData().equals("EXPRE_CONJ")) {
                    Set<String> currentSet = obtenerConjuntoDeExprConj(child);  // Obtiene el conjunto
                    // Si ya tenemos un operador, aplicamos la operación
                    if (!currentOperator.isEmpty()) {
                        result = aplicarOperacion(currentOperator, result, currentSet);
                    } else {
                        // Si es el primer conjunto, lo asignamos al resultado inicial
                        result = currentSet;
                    }
                } 
                // Caso donde el nodo hijo es otro CONJ_EXPR
                else if (child.getData().equals("CONJ_EXPR")) {
                    Set<String> evaluatedSet = evaluarConjExpr(child);
                    if (!currentOperator.isEmpty()) {
                        result = aplicarOperacion(currentOperator, result, evaluatedSet);
                    } else {
                        result = evaluatedSet;
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

    // Método para obtener el operador de un nodo OPER_EXPR
    private String obtenerOperadorDeExpr(TreeNode<T> operExprNode) {
        // Asumimos que el operador siempre será el primer hijo de OPER_EXPR
        if (operExprNode.getChildren().size() > 0) {
            return operExprNode.getChildren().get(0).getData().toString();
        }
        return "";
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

    // Método para recorrer el árbol y obtener las operaciones definidas
    public Map<String, Set<String>> operacionesDefinidas() {
        Map<String, Set<String>> operaciones = new HashMap<>();
        // Llamar al método recursivo para procesar el nodo raíz
        obtenerOperacionRecursivo(this, operaciones);
        return operaciones;
    }

    // Método recursivo para procesar nodos y extraer operaciones definidas
    private void obtenerOperacionRecursivo(TreeNode<T> node, Map<String, Set<String>> operaciones) {
        if (node == null) return;

        // Verificar si el nodo actual es de tipo OPER_DEF
        if (node.getData().equals("OPER_DEF")) {
            // Obtener los hijos de OPER_DEF
            List<TreeNode<T>> children = node.getChildren();
            if (children.size() >= 3) {
                String operacionId = children.get(0).getData().toString(); // Nombre de la operacion (ID)
                TreeNode<T> operDefNode = children.get(2); // Nodo CONJ_EXPR

                TreeNode<T> operExprNode = operDefNode.getChildren().get(0);
                // Procesar el nodo CONJ_EXPR para obtener la definición del conjunto
                Set<String> conjuntoOperaciones = procesarOperDef(operExprNode);

                System.out.println("OperacionId: " + operacionId + " Operaciones: " + conjuntoOperaciones);
            }
        }

        // Recorrer recursivamente los nodos hijos
        for (TreeNode<T> child : node.getChildren()) {
            obtenerOperacionRecursivo(child, operaciones);
        }
    }

    // Método para procesar el nodo CONJUNTO_EXPR de forma recursiva y devolver un conjunto de elementos
    private Set<String> procesarOperDef(TreeNode<T> operDefNode) {
        Set<String> conjuntoOperaciones = new HashSet<>();
        if (operDefNode == null || !operDefNode.getData().equals("OPER_EXPR")) {
            return conjuntoOperaciones;
        }

        // Recorrer recursivamente los hijos de CONJUNTO_EXPR para obtener los elementos del conjunto
        for (TreeNode<T> child : operDefNode.getChildren()) {
            if (child.getData().equals("OPER_EXPR")) {
                conjuntoOperaciones.addAll(procesarOperDef(child));
            } else if (child.getData().equals("SIMBOL_EXPR")) {
                conjuntoOperaciones.addAll(procesarSimbolExpr(child));
            }
        }
        return conjuntoOperaciones;
    }

    // Método para procesar un nodo EXPR y convertirlo en un conjunto de elementos
    private Set<String> procesarSimbolExpr(TreeNode<T> exprNode) {
        Set<String> exprElementos = new HashSet<>();
        if (exprNode == null || !exprNode.getData().equals("SIMBOL_EXPR")) {
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
                conjuntoElementos.addAll(procesarExprDef(child));
            } else if (child.getData().equals("EXPR")) {
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