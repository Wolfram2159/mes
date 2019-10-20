package com.company;

public class Node {
    private int id;
    private int x;
    private int y;
    private float t;
    private boolean B;

    public Node(int id, int x, int y, float t, boolean b) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.t = t;
        B = b;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", B=" + B +
                '}';
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

    public float getT() {
        return t;
    }

    public void setT(float t) {
        this.t = t;
    }

    public boolean isB() {
        return B;
    }

    public void setB(boolean b) {
        B = b;
    }
}
