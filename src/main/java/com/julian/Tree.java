package com.julian;

public class Tree<T> {

    private TreeNode<T> root;

    public Tree(T rootData) {
        root = new TreeNode<>(rootData);
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void addChild(TreeNode<T> parent, T childData) {
        TreeNode<T> childNode = new TreeNode<>(childData);
        parent.addChild(childNode);
    }

}
