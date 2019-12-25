package com.company;

import com.company.matrixes.SimpleMatrix;

import java.util.Arrays;

public class Element {

    private int id;

    private Node[] nodes;
    private SimpleMatrix dNdyDiff = new SimpleMatrix(4, 4);
    private SimpleMatrix dNdxDiff = new SimpleMatrix(4, 4);
    private SimpleMatrix formFunctionValues = new SimpleMatrix(4, 4);
    private SimpleMatrix[] jacobians = new SimpleMatrix[4];
    private SimpleMatrix localMatrixH;
    private SimpleMatrix localMatrixC;
    private SimpleMatrix localVectorP;

    public Element(int id, Node[] nodes) {
        this.id = id;
        this.nodes = nodes;
        this.localMatrixH = new SimpleMatrix(4, 4);
        this.localMatrixC = new SimpleMatrix(4, 4);
        this.localVectorP = new SimpleMatrix(4, 1);
    }

    public void addSubMatrixToH(SimpleMatrix localSubMatrix) {
        try {
            localMatrixH.addMatrixAtIndex(0, 0, localSubMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSubMatrixToC(SimpleMatrix localSubMatrix){
        try {
            localMatrixC.addMatrixAtIndex(0, 0, localSubMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSubVectorToP(SimpleMatrix localSubMatrix){
        try {
            localVectorP.addMatrixAtIndex(0, 0, localSubMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SimpleMatrix getLocalMatrixH() {
        return localMatrixH;
    }

    public SimpleMatrix getLocalMatrixC() {
        return localMatrixC;
    }

    public SimpleMatrix getLocalVectorP() {
        return localVectorP;
    }

    public SimpleMatrix getFormFunctionValues() {
        return formFunctionValues;
    }

    public void setFormFunctionValues(SimpleMatrix formFunctionValues) {
        this.formFunctionValues = formFunctionValues;
    }

    public void setdNdyDiff(SimpleMatrix dNdyDiff) {
        this.dNdyDiff = dNdyDiff;
    }

    public void setdNdxDiff(SimpleMatrix dNdxDiff) {
        this.dNdxDiff = dNdxDiff;
    }

    public SimpleMatrix getdNdyDiff() {
        return dNdyDiff;
    }

    public SimpleMatrix getdNdxDiff() {
        return dNdxDiff;
    }

    public SimpleMatrix getJacobians(int index) {
        return jacobians[index];
    }

    public void setJacobians(int index, SimpleMatrix jacobian) {
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
