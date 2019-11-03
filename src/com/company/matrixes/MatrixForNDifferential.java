package com.company.matrixes;

public class MatrixForNDifferential extends Matrix {

    public MatrixForNDifferential() {
        super();
    }

    @Override
    protected double firstFunction(MesPoint point) {
        return -0.25 * (1 - point.e);
    }

    @Override
    protected double secondFunction(MesPoint point) {
        return -0.25 * (1 + point.e);
    }

    @Override
    protected double thirdFunction(MesPoint point) {
        return 0.25 * (1 + point.e);
    }

    @Override
    protected double fourthFunction(MesPoint point) {
        return 0.25 * (1 - point.e);
    }
}
