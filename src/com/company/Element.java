package com.company;

import com.company.matrixes.SimpleMatrix;

import java.util.Arrays;
import java.util.List;

public class Element {
    private final static int ELEMENT_NODES = 4;

    private int id;

    private Node[] nodes;
    private SimpleMatrix dNdyDiff = new SimpleMatrix(4, 4);
    private SimpleMatrix dNdxDiff = new SimpleMatrix(4, 4);
    private SimpleMatrix[] jacobians = new SimpleMatrix[4];
    private SimpleMatrix[] smallHMatrixes = new SimpleMatrix[4];
    private SimpleMatrix H;

    public Element(int id, Node[] nodes) {
        this.id = id;
        this.nodes = nodes;
    }

    public void calculateH() {
        SimpleMatrix H = new SimpleMatrix(4, 4);
        for (int i = 0; i < 4; i++) {
            try {
                H.addMatrixAtIndex(0,0, smallHMatrixes[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.H = H;
    }

    public SimpleMatrix getSmallHMatrixes(int index) {
        return smallHMatrixes[index];
    }

    public void setSmallHMatrixes(int index, SimpleMatrix smallHMatrixes) {
        this.smallHMatrixes[index] = smallHMatrixes;
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
