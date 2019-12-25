package com.company.matrixes;

import java.util.List;

public class MatrixMapper {
    private final static int INTEGRAL_POINTS_COUNT = 4;

    public static SimpleMatrix convertMatrix(Matrix sourceMatrix) {
        SimpleMatrix result = new SimpleMatrix(4, 4);
        for (int integralPoint = 0; integralPoint < INTEGRAL_POINTS_COUNT; integralPoint++) {
            List<Double> valuesForPoint = sourceMatrix.getMatrixForPoint(integralPoint);
            result.addValueAt(integralPoint, 0, valuesForPoint.get(0));
            result.addValueAt(integralPoint, 1, valuesForPoint.get(1));
            result.addValueAt(integralPoint, 2, valuesForPoint.get(2));
            result.addValueAt(integralPoint, 3, valuesForPoint.get(3));
        }
        return result;
    }
}
