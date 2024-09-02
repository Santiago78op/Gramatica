package com.julian;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeNode<T> {
    T data;
    List<TreeNode<T>> children;

    TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
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

    // Método para evaluar las operaciones de conjuntos y mostrar resultados
    public void evaluateOperations() {
        if (data.toString().equals("BLOCK")) {
            // Evaluar cada sección del bloque
            for (TreeNode<T> child : children) {
                if (child.getData().toString().equals("OPER_LIST")) {
                    // Evaluar operaciones de conjuntos
                    for (TreeNode<T> operChild : child.getChildren()) {
                        Set<String> result = evaluateOperation(operChild);
                        System.out.println("Resultado de la operación '" + operChild.getChildren().get(0).getData() + "': " + result);
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

    // Método para evaluar una operación específica
    private Set<String> evaluateOperation(TreeNode<T> node) {
        if (!node.getData().toString().equals("OPER_PART")) {
            return new HashSet<>();
        }

        // Obtener los detalles de la operación
        String operation = node.getChildren().get(0).getData().toString();
        List<TreeNode<T>> operands = node.getChildren().subList(1, node.getChildren().size());

        // Evaluar la operación dependiendo del tipo
        switch (operation) {
            case "U": // Unión
                return union(evaluateOperation(operands.get(0)), evaluateOperation(operands.get(1)));
            case "&": // Intersección
                return intersection(evaluateOperation(operands.get(0)), evaluateOperation(operands.get(1)));
            case "^": // Complemento
                return complement(evaluateOperation(operands.get(0)));
            case "-": // Diferencia
                return difference(evaluateOperation(operands.get(0)), evaluateOperation(operands.get(1)));
            default:
                // Si es un conjunto, convertimos el conjunto a un Set
                return parseSet(operation);
        }
    }

    // Método para evaluar una evaluación específica de conjuntos
    private Set<String> evaluateEvaluation(TreeNode<T> evalNode) {
        if (!evalNode.getData().toString().equals("EVAL_PART")) {
            return new HashSet<>();
        }

        // Obtener el nombre de la operación y el conjunto a evaluar
        String operationName = evalNode.getChildren().get(6).getData().toString();
        Set<String> conjunto = parseSet(evalNode.getChildren().get(3).getData().toString());

        // Buscar el nodo de operación correspondiente y aplicar la operación al conjunto
        System.out.println("Aplicando operación '" + operationName + "' al conjunto: " + conjunto);

        // Encontrar el nodo de operación en el árbol
        for (TreeNode<T> child : this.getChildren()) {
            if (child.getData().toString().equals("OPER_LIST")) {
                for (TreeNode<T> operChild : child.getChildren()) {
                    if (operChild.getChildren().get(0).getData().toString().equals(operationName)) {
                        return evaluateOperationWithSet(operChild, conjunto);
                    }
                }
            }
        }

        // Retorna un conjunto vacío si no se encuentra la operación
        return new HashSet<>();
    }

    // Método para aplicar una operación específica a un conjunto dado
    private Set<String> evaluateOperationWithSet(TreeNode<T> operationNode, Set<String> conjunto) {
        // Obtener la operación y evaluar los nodos relacionados
        String operation = operationNode.getChildren().get(0).getData().toString();

        switch (operation) {
            case "U": // Unión
                return union(conjunto, evaluateOperation(operationNode.getChildren().get(1)));
            case "&": // Intersección
                return intersection(conjunto, evaluateOperation(operationNode.getChildren().get(1)));
            case "^": // Complemento
                return complement(conjunto);
            case "-": // Diferencia
                return difference(conjunto, evaluateOperation(operationNode.getChildren().get(1)));
            default:
                // Si no es una operación conocida, devolvemos el conjunto tal cual
                return conjunto;
        }
    }

    // Métodos para las operaciones de conjuntos
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
}
