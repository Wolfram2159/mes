package com.company;

import com.company.matrixes.Matrix;
import com.company.matrixes.MatrixForEDifferential;
import com.company.matrixes.MatrixForFormFunctionsValue;
import com.company.matrixes.MatrixForNDifferential;
import com.company.matrixes.MesPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Universal {
    private Matrix eDifferentials;
    private Matrix nDifferentials;
    private Matrix functionsValues;

    private List<MesPoint> pointsList;

    public Universal(MesPoint... points) {
        pointsList = new ArrayList<>();
        pointsList.addAll(Arrays.asList(points));
        eDifferentials = new MatrixForEDifferential();
        nDifferentials = new MatrixForNDifferential();
        functionsValues = new MatrixForFormFunctionsValue();
        calculateMatrixes();
    }

    private void calculateMatrixes() {
        for (MesPoint mesPoint : pointsList) {
            eDifferentials.addPointAndCalculateDifferentials(mesPoint);
            nDifferentials.addPointAndCalculateDifferentials(mesPoint);
            functionsValues.addPointAndCalculateDifferentials(mesPoint);
        }
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
