package com.company;

import com.company.matrixes.SimpleMatrix;

public class ProblemCalculator {
    private SimpleMatrix newHInversion;
    private SimpleMatrix newP;
    private double stepTime;
    private double simulationTime;
    private SimpleMatrix initialTemp;
    private SimpleMatrix searchingTemp;
    private SimpleMatrix cDivideByDTau;
    private GlobalMatrix globalMatrix;

    public ProblemCalculator(GlobalMatrix globalMatrix, double stepTime, double simulationTime, SimpleMatrix initialTemp) throws Exception {
        this.stepTime = stepTime;
        this.simulationTime = simulationTime;
        this.initialTemp = initialTemp;
        this.globalMatrix = globalMatrix;
        calculateConstantValues();
    }

    private void calculateConstantValues() throws Exception {
        SimpleMatrix cDivideByTau = globalMatrix.getcGlobal();
        cDivideByTau.divideByScalar(stepTime);
        this.cDivideByDTau = cDivideByTau;
        this.newHInversion = globalMatrix.gethGlobal();
        this.newHInversion.addMatrixAtIndex(0, 0, cDivideByTau);
        this.newHInversion = this.newHInversion.inverseThisMatrix();

        calculateNewP(initialTemp);
    }

    private void calculateNewP(SimpleMatrix temperature) throws Exception {
        SimpleMatrix subNewP = SimpleMatrix.multiplyMatrixes(cDivideByDTau, temperature);
        subNewP.addMatrixAtIndex(0, 0, globalMatrix.getpGlobal());
        this.newP = subNewP;
        //this.newP.printMatrix();
    }

    public void solveProblem() throws Exception {
        for (int i = (int) stepTime; i <= simulationTime; i += stepTime) {
            searchingTemp = SimpleMatrix.multiplyMatrixes(this.newHInversion, this.newP);
            searchMinAndMax(searchingTemp);
            calculateNewP(searchingTemp);
        }
    }

    private void searchMinAndMax(SimpleMatrix searchingTemp) {
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
