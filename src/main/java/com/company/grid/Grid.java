package com.company.grid;

import com.company.global.Universal;
import com.company.global.GlobalDate;
import com.company.global.GlobalMatrix;
import com.company.matrixes.UniversalMatrix;
import com.company.matrixes.MatrixMapper;
import com.company.matrixes.Matrix;

import java.util.List;

public class Grid {
    private Node[] nodeList;
    private Element[] grid;
    private static final int ELEMENT_NODES_COUNT = 4;

    private GlobalDate globalDate;
    private Universal universal;
    private GlobalMatrix globalMatrix;

    public Grid(GlobalDate globalDate) {
        this.globalDate = globalDate;
        nodeList = new Node[globalDate.getnN()];
        grid = new Element[globalDate.getnE()];
        buildGrid();
        universal = new Universal();
    }

    private void buildGrid() {
        buildNodes();
        buildElements();
    }

    private void buildNodes() {
        int id = 0;
        for (double i = 0; i < (double) globalDate.getnW() * globalDate.getW(); i += globalDate.getW()) {
            for (double j = 0; j < (double) globalDate.getnH() * globalDate.getH(); j += globalDate.getH()) {
                Node node = new Node(id, i, j, 0, checkBC(i, j));
                nodeList[id] = node;
                id++;
            }
        }
    }

    private boolean checkBC(double x, double y) {
        return (x == 0 || x == ((globalDate.getnW() - 1) * globalDate.getW()) || y == 0 || y == ((globalDate.getnH() - 1) * globalDate.getH()));
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

    private final static int FORM_FUNCTION_COUNT = 4;
    private final static int INTEGRAL_POINTS_COUNT = 4;

    public void calculateMatrixesForAllElements(double K, double c, double ro, double alfa, double ambientTemperature) throws Exception {
        calculateDifferentials();
        calculateHMatrixes(K);
        calculateBoundaryConditionsAndPVectors(alfa, ambientTemperature);
        calculateCMatrixes(c, ro);
        agregateElements();
    }

    private void agregateElements() throws Exception {
        for (Element element : grid) {
            globalMatrix.agregateElement(element);
        }
    }

    private void calculateDifferentials() throws Exception {
        for (Element element : grid) {
            calculateJacobians1D(element);
            Matrix xDifferentialMatrix = new Matrix(4, 4);
            Matrix yDifferentialMatrix = new Matrix(4, 4);
            for (int integralPoint = 0; integralPoint < INTEGRAL_POINTS_COUNT; integralPoint++) {
                List<Double> eDifferentialMatrix = universal.geteDifferentials().getMatrixForPoint(integralPoint);
                List<Double> nDifferentialMatrix = universal.getnDifferentials().getMatrixForPoint(integralPoint);
                //[A] * {b} = {c} <=> {b} = [A]-1 * {c}
                Matrix jacobian = calculateJacobian2D(element, eDifferentialMatrix, nDifferentialMatrix);
                element.setJacobian2D(integralPoint, jacobian);
                Matrix inversedMatrixA = Matrix.inverseMatrix(jacobian);
                for (int formFunction = 0; formFunction < FORM_FUNCTION_COUNT; formFunction++) {
                    Matrix matrixC = new Matrix(2, 1);
                    matrixC.addValueAt(0, 0, eDifferentialMatrix.get(formFunction));
                    matrixC.addValueAt(1, 0, nDifferentialMatrix.get(formFunction));

                    Matrix matrixB = Matrix.multiplyMatrixes(inversedMatrixA, matrixC);
                    xDifferentialMatrix.addValueAt(integralPoint, formFunction, matrixB.getValueAt(0, 0));
                    yDifferentialMatrix.addValueAt(integralPoint, formFunction, matrixB.getValueAt(1, 0));
                }
            }
            element.setdNdxDiff(xDifferentialMatrix);
            element.setdNdyDiff(yDifferentialMatrix);
            Matrix formFunctionValues = MatrixMapper.convertMatrix(universal.getFunctionsValues());
            element.setFormFunctionValues(formFunctionValues);
        }
    }

    private void calculateHMatrixes(double K) throws Exception {
        for (Element element : grid) {
            Matrix xDifferential = element.getdNdxDiff();
            Matrix yDifferential = element.getdNdyDiff();
            for (int integralPoint = 0; integralPoint < INTEGRAL_POINTS_COUNT; integralPoint++) {
                Matrix jacobian = element.getJacobian2D(integralPoint);
                double detJ = jacobian.calculateDeterminate();
                //DN/DX
                Matrix xRowDiff = xDifferential.getRowAsMatrix(integralPoint);
                Matrix xDifferentialIntegral = multiplyRowByColumn(xRowDiff);
                //DN/DY
                Matrix yRowDiff = yDifferential.getRowAsMatrix(integralPoint);
                Matrix yDifferentialIntegral = multiplyRowByColumn(yRowDiff);
                //Local H
                Matrix hLocalMatrix = Matrix.addMatrixes(xDifferentialIntegral, yDifferentialIntegral);
                hLocalMatrix.multiplyByScalar(K * detJ);
                element.addSubMatrixToH(hLocalMatrix);
            }
        }
    }

    private void calculateBoundaryConditionsAndPVectors(double alfa, double ambientTemperature) throws Exception {
        final int[][] indexes = {
                {0, 1},
                {1, 2},
                {2, 3},
                {3, 0}
        };

        List<UniversalMatrix> functionsValuesForBoundaryConditions = universal.getFunctionsValuesForBoundaryConditions();

        for (Element element : grid) {
            for (int side = 0; side < 4; side++) {
                Node firstNode = element.getNodes()[indexes[side][0]];
                Node secondNode = element.getNodes()[indexes[side][1]];
                double detJ = element.getJacobian1D(side);
                if (checkIfNodesFromBound(firstNode, secondNode)) {
                    UniversalMatrix boundaryCondForSide = functionsValuesForBoundaryConditions.get(side);
                    Matrix matrix = MatrixMapper.convertMatrix(boundaryCondForSide);
                    for (int integralPoint = 0; integralPoint < boundaryCondForSide.getSizeOfMatrix(); integralPoint++) {
                        Matrix rowForIntegralPoint = matrix.getRowAsMatrix(integralPoint);
                        //BoundaryConditions
                        Matrix subMatrixForBoundaryCond = multiplyRowByColumn(rowForIntegralPoint);
                        subMatrixForBoundaryCond.multiplyByScalar(alfa * detJ);
                        element.addSubMatrixToH(subMatrixForBoundaryCond);
                        //P Vector
                        Matrix subMatrixForPVector = rowForIntegralPoint.transponateMatrix();
                        subMatrixForPVector.multiplyByScalar(detJ * alfa * ambientTemperature);
                        element.addSubVectorToP(subMatrixForPVector);
                    }
                }
            }
        }
    }


    private boolean checkIfNodesFromBound(Node first, Node second) {
        return ((first.getX() == 0 && second.getX() == 0) ||
                (first.getY() == 0 && second.getY() == 0) ||
                (first.getX() == (globalDate.getnW() - 1) * globalDate.getW() && second.getX() == (globalDate.getnW() - 1) * globalDate.getW()) ||
                (first.getY() == (globalDate.getnH() - 1) * globalDate.getH() && second.getY() == (globalDate.getnH() - 1) * globalDate.getH()));
    }

    private Matrix multiplyRowByColumn(Matrix rowForIntegralPoint) throws Exception {
        Matrix columnForIntegralPoint = rowForIntegralPoint.transponateMatrix();
        return Matrix.multiplyMatrixes(columnForIntegralPoint, rowForIntegralPoint);
    }

    private void calculateCMatrixes(double c, double ro) throws Exception {
        for (Element element : grid) {
            Matrix formFunctionValues = element.getFormFunctionValues();
            for (int integralPoint = 0; integralPoint < INTEGRAL_POINTS_COUNT; integralPoint++) {
                Matrix jacobian = element.getJacobian2D(integralPoint);
                double detJ = jacobian.calculateDeterminate();
                Matrix formFunctionsRow = formFunctionValues.getRowAsMatrix(integralPoint);
                Matrix subMatrixC = multiplyRowByColumn(formFunctionsRow);
                subMatrixC.multiplyByScalar(c * ro * detJ);
                element.addSubMatrixToC(subMatrixC);
            }
        }
    }

    private Matrix calculateJacobian2D(Element element, List<Double> EDiff, List<Double> NDiff) {
        Matrix jacobian = new Matrix(2, 2);
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

    private void calculateJacobians1D(Element element) {
        final int[][] indexes = {
                {0, 1},
                {1, 2},
                {2, 3},
                {3, 0}
        };

        for (int[] index : indexes) {
            int jacobianIndex = index[0];
            Node firstNode = element.getNodes()[index[0]];
            Node secondNode = element.getNodes()[index[1]];
            double deltaX = Math.pow((secondNode.getX() - firstNode.getX()), 2);
            double deltaY = Math.pow((secondNode.getY() - firstNode.getY()), 2);
            double deltaLength = Math.sqrt(deltaX + deltaY) / 2;
            element.setJacobian1D(jacobianIndex, deltaLength);
        }
    }

    public void setUniversal(Universal universal) {
        this.universal = universal;
    }

    public void setGlobalMatrix(GlobalMatrix globalMatrix) {
        this.globalMatrix = globalMatrix;
    }
}
