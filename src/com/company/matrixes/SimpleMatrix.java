package com.company.matrixes;

public class SimpleMatrix {
    //HxW
    private int n;
    private int m;
    private double[][] matrix;

    public SimpleMatrix() {

    }

    public SimpleMatrix(int n, int m) {
        this.n = n;
        this.m = m;
        matrix = new double[n][m];
    }

    public SimpleMatrix(SimpleMatrix a) {
        this.n = a.getN();
        this.m = a.getM();
        matrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = a.matrix[i][j];
            }
        }
    }

    public double getValueAt(int i, int j) {
        return matrix[i][j];
    }

    public void addValueAt(int i, int j, double value) {
        matrix[i][j] += value;
    }

    public double[] getColumnAt(int i) {
        double[] column = new double[getN()];
        for (int j = 0; j < getN(); j++) {
            column[j] = matrix[j][i];
        }
        return column;
    }

    public double[] getRowAt(int i) {
        return matrix[i];
    }

    public SimpleMatrix getRowAsMatrix(int i) {
        SimpleMatrix row = new SimpleMatrix(1, m);
        for (int k = 0; k < m; k++) {
            row.addValueAt(0, k, matrix[i][k]);
        }
        return row;
    }

    public SimpleMatrix getColumnAsMatrix(int i) {
        SimpleMatrix column = new SimpleMatrix(this.n, 1);
        for (int k = 0; k < n; k++) {
            column.addValueAt(k, 0, matrix[k][i]);
        }
        return column;
    }

    public static SimpleMatrix multiplyMatrixes(SimpleMatrix a, SimpleMatrix b) throws Exception {
        if (a.getM() != b.getN()) {
            throw new Exception("cannot multiply matrixes");
        }
        SimpleMatrix product = new SimpleMatrix(a.getN(), b.getM());
        for (int i = 0; i < a.getN(); i++) {
            for (int j = 0; j < b.getM(); j++) {
                double val = 0;
                double[] rowFromA = a.getRowAt(i);
                double[] columnFromB = b.getColumnAt(j);
                for (int k = 0; k < a.getM(); k++) {
                    val += rowFromA[k] * columnFromB[k];
                }
                product.addValueAt(i, j, val);
            }
        }
        return product;
    }

    public void addMatrixAtIndex(int i, int j, SimpleMatrix matrix) throws Exception {
        int rangeX = matrix.getM() + j;
        int rangeY = matrix.getN() + i;
        if (rangeX > this.m) {
            throw new Exception("going out of X bound");
        } else if (rangeY > this.n) {
            throw new Exception("going out of Y bound");
        }
        double[][] matrixToAdd = matrix.getMatrix();
        for (int k = 0; k < matrix.getN(); k++) {
            for (int l = 0; l < matrix.getM(); l++) {
                this.matrix[i + k][j + l] += matrixToAdd[k][l];
            }
        }
    }

    public static SimpleMatrix addMatrixes(SimpleMatrix a, SimpleMatrix b) throws Exception {
        if (a.m != b.m || a.n != b.n) {
            throw new Exception("cannot add matrixes");
        }
        SimpleMatrix result = new SimpleMatrix(a.n, a.m);
        for (int n = 0; n < a.n; n++) {
            for (int m = 0; m < a.m; m++) {
                result.matrix[n][m] = a.getValueAt(n, m) + b.getValueAt(n, m);
            }
        }
        return result;
    }

    public void inverseMatrix() {
        double det = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
        det = 1 / det;
        double a = matrix[0][0];
        matrix[0][0] = matrix[1][1];
        matrix[1][1] = a;
        matrix[1][0] *= -1;
        matrix[0][1] *= -1;
        multiplyByScalar(det);
    }

    public SimpleMatrix transponateMatrix() {
        SimpleMatrix transponated = new SimpleMatrix(this.m, this.n);
        for (int i = 0; i < this.m; i++) {
            double[] column = getColumnAt(i);
            for (int j = 0; j < column.length; j++) {
                transponated.addValueAt(i, j, column[j]);
            }
        }
        return transponated;
    }

    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] *= scalar;
            }
        }
    }

    public double calculateDeterminate() {
        return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
    }

    public void printMatrix() {
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getM(); j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
