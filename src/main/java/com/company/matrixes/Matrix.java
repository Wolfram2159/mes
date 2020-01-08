package com.company.matrixes;

public class Matrix {
    //HxW
    private int n;
    private int m;
    private double[][] matrix;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        matrix = new double[n][m];
    }

    public Matrix(double[][] matrix) {
        this.n = matrix.length;
        this.m = matrix[0].length;
        this.matrix = new double[n][m];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                this.matrix[i][j] = matrix[i][j];
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

    public Matrix getRowAsMatrix(int i) {
        Matrix row = new Matrix(1, m);
        for (int k = 0; k < m; k++) {
            row.addValueAt(0, k, matrix[i][k]);
        }
        return row;
    }

    public Matrix getCellAsMatrix(int i, int j) {
        Matrix cell = new Matrix(1, 1);
        double value = this.getValueAt(i, j);
        cell.addValueAt(0, 0, value);
        return cell;
    }

    public static Matrix multiplyMatrixes(Matrix a, Matrix b) throws Exception {
        if (a.getM() != b.getN()) {
            throw new Exception("cannot multiply matrixes");
        }
        Matrix product = new Matrix(a.getN(), b.getM());
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

    public void addMatrixAtIndex(int i, int j, Matrix matrix) throws Exception {
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

    public static Matrix addMatrixes(Matrix a, Matrix b) throws Exception {
        if (a.m != b.m || a.n != b.n) {
            throw new Exception("cannot add matrixes");
        }
        Matrix result = new Matrix(a.n, a.m);
        for (int n = 0; n < a.n; n++) {
            for (int m = 0; m < a.m; m++) {
                result.matrix[n][m] = a.getValueAt(n, m) + b.getValueAt(n, m);
            }
        }
        return result;
    }

    public static Matrix inverseMatrix(Matrix matrixToInverse) {
        double det = matrixToInverse.matrix[0][0] * matrixToInverse.matrix[1][1] - matrixToInverse.matrix[1][0] * matrixToInverse.matrix[0][1];
        det = 1 / det;
        Matrix inversedMatrix = new Matrix(matrixToInverse.n, matrixToInverse.m);
        inversedMatrix.matrix[0][0] = matrixToInverse.matrix[1][1];
        inversedMatrix.matrix[1][1] = matrixToInverse.matrix[0][0];
        inversedMatrix.matrix[1][0] *= -1;
        inversedMatrix.matrix[0][1] *= -1;
        inversedMatrix.multiplyByScalar(det);
        return inversedMatrix;
    }

    public Matrix inverseThisMatrix() {
        int n = this.matrix.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i)
            b[i][i] = 1;

        // Transform the matrix into an upper triangle
        gaussian(this.matrix, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                            -= this.matrix[index[j]][i]*b[index[i]][k];

        // Perform backward substitutions
        for (int i=0; i<n; ++i)
        {
            x[n-1][i] = b[index[n-1]][i]/this.matrix[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j)
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k)
                {
                    x[j][i] -= this.matrix[index[j]][k]*x[k][i];
                }
                x[j][i] /= this.matrix[index[j]][j];
            }
        }
        return new Matrix(x);
    }

    public void gaussian(double a[][], int index[]) {
        int n = index.length;
        double c[] = new double[n];

        // Initialize the index
        for (int i = 0; i < n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = j + 1; l < n; ++l)
                    a[index[i]][l] -= pj * a[index[j]][l];
            }
        }
    }

    public Matrix transponateMatrix() {
        Matrix transponated = new Matrix(this.m, this.n);
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

    public void divideByScalar(double scalar) {
        multiplyByScalar(1 / scalar);
    }

    public double calculateDeterminate() {
        double wyznacznik = 0;

        if (matrix.length == 1 && matrix[0].length == 1) {
            wyznacznik = matrix[0][0];
        } else if (matrix.length != matrix[0].length) {
            throw new RuntimeException("Nie można wyznaczyć wyznacznika dla macierzy która nie jest kwadratowa");
        } else if (matrix.length == 2 && matrix[0].length == 2) {
            wyznacznik = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
        } else {
            double[][] nTab = new double[matrix.length + (matrix.length - 1)][matrix[0].length];
            for (int i = 0, _i = 0; i < nTab.length; i++, _i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (_i < matrix.length && j < matrix[0].length) {
                        nTab[i][j] = matrix[_i][j];
                    } else {
                        _i = 0;
                        nTab[i][j] = matrix[_i][j];
                    }
                }
            }

            double iloczyn = 1;
            int _i;

            for (int i = 0; i < matrix.length; i++) {
                _i = i;
                for (int j = 0; j < matrix[0].length; j++) {
                    iloczyn *= nTab[_i][j];
                    _i++;
                }
                wyznacznik += iloczyn;
                iloczyn = 1;
            }

            iloczyn = 1;
            for (int i = 0; i < matrix.length; i++) {
                _i = i;
                for (int j = matrix[0].length - 1; j >= 0; j--) {
                    iloczyn *= nTab[_i][j];
                    _i++;
                }
                wyznacznik -= iloczyn;
                iloczyn = 1;
            }
        }
        return wyznacznik;
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

    public void addValuesToAllCells(double initialTemp) {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                this.matrix[i][j] = initialTemp;
            }
        }
    }
}
