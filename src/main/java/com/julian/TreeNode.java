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
                obtenerOperaciones(child);
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

    // Método para obtener el conjunto de un nodo EXPR_CONJ
    private Set<String> obtenerConjuntoDeExprConj(TreeNode<T> exprConjNode) {
        if (exprConjNode.getData().equals("EXPRE_CONJ")) {
            Set<String> conjuntoElementos = new HashSet<>();
            for (TreeNode<T> child : exprConjNode.getChildren()) {
                if (child.getData().equals("EXPRE_CONJ")) {
                    conjuntoElementos.addAll(obtenerConjuntoDeExprConj(child));
                } else {
                    String conjuntoId = child.getChildren().get(1).getData().toString();
                    conjuntoElementos.add(conjuntoId);
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
            String operationName = evalNode.getChildren().get(6).getData().toString();
            Set<String> conjunto = parseSet(evalNode.getChildren().get(3).getData().toString());
            System.out.println("Aplicando operación '" + operationName + "' al conjunto: " + conjunto);
            return conjunto;
        }
        return new HashSet<>();
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

    // Método para aplicar las leyes de conjuntos a las expresiones
    private void aplicarLeyesConjuntos(TreeNode<T> node) {
        if (node == null) return;
        if (node.getData().equals("CONJ_EXPR") || node.getData().equals("OPER_EXPR")) {
            // Aplicar las leyes de DeMorgan, distributivas, etc.
            aplicarLeyDeMorgan(node);
            aplicarLeyAsociativa(node);
            aplicarLeyDistributiva(node);
            aplicarLeyIdempotente(node);
            aplicarLeyAbsorcion(node);
        }
        for (TreeNode<T> child : node.getChildren()) {
            aplicarLeyesConjuntos(child);
        }
    }

    private void aplicarLeyDeMorgan(TreeNode<T> node) {
        // Implementa la lógica para aplicar las leyes de DeMorgan
    }
    private void aplicarLeyAsociativa(TreeNode<T> node) {
        // Implementa la lógica para aplicar las propiedades asociativas
    }
    private void aplicarLeyDistributiva(TreeNode<T> node) {
        // Implementa la lógica para aplicar las propiedades distributivas
    }
    private void aplicarLeyIdempotente(TreeNode<T> node) {
        // Implementa la lógica para aplicar las propiedades idempotentes
    }
    private void aplicarLeyAbsorcion(TreeNode<T> node) {
        // Implementa la lógica para aplicar las propiedades de absorción
    }

    // Método para obtener operaciones y conjuntos definidos
    public void obtenerOperaciones(TreeNode<T> node) {
        if (node.getData().toString().equals("OPER_LIST")) {
            for (TreeNode<T> operPartNode : node.getChildren()) {
                if (operPartNode.getData().toString().equals("OPER_PART")) {
                    String operationName = operPartNode.getChildren().get(2).getData().toString();
                    TreeNode<T> operDefNode = operPartNode.getChildren().get(4);
                    if (operDefNode.getData().toString().equals("CONJ_EXPR")) {
                        System.out.println("Evaluando operación: " + operationName);
                        Set<String> resultadoOperacion = evaluarConjExpr(operDefNode);
                        System.out.println("Resultado de la operación '" + operationName + "': " + resultadoOperacion);
                    }
                } else if (operPartNode.getData().toString().equals("OPER_LIST")) {
                    obtenerOperaciones(operPartNode);
                }
            }
        }
    }

    // Método para evaluar un nodo CONJ_EXPR de manera recursiva
    private Set<String> evaluarConjExpr(TreeNode<T> conjExprNode) {
        if (conjExprNode.getData().equals("CONJ_EXPR")) {
            for (TreeNode<T> child : conjExprNode.getChildren()) {
                if (child.getData().toString().equals("CONJ_EXPR")) {
                    evaluarConjExpr(child);
                } else if (child.getData().toString().equals("OPER_EXPR")) {
                    return obtenerOperExpr(child);
                }
            }
        }
        return new HashSet<>();
    }

    public Set<String> obtenerOperExpr(TreeNode<T> conjExprNode) {
        Set<String> result = new HashSet<>();
        String operator = "";
        for (TreeNode<T> child : conjExprNode.getChildren()) {
            if (child.getData().equals("OPER_EXPR")) {
                operator = obtenerOperator(child);
            } else if (child.getData().equals("EXPRE_CONJ")) {
                Set<String> currentSet = obtenerConjuntoDeExprConj(child);
                if (!operator.isEmpty()) {
                    result = aplicarOperacion(operator, result, currentSet);
                } else {
                    result = currentSet;
                }
            }
        }
        return result;
    }

    public String obtenerOperator(TreeNode<T> simbolExpr) {
        String operator = "";
        for (TreeNode<T> child : simbolExpr.getChildren()) {
            if (child.getData().toString().equals("OPER_EXPR")) {
                return obtenerOperator(child);
            } else if (child.getData().toString().equals("SIMBOL_EXPR")) {
                String signo = child.getChildren().getFirst().getData().toString();
                return operator + signo;
            }
        }
        return operator;
    }

    // Método para aplicar la operación al conjunto
    private Set<String> aplicarOperacion(String operator, Set<String> set1, Set<String> set2) {
        return switch (operator) {
            case "U" -> union(set1, set2);
            case "&" -> intersection(set1, set2);
            case "^" -> complement(set1);
            case "-" -> difference(set1, set2);
            default -> new HashSet<>();
        };
    }
}
