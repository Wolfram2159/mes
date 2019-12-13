package com.company.matrixes;

public class MatrixForNDifferential extends Matrix {

    public MatrixForNDifferential() {
        super();
    }

    @Override
    protected double firstFunction(IntegralPoint point) {
        return -0.25 * (1 - point.e);
    }

    @Override
    protected double secondFunction(IntegralPoint point) {
        return -0.25 * (1 + point.e);
    }

    @Override
    protected double thirdFunction(IntegralPoint point) {
        return 0.25 * (1 + point.e);
    }

    @Override
    protected double fourthFunction(IntegralPoint point) {
        return 0.25 * (1 - point.e);
    }
}
