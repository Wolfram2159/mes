package com.company;

import com.company.matrixes.Matrix;
import com.company.matrixes.MatrixForEDifferential;
import com.company.matrixes.MatrixForFormFunctionsValue;
import com.company.matrixes.MatrixForNDifferential;
import com.company.matrixes.IntegralPoint;

import java.util.ArrayList;
import java.util.List;

public class Universal {
    private final static double e = Math.sqrt((double) 1 / 3);
    private final static double n = Math.sqrt((double) 1 / 3);
    private final static IntegralPoint firstIntegralPoint = new IntegralPoint(-e, -n);
    private final static IntegralPoint secondIntegralPoint = new IntegralPoint(e, -n);
    private final static IntegralPoint thirdIntegralPoint = new IntegralPoint(e, n);
    private final static IntegralPoint fourthIntegralPoint = new IntegralPoint(-e, n);
    private final static IntegralPoint[] gaussIntegralPoints = {
            firstIntegralPoint,
            secondIntegralPoint,
            thirdIntegralPoint,
            fourthIntegralPoint
    };

    private final static IntegralPoint firstSideFirstPoint = new IntegralPoint(-e, -1);
    private final static IntegralPoint firstSideSecondPoint = new IntegralPoint(e, -1);
    private final static IntegralPoint secondSideFirstPoint = new IntegralPoint(1, -n);
    private final static IntegralPoint secondSideSecondPoint = new IntegralPoint(1, n);
    private final static IntegralPoint thirdSideFirstPoint = new IntegralPoint(e, 1);
    private final static IntegralPoint thirdSideSecondPoint = new IntegralPoint(-e, 1);
    private final static IntegralPoint fourthSideFirstPoint = new IntegralPoint(-1, n);
    private final static IntegralPoint fourthSideSecondPoint = new IntegralPoint(-1, -n);
    private final static IntegralPoint[][] boundaryConditionsIntegralPoints = {
            {
                    firstSideFirstPoint,
                    firstSideSecondPoint
            },
            {
                    secondSideFirstPoint,
                    secondSideSecondPoint
            },
            {
                    thirdSideFirstPoint,
                    thirdSideSecondPoint
            },
            {
                    fourthSideFirstPoint,
                    fourthSideSecondPoint
            }
    };

    private Matrix eDifferentials;
    private Matrix nDifferentials;
    private Matrix functionsValues;
    private List<Matrix> functionsValuesForBoundaryConditions;


    public Universal() {
        eDifferentials = new MatrixForEDifferential();
        nDifferentials = new MatrixForNDifferential();
        functionsValues = new MatrixForFormFunctionsValue();
        functionsValuesForBoundaryConditions = new ArrayList<>();//new MatrixForFormFunctionsValue();
        calculateMatrixes();
    }

    private void calculateMatrixes() {
        for (IntegralPoint integralPoint : gaussIntegralPoints) {
            eDifferentials.addPointAndCalculateDifferentials(integralPoint);
            nDifferentials.addPointAndCalculateDifferentials(integralPoint);
            functionsValues.addPointAndCalculateDifferentials(integralPoint);
        }
        for (IntegralPoint[] oneSideIntegralPoints : boundaryConditionsIntegralPoints) {
            Matrix oneSide = new MatrixForFormFunctionsValue();
            oneSide.addPointAndCalculateDifferentials(oneSideIntegralPoints);
            functionsValuesForBoundaryConditions.add(oneSide);
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

    public List<Matrix> getFunctionsValuesForBoundaryConditions() {
        return functionsValuesForBoundaryConditions;
    }
}
