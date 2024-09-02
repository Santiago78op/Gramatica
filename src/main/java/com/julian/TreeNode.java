package com.julian;

import java.util.*;

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
        // Verificar si el nodo actual es de tipo CONJ_PART
        if (node.getData().equals("EXPR_DEF")) {
            // Obtener los hijos de CONJ_PART
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
