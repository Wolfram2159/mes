package com.company;

import com.company.matrixes.Matrix;

public class ProblemCalculator {
    private Matrix newHInversion;
    private Matrix newP;
    private double stepTime;
    private double simulationTime;
    private Matrix initialTemp;
    private Matrix searchingTemp;
    private Matrix cDivideByDTau;
    private GlobalMatrix globalMatrix;

    public ProblemCalculator(GlobalMatrix globalMatrix, double stepTime, double simulationTime, Matrix initialTemp) throws Exception {
        this.stepTime = stepTime;
        this.simulationTime = simulationTime;
        this.initialTemp = initialTemp;
        this.globalMatrix = globalMatrix;
        calculateConstantValues();
    }

    private void calculateConstantValues() throws Exception {
        Matrix cDivideByTau = globalMatrix.getcGlobal();
        cDivideByTau.divideByScalar(stepTime);
        this.cDivideByDTau = cDivideByTau;
        this.newHInversion = globalMatrix.gethGlobal();
        this.newHInversion.addMatrixAtIndex(0, 0, cDivideByTau);
        this.newHInversion = this.newHInversion.inverseThisMatrix();

        calculateNewP(initialTemp);
    }

    public void solveProblem() throws Exception {
        for (int i = (int) stepTime; i <= simulationTime; i += stepTime) {
            searchingTemp = Matrix.multiplyMatrixes(this.newHInversion, this.newP);
            searchMinAndMax(searchingTemp);
            calculateNewP(searchingTemp);
        }
    }

    private void calculateNewP(Matrix temperature) throws Exception {
        Matrix subNewP = Matrix.multiplyMatrixes(cDivideByDTau, temperature);
        subNewP.addMatrixAtIndex(0, 0, globalMatrix.getpGlobal());
        this.newP = subNewP;
    }

    private void searchMinAndMax(Matrix searchingTemp) {
        double[][] temp = searchingTemp.getMatrix();
        double min = temp[0][0];
        double max = 0;
        for (double[] rows : temp) {
            for (double row : rows) {
                if (row < min){
                    min = row;
                }
                if (row > max){
                    max = row;
                }
            }
        }
        String format = String.format("Min temp = %.2f, Max temp = %.2f", min, max);
        System.out.println(format);
    }
}
