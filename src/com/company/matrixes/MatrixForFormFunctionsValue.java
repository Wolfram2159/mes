package com.company.matrixes;

public class MatrixForFormFunctionsValue extends Matrix {
    public MatrixForFormFunctionsValue() {
        super();
    }

    @Override
    protected double firstFunction(MesPoint point) {
        return 0.25 * (1 - point.e) * (1 - point.n);
    }

    @Override
    protected double secondFunction(MesPoint point) {
        return 0.25 * (1 + point.e) * (1 - point.n);
    }

    @Override
    protected double thirdFunction(MesPoint point) {
        return 0.25 * (1 + point.e) * (1 + point.n);
    }

    @Override
    protected double fourthFunction(MesPoint point) {
        return 0.25 * (1 - point.e) * (1 + point.n);
    }
}
