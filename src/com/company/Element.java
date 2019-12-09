package com.company;

import java.util.Arrays;
import java.util.List;

public class Element {
    private final static int ELEMENT_NODES = 4;

    private int id;

    private Node[] nodes;
    private double[][] matrixH = new double[4][4];

    public Element(int id, Node[] nodes) {
        this.id = id;
        this.nodes = nodes;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", nodes=" + Arrays.asList(nodes) +
                '}';
    }
}
