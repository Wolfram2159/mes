package com.company;

import java.util.Arrays;
import java.util.List;

public class Element {
    private final static int ELEMENT_NODES = 4;

    private int id;
    private int x;
    private int y;

    private Node[] nodes;

    public Element(int id, int x, int y, Node[] nodes) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.nodes = nodes;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
                ", x=" + x +
                ", y=" + y +
                ", nodes=" + Arrays.asList(nodes) +
                '}';
    }
}
