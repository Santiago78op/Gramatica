package com.julian;

import java.util.ArrayList;
import java.util.List;

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

    public  void printTree(TreeNode<T> node, String appender) {
        System.out.println(appender + node.getData());
        for (TreeNode<T> each : node.getChildren()) {
            printTree(each, appender + appender);
        }
    }

}
