package com.company.matrixes;

import java.util.List;

public class MatrixMapper {
    public static SimpleMatrix convertMatrix(Matrix sourceMatrix) {
        int size = sourceMatrix.getSizeOfMatrix();
        SimpleMatrix result = new SimpleMatrix(size, 4);
        for (int integralPoint = 0; integralPoint < size; integralPoint++) {
            List<Double> valuesForPoint = sourceMatrix.getMatrixForPoint(integralPoint);
            result.addValueAt(integralPoint, 0, valuesForPoint.get(0));
            result.addValueAt(integralPoint, 1, valuesForPoint.get(1));
            result.addValueAt(integralPoint, 2, valuesForPoint.get(2));
            result.addValueAt(integralPoint, 3, valuesForPoint.get(3));
        }
        return result;
    }
}