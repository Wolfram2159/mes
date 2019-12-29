package com.company;

import com.company.matrixes.SimpleMatrix;

public class GlobalMatrix {
    private SimpleMatrix hGlobal;
    private SimpleMatrix cGlobal;
    private SimpleMatrix pGlobal;

    public GlobalMatrix(int nodesNumber) {
        hGlobal = new SimpleMatrix(nodesNumber, nodesNumber);
        cGlobal = new SimpleMatrix(nodesNumber, nodesNumber);
        pGlobal = new SimpleMatrix(nodesNumber, 1);
    }

    public void agregateElement(Element element) throws Exception {
        Node[] nodes = element.getNodes();
        SimpleMatrix localMatrixH = element.getLocalMatrixH();
        SimpleMatrix localMatrixC = element.getLocalMatrixC();
        SimpleMatrix localVectorP = element.getLocalVectorP();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                int i = nodes[y].getId();
                int j = nodes[x].getId();
                SimpleMatrix hCell = localMatrixH.getCellAsMatrix(y, x);
                addToH(i, j, hCell);
                SimpleMatrix cCell = localMatrixC.getCellAsMatrix(y, x);
                addToC(i, j, cCell);
            }
            int i = nodes[y].getId();
            int j = 0;
            SimpleMatrix pCell = localVectorP.getCellAsMatrix(y, 0);
            addToP(i, j, pCell);
        }
    }

    public void addToH(int i, int j, SimpleMatrix cell) throws Exception {
        hGlobal.addMatrixAtIndex(i, j, cell);
    }

    public void addToC(int i, int j, SimpleMatrix cell) throws Exception {
        cGlobal.addMatrixAtIndex(i, j, cell);
    }

    public void addToP(int i, int j, SimpleMatrix cell) throws Exception {
        pGlobal.addMatrixAtIndex(i, j, cell);
    }

    public SimpleMatrix gethGlobal() {
        return hGlobal;
    }

    public SimpleMatrix getcGlobal() {
        return cGlobal;
    }

    public SimpleMatrix getpGlobal() {
        return pGlobal;
    }
}
