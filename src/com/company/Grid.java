package com.company;

import com.company.matrixes.Matrix;
import com.company.matrixes.MatrixMapper;
import com.company.matrixes.SimpleMatrix;

import java.util.List;

public class Grid {
    private Node[] nodeList;
    private Element[] grid;
    private static final int ELEMENT_NODES_COUNT = 4;

    private GlobalDate globalDate;
    private Universal universal;

    public Grid(GlobalDate globalDate) {
        this.globalDate = globalDate;
        nodeList = new Node[globalDate.getnN()];
        grid = new Element[globalDate.getnE()];
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
                grid[id] = element;
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
        return grid[id];
    }

    public Element getElementById(int id) {
        return grid[id];
    }

    private final static int FORM_FUNCTION_COUNT = 4;
    private final static int INTEGRAL_POINTS_COUNT = 4;

    public void calculateMatrixesForAllElements(double K, double c, double ro, double alfa) throws Exception {
        calculateDifferentials();
        calculateHMatrixes(K);
        calculateBoundaryConditions(alfa);
        calculateCMatrixes(c, ro);
        calculatePVectors(alfa);
    }

    private void calculateDifferentials() throws Exception {
        for (Element element : grid) {
            SimpleMatrix xDifferentialMatrix = new SimpleMatrix(4, 4);
            SimpleMatrix yDifferentialMatrix = new SimpleMatrix(4, 4);
            for (int integralPoint = 0; integralPoint < INTEGRAL_POINTS_COUNT; integralPoint++) {
                List<Double> eDifferentialMatrix = universal.geteDifferentials().getMatrixForPoint(integralPoint);
                List<Double> nDifferentialMatrix = universal.getnDifferentials().getMatrixForPoint(integralPoint);
                //[A] * {b} = {c} <=> {b} = [A]-1 * {c}
                SimpleMatrix matrixA = calculateJacobianMatrixForElement(element, eDifferentialMatrix, nDifferentialMatrix);
                element.setJacobians(integralPoint, matrixA);
                matrixA.inverseMatrix();
                for (int formFunction = 0; formFunction < FORM_FUNCTION_COUNT; formFunction++) {
                    SimpleMatrix matrixC = new SimpleMatrix(2, 1);
                    matrixC.addValueAt(0, 0, eDifferentialMatrix.get(formFunction));
                    matrixC.addValueAt(1, 0, nDifferentialMatrix.get(formFunction));

                    SimpleMatrix matrixB = SimpleMatrix.multiplyMatrixes(matrixA, matrixC);
                    xDifferentialMatrix.addValueAt(integralPoint, formFunction, matrixB.getValueAt(0, 0));
                    yDifferentialMatrix.addValueAt(integralPoint, formFunction, matrixB.getValueAt(1, 0));
                }
            }
            element.setdNdxDiff(xDifferentialMatrix);
            element.setdNdyDiff(yDifferentialMatrix);
            SimpleMatrix formFunctionValues = MatrixMapper.convertMatrix(universal.getFunctionsValues());
            element.setFormFunctionValues(formFunctionValues);
        }
    }

    private void calculateHMatrixes(double K) throws Exception {
        for (Element element : grid) {
            SimpleMatrix xDifferential = element.getdNdxDiff();
            SimpleMatrix yDifferential = element.getdNdyDiff();
            for (int integralPoint = 0; integralPoint < INTEGRAL_POINTS_COUNT; integralPoint++) {
                SimpleMatrix jacobian = element.getJacobians(integralPoint);
                double detJ = jacobian.calculateDeterminate();
                //DN/DX
                SimpleMatrix xRowDiff = xDifferential.getRowAsMatrix(integralPoint);
                SimpleMatrix xDifferentialIntegral = multiplyRowByColumn(xRowDiff);
                //DN/DY
                SimpleMatrix yRowDiff = yDifferential.getRowAsMatrix(integralPoint);
                SimpleMatrix yDifferentialIntegral = multiplyRowByColumn(yRowDiff);
                //Local H
                SimpleMatrix hLocalMatrix = SimpleMatrix.addMatrixes(xDifferentialIntegral, yDifferentialIntegral);
                hLocalMatrix.multiplyByScalar(K * detJ);
                element.addSubMatrixToH(hLocalMatrix);
            }
            element.getLocalMatrixH().printMatrix();
        }
    }

    private void calculateBoundaryConditions(double alfa) throws Exception {
        final int[][] indexes = {
                {0, 1},
                {1, 2},
                {2, 3},
                {3, 0}
        };

        List<Matrix> functionsValuesForBoundaryConditions = universal.getFunctionsValuesForBoundaryConditions();

        for (Element element : grid) {
            for (int side = 0; side < 4; side++) {
                Node firstNode = element.getNodes()[indexes[side][0]];
                Node secondNode = element.getNodes()[indexes[side][1]];
                SimpleMatrix jacobians = element.getJacobians(side);
                double detJ = jacobians.calculateDeterminate();
                if (checkIfNodesFromBound(firstNode, secondNode)){
                    Matrix boundaryCondForSide = functionsValuesForBoundaryConditions.get(side);
                    SimpleMatrix matrix = MatrixMapper.convertMatrix(boundaryCondForSide);
                    for (int integralPoint = 0; integralPoint < boundaryCondForSide.getSizeOfMatrix(); integralPoint++) {
                        SimpleMatrix rowForIntegralPoint = matrix.getRowAsMatrix(integralPoint);
                        SimpleMatrix subMatrixForBoundaryCond = multiplyRowByColumn(rowForIntegralPoint);
                        subMatrixForBoundaryCond.multiplyByScalar(alfa * detJ);
                        element.addSubMatrixToH(subMatrixForBoundaryCond);
                    }
                }
            }
        }
    }


    private boolean checkIfNodesFromBound(Node first, Node second){
        return  ((first.getX() == 0 && second.getX() == 0) ||
                (first.getY() == 0 && second.getY() == 0) ||
                (first.getX() == globalDate.getnW() - 1 && second.getX() == globalDate.getnW() - 1) ||
                (first.getY() == globalDate.getnH() - 1 && second.getY() == globalDate.getnH() - 1));
    }

    private SimpleMatrix multiplyRowByColumn(SimpleMatrix rowForIntegralPoint) throws Exception {
        SimpleMatrix columnForIntegralPoint = rowForIntegralPoint.transponateMatrix();
        return SimpleMatrix.multiplyMatrixes(columnForIntegralPoint, rowForIntegralPoint);
    }

    private void calculateCMatrixes(double c, double ro) throws Exception {
        for (Element element : grid) {
            SimpleMatrix formFunctionValues = element.getFormFunctionValues();
            for (int integralPoint = 0; integralPoint < INTEGRAL_POINTS_COUNT; integralPoint++) {
                SimpleMatrix jacobian = element.getJacobians(integralPoint);
                double detJ = jacobian.calculateDeterminate();
                SimpleMatrix formFunctionsRow = formFunctionValues.getRowAsMatrix(integralPoint);
                SimpleMatrix subMatrixC = multiplyRowByColumn(formFunctionsRow);
                subMatrixC.multiplyByScalar(c * ro * detJ);
                element.addSubMatrixToC(subMatrixC);
            }
            element.getLocalMatrixC().printMatrix();
        }
    }

    private void calculatePVectors(double alfa) {
        for (Element element : grid) {
            SimpleMatrix formFunctionValues = element.getFormFunctionValues();
            for (int integralPoint = 0; integralPoint < INTEGRAL_POINTS_COUNT; integralPoint++) {
                SimpleMatrix jacobian = element.getJacobians(integralPoint);
                double detJ = jacobian.calculateDeterminate();
                SimpleMatrix formFunctionsRow = formFunctionValues.getRowAsMatrix(integralPoint);
                SimpleMatrix subVectorP = formFunctionsRow.transponateMatrix();
                subVectorP.multiplyByScalar(alfa * detJ);
                element.addSubVectorToP(subVectorP);
            }
            element.getLocalVectorP().printMatrix();
        }
    }

    private SimpleMatrix calculateJacobianMatrixForElement(Element element, List<Double> EDiff, List<Double> NDiff) {
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
