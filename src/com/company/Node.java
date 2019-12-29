package com.company;

public class Node {
    private int id;
    private double x;
    private double y;
    private float t;
    private boolean BC;

    public Node(int id, double x, double y, float t, boolean BC) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
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
