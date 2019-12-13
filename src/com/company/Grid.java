package com.company;

import com.company.matrixes.SimpleMatrix;

import java.util.List;

public class Grid {
    private Node[] nodeList;
    private Element[] elementList;
    private static final int ELEMENT_NODES_COUNT = 4;

    private GlobalDate globalDate;
    private Universal universal;

    public Grid(GlobalDate globalDate) {
        this.globalDate = globalDate;
        nodeList = new Node[globalDate.getnN()];
        elementList = new Element[globalDate.getnE()];
        buildGrid();
    }

    private void buildGrid() {
        buildNodes();
        buildElements();
    }

    private void buildNodes() {
        int id = 0;
        for (int i = 0; i < globalDate.getnW(); i++) {
            for (int j = 0; j < globalDate.getnH(); j++) {
                Node node = new Node(id, i, j, 0, checkB(i, j));
                nodeList[id] = node;
                id++;
            }
        }
    }

    private boolean checkB(int x, int y) {
        return (x == 0 || x == (globalDate.getnW() - 1) || y == 0 || y == (globalDate.getnH() - 1));
    }

    private void buildElements() {
        int id = 0;
        for (int i = 0; i < globalDate.getnW() - 1; i++) {
            for (int j = 0; j < globalDate.getnH() - 1; j++) {
                Element element = new Element(id, determineNodesForElement(i, j));
                elementList[id] = element;
                id++;
            }
        }
    }

    private Node[] determineNodesForElement(int x, int y) {
        Node[] nodes = new Node[ELEMENT_NODES_COUNT];
        nodes[0] = getNode(x, y);
        nodes[1] = getNode(x + 1, y);
        nodes[2] = getNode(x + 1, y + 1);
        nodes[3] = getNode(x, y + 1);
        return nodes;
    }

    public Node getNode(int x, int y) {
        int id = globalDate.getnH() * x + y;
        return nodeList[id];
    }

    public Element getElement(int x, int y) {
        int id = (globalDate.getnH() - 1) * x + y;
        return elementList[id];
    }

    public Element getElementById(int id) {
        return elementList[id];
    }

    public void calculateMatrixForAllElements(double K) {
        for (Element element : elementList) {
            SimpleMatrix matrixdndx = new SimpleMatrix(4, 4);
            SimpleMatrix matrixdndy = new SimpleMatrix(4, 4);
            System.out.println("Printing for el " + element.getId());
            for (int i = 0; i < 4; i++) {
                List<Double> EDiff = universal.geteDifferentials().getMatrixForPoint(i);
                List<Double> NDiff = universal.getnDifferentials().getMatrixForPoint(i);
                SimpleMatrix matrixA = calculateJacobianMatrix(element, EDiff, NDiff);
                element.setJacobians(i, matrixA);
                matrixA.inverseMatrix();
                for (int j = 0; j < 4; j++) {
                    SimpleMatrix matrixC = new SimpleMatrix(2, 1);
                    matrixC.addValueAt(0, 0, EDiff.get(j));
                    matrixC.addValueAt(1, 0, NDiff.get(j));
                    try {
                        SimpleMatrix matrixB = SimpleMatrix.multiplyMatrixes(matrixA, matrixC);
                        matrixdndx.addValueAt(i, j, matrixB.getValueAt(0, 0));
                        matrixdndy.addValueAt(i, j, matrixB.getValueAt(1, 0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            matrixdndy.printMatrix();
            element.setdNdxDiff(matrixdndx);
            element.setdNdyDiff(matrixdndy);
        }
        calculateHMatrix(K);
    }

    public void calculateHMatrix(double K) {
        for (Element element : elementList) {
            System.out.println("ELEMENTOS");
            SimpleMatrix dx = element.getdNdxDiff();
            SimpleMatrix dy = element.getdNdyDiff();
            for (int i = 0; i < 4; i++) {
                //DN/DX
                SimpleMatrix jacobian = element.getJacobians(i);
                double detJ = jacobian.calculateDeteminate();
                SimpleMatrix rowdx = dx.getRowAsMatrix(i);
                SimpleMatrix rowdxT = rowdx.transponateMatrix();
                SimpleMatrix rowRowTdx = new SimpleMatrix();
                SimpleMatrix rowRowTdy = new SimpleMatrix();
                try {
                    rowRowTdx = SimpleMatrix.multiplyMatrixes(rowdxT, rowdx);
                    rowRowTdx.multiplyByScalar(detJ);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //DN/DY
                SimpleMatrix rowdy = dy.getRowAsMatrix(i);
                SimpleMatrix rowdyT = rowdy.transponateMatrix();
                try {
                    rowRowTdy = SimpleMatrix.multiplyMatrixes(rowdyT, rowdy);
                    rowRowTdy.multiplyByScalar(detJ);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    rowRowTdx.addMatrixAtIndex(0,0, rowRowTdy);
                    rowRowTdx.multiplyByScalar(K);
                    element.setSmallHMatrixes(i, rowRowTdx);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            element.calculateH();
        }
    }

    private SimpleMatrix calculateJacobianMatrix(Element element, List<Double> EDiff, List<Double> NDiff) {
        SimpleMatrix jacobian = new SimpleMatrix(2, 2);
        double dxdE = 0;
        double dydE = 0;
        double dxdN = 0;
        double dydN = 0;
        for (int i = 0; i < 4; i++) {
            dxdE += EDiff.get(i) * element.getNodes()[i].getX();
            dydE += EDiff.get(i) * element.getNodes()[i].getY();
            dxdN += NDiff.get(i) * element.getNodes()[i].getX();
            dydN += NDiff.get(i) * element.getNodes()[i].getY();
        }
        jacobian.addValueAt(0, 0, dxdE);
        jacobian.addValueAt(0, 1, dydE);
        jacobian.addValueAt(1, 0, dxdN);
        jacobian.addValueAt(1, 1, dydN);
        return jacobian;
    }

    public void setUniversal(Universal universal) {
        this.universal = universal;
    }
}
