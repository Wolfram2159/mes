package com.company.global;

import com.company.matrixes.UniversalMatrix;
import com.company.matrixes.MatrixForEDifferential;
import com.company.matrixes.MatrixForFormFunctionsValue;
import com.company.matrixes.MatrixForNDifferential;
import com.company.matrixes.IntegralPoint;

import java.util.ArrayList;
import java.util.List;

public class Universal {
    private final double e = Math.sqrt((double) 1 / 3);
    private final double n = Math.sqrt((double) 1 / 3);
    private final IntegralPoint firstIntegralPoint = new IntegralPoint(-e, -n);
    private final IntegralPoint secondIntegralPoint = new IntegralPoint(e, -n);
    private final IntegralPoint thirdIntegralPoint = new IntegralPoint(e, n);
    private final IntegralPoint fourthIntegralPoint = new IntegralPoint(-e, n);
    private final IntegralPoint[] gaussIntegralPoints = {
            firstIntegralPoint,
            secondIntegralPoint,
            thirdIntegralPoint,
            fourthIntegralPoint
    };

    private final IntegralPoint firstSideFirstPoint = new IntegralPoint(-e, -1);
    private final IntegralPoint firstSideSecondPoint = new IntegralPoint(e, -1);
    private final IntegralPoint secondSideFirstPoint = new IntegralPoint(1, -n);
    private final IntegralPoint secondSideSecondPoint = new IntegralPoint(1, n);
    private final IntegralPoint thirdSideFirstPoint = new IntegralPoint(e, 1);
    private final IntegralPoint thirdSideSecondPoint = new IntegralPoint(-e, 1);
    private final IntegralPoint fourthSideFirstPoint = new IntegralPoint(-1, n);
    private final IntegralPoint fourthSideSecondPoint = new IntegralPoint(-1, -n);
    private final IntegralPoint[][] boundaryConditionsIntegralPoints = {
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

    private UniversalMatrix eDifferentials;
    private UniversalMatrix nDifferentials;
    private UniversalMatrix functionsValues;
    private List<UniversalMatrix> functionsValuesForBoundaryConditions;


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
            UniversalMatrix oneSide = new MatrixForFormFunctionsValue();
            oneSide.addPointAndCalculateDifferentials(oneSideIntegralPoints);
            functionsValuesForBoundaryConditions.add(oneSide);
        }
    }

    public UniversalMatrix geteDifferentials() {
        return eDifferentials;
    }

    public UniversalMatrix getnDifferentials() {
        return nDifferentials;
    }

    public UniversalMatrix getFunctionsValues() {
        return functionsValues;
    }

    public List<UniversalMatrix> getFunctionsValuesForBoundaryConditions() {
        return functionsValuesForBoundaryConditions;
    }
}
