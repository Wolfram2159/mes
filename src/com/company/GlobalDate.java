package com.company;

public class GlobalDate {
    private double H;
    private double W;
    private int nH;
    private int nW;

    private int nN;
    private int nE;

    public GlobalDate(double h, double w, int nH, int nW) {
        this.H = h;
        this.W = w;
        this.nH = nH;
        this.nW = nW;
        nN = nH * nW;
        nE = (nH - 1) * (nW - 1);
    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double getW() {
        return W;
    }

    public void setW(double w) {
        W = w;
    }

    public int getnH() {
        return nH;
    }

    public void setnH(int nH) {
        this.nH = nH;
    }

    public int getnW() {
        return nW;
    }

    public void setnW(int nW) {
        this.nW = nW;
    }

    public int getnN() {
        return nN;
    }

    public void setnN(int nN) {
        this.nN = nN;
    }

    public int getnE() {
        return nE;
    }

    public void setnE(int nE) {
        this.nE = nE;
    }
}
