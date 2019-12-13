package com.company;

import com.company.matrixes.Matrix;
import com.company.matrixes.MatrixForEDifferential;
import com.company.matrixes.MatrixForFormFunctionsValue;
import com.company.matrixes.MatrixForNDifferential;
import com.company.matrixes.IntegralPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Universal {
    private Matrix eDifferentials;
    private Matrix nDifferentials;
    private Matrix functionsValues;

    private List<IntegralPoint> pointsList;

    public Universal(IntegralPoint... points) {
        pointsList = new ArrayList<>();
        pointsList.addAll(Arrays.asList(points));
        eDifferentials = new MatrixForEDifferential();
        nDifferentials = new MatrixForNDifferential();
        functionsValues = new MatrixForFormFunctionsValue();
        calculateMatrixes();
    }

    private void calculateMatrixes() {
        for (IntegralPoint integralPoint : pointsList) {
            eDifferentials.addPointAndCalculateDifferentials(integralPoint);
            nDifferentials.addPointAndCalculateDifferentials(integralPoint);
            functionsValues.addPointAndCalculateDifferentials(integralPoint);
        }
    }

    public Matrix geteDifferentials() {
        return eDifferentials;
    }

    public Matrix getnDifferentials() {
        return nDifferentials;
    }

    public Matrix getFunctionsValues() {
        return functionsValues;
    }

    public void printNMatrix() {
        nDifferentials.printMatrix();
    }

    public void printEMatrix() {
        eDifferentials.printMatrix();
    }

    public void printValuesMatrix() {
        functionsValues.printMatrix();
    }
}
