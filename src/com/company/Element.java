package com.company;

import com.company.matrixes.Matrix;

import java.util.Arrays;

public class Element {

    private int id;

    private Node[] nodes;
    private Matrix dNdyDiff = new Matrix(4, 4);
    private Matrix dNdxDiff = new Matrix(4, 4);
    private Matrix formFunctionValues = new Matrix(4, 4);
    private Matrix[] jacobians = new Matrix[4];
    private Matrix localMatrixH;
    private Matrix localMatrixC;
    private Matrix localVectorP;

    public Element(int id, Node[] nodes) {
        this.id = id;
        this.nodes = nodes;
        this.localMatrixH = new Matrix(4, 4);
        this.localMatrixC = new Matrix(4, 4);
        this.localVectorP = new Matrix(4, 1);
    }

    public void addSubMatrixToH(Matrix localSubMatrix) {
        try {
            localMatrixH.addMatrixAtIndex(0, 0, localSubMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSubMatrixToC(Matrix localSubMatrix){
        try {
            localMatrixC.addMatrixAtIndex(0, 0, localSubMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSubVectorToP(Matrix localSubMatrix){
        try {
            localVectorP.addMatrixAtIndex(0, 0, localSubMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Matrix getLocalMatrixH() {
        return localMatrixH;
    }

    public Matrix getLocalMatrixC() {
        return localMatrixC;
    }

    public Matrix getLocalVectorP() {
        return localVectorP;
    }

    public Matrix getFormFunctionValues() {
        return formFunctionValues;
    }

    public void setFormFunctionValues(Matrix formFunctionValues) {
        this.formFunctionValues = formFunctionValues;
    }

    public void setdNdyDiff(Matrix dNdyDiff) {
        this.dNdyDiff = dNdyDiff;
    }

    public void setdNdxDiff(Matrix dNdxDiff) {
        this.dNdxDiff = dNdxDiff;
    }

    public Matrix getdNdyDiff() {
        return dNdyDiff;
    }

    public Matrix getdNdxDiff() {
        return dNdxDiff;
    }

    public Matrix getJacobians(int index) {
        return jacobians[index];
    }

    public void setJacobians(int index, Matrix jacobian) {
        jacobians[index] = jacobian;
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

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", nodes=" + Arrays.asList(nodes) +
                '}';
    }
}
