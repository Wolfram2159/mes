package com.company;

public class Node {
    private int id;
    private int x;
    private int y;
    private float t;
    private boolean BC;

    public Node(int id, int x, int y, float t, boolean BC) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.t = t;
        this.BC = BC;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", t=" + t +
                ", B=" + BC +
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

    public boolean isBC() {
        return BC;
    }

    public void setBC(boolean BC) {
        this.BC = BC;
    }
}
