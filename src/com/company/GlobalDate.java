package com.company;

public class GlobalDate {
    private float H;
    private float W;
    private int nH;
    private int nW;

    private int nN;
    private int nE;

    public GlobalDate(float h, float w, int nH, int nW) {
        this.H = h;
        this.W = w;
        this.nH = nH;
        this.nW = nW;
        nN = nH * nW;
        nE = (nH - 1) * (nW - 1);
    }

    public float getH() {
        return H;
    }

    public void setH(float h) {
        H = h;
    }

    public float getW() {
        return W;
    }

    public void setW(float w) {
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
