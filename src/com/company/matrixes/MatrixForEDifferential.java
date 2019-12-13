package com.company.matrixes;


public class MatrixForEDifferential extends Matrix {

    public MatrixForEDifferential() {
        super();
    }

    @Override
    protected double firstFunction(IntegralPoint point) {
        return -0.25 * (1 - point.n);
    }

    @Override
    protected double secondFunction(IntegralPoint point) {
        return 0.25 * (1 - point.n);
    }

    @Override
    protected double thirdFunction(IntegralPoint point) {
        return 0.25 * (1 + point.n);
    }

    @Override
    protected double fourthFunction(IntegralPoint point) {
        return -0.25 * (1 + point.n);
    }
}
