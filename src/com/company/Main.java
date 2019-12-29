package com.company;

import com.company.matrixes.SimpleMatrix;

public class Main {
    //
    private final static double conductivity = 25;
    private final static double specificHeat = 700;
    private final static double density = 7800;
    private final static double alfa = 300;
    private final static double ambientTemp = 1200;
    private final static double initialTemp = 100;
    private final static int nH = 31;
    private final static int nW = 31;
    private final static float gridH = 1f / 10f;
    private final static float gridW = 1f / 10f;
    private final static double stepTime = 1;
    private final static double simulationTime = 20;

    private final static float elementH = gridH * (1f / (nH - 1));
    private final static float elementW = gridW * (1f / (nW - 1));

    public static void main(String[] args) {
        Universal universal = new Universal();
        GlobalDate globalDate = new GlobalDate(elementH, elementW, nH, nW);
        GlobalMatrix globalMatrix = new GlobalMatrix(globalDate.getnN());
        Grid grid = new Grid(globalDate);
        grid.setUniversal(universal);
        grid.setGlobalMatrix(globalMatrix);
        try {
            grid.calculateMatrixesForAllElements(conductivity, specificHeat, density, alfa, ambientTemp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SimpleMatrix initialTempMatrix = new SimpleMatrix(globalDate.getnN(), 1);
        initialTempMatrix.addValuesToAllCells(initialTemp);
        try {
            ProblemCalculator problemCalculator = new ProblemCalculator(globalMatrix, stepTime, simulationTime, initialTempMatrix);
            problemCalculator.solveProblem();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
