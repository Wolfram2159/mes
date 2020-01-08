package com.company.global;

import com.company.grid.Element;
import com.company.grid.Node;
import com.company.matrixes.Matrix;

public class GlobalMatrix {
    private Matrix hGlobal;
    private Matrix cGlobal;
    private Matrix pGlobal;

    public GlobalMatrix(int nodesNumber) {
        hGlobal = new Matrix(nodesNumber, nodesNumber);
        cGlobal = new Matrix(nodesNumber, nodesNumber);
        pGlobal = new Matrix(nodesNumber, 1);
    }

    public void agregateElement(Element element) throws Exception {
        Node[] nodes = element.getNodes();
        Matrix localMatrixH = element.getLocalMatrixH();
        Matrix localMatrixC = element.getLocalMatrixC();
        Matrix localVectorP = element.getLocalVectorP();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                int i = nodes[y].getId();
                int j = nodes[x].getId();
                Matrix hCell = localMatrixH.getCellAsMatrix(y, x);
                addToH(i, j, hCell);
                Matrix cCell = localMatrixC.getCellAsMatrix(y, x);
                addToC(i, j, cCell);
            }
            int i = nodes[y].getId();
            int j = 0;
            Matrix pCell = localVectorP.getCellAsMatrix(y, 0);
            addToP(i, j, pCell);
        }
    }

    private void addToH(int i, int j, Matrix cell) throws Exception {
        hGlobal.addMatrixAtIndex(i, j, cell);
    }

    private void addToC(int i, int j, Matrix cell) throws Exception {
        cGlobal.addMatrixAtIndex(i, j, cell);
    }

    private void addToP(int i, int j, Matrix cell) throws Exception {
        pGlobal.addMatrixAtIndex(i, j, cell);
    }

    public Matrix gethGlobal() {
        return hGlobal;
    }

    public Matrix getcGlobal() {
        return cGlobal;
    }

    public Matrix getpGlobal() {
        return pGlobal;
    }
}
