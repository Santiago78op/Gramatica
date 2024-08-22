package com.julian;

import java.util.ArrayList;

public class Tree {

    private String root;
    private ArrayList<Tree> children;

    public Tree(String root) {
        this.root = root;
        this.children = new ArrayList<>();
    }

    public void addChildren(Tree child){
        this.children.add(child);
    }

    public void printTree(Tree tree){
        for(Tree child : tree.children){
            printTree(child);
        }
        System.out.println(tree.root);
    }
}