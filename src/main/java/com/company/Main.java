package com.company;

import com.company.calculator.ProblemCalculator;
import com.company.entryData.EntryDataObject;
import com.company.global.GlobalDate;
import com.company.global.GlobalMatrix;
import com.company.grid.Grid;
import com.company.matrixes.Matrix;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Main {
    private static double conductivity;
    private static double specificHeat;
    private static double density;
    private static double alfa;
    private static double ambientTemp;
    private static double initialTemp;
    private static int nH;
    private static int nW;
    private static float gridH;
    private static float gridW;
    private static double stepTime;
    private static double simulationTime;

    public static void main(String[] args) {
        readValues();
        GlobalDate globalDate = new GlobalDate(gridH, gridW, nH, nW);
        GlobalMatrix globalMatrix = new GlobalMatrix(globalDate.getnN());
        Grid grid = new Grid(globalDate);
        grid.setGlobalMatrix(globalMatrix);
        try {
            grid.calculateMatrixesForAllElements(conductivity, specificHeat, density, alfa, ambientTemp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Matrix initialTempMatrix = new Matrix(globalDate.getnN(), 1);
        initialTempMatrix.addValuesToAllCells(initialTemp);
        try {
            ProblemCalculator problemCalculator = new ProblemCalculator(globalMatrix, stepTime, simulationTime, initialTempMatrix);
            problemCalculator.solveProblem();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readValues() {
        EntryDataObject entrySet;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src\\main\\java\\com\\company\\entrySet.json");
            entrySet = objectMapper.readValue(file, EntryDataObject.class);
            conductivity = entrySet.getConductivity();
            specificHeat = entrySet.getSpecificHeat();
            density = entrySet.getDensity();
            alfa = entrySet.getAlfa();
            ambientTemp = entrySet.getAmbientTemperature();
            initialTemp = entrySet.getInitialTemperature();
            nH = entrySet.getnH();
            nW = entrySet.getnW();
            gridH = entrySet.getGridH();
            gridW = entrySet.getGridW();
            stepTime = entrySet.getStepTime();
            simulationTime = entrySet.getSimulationTime();
        } catch (IOException e) {
            System.out.println("niepoprawnie wprowadzone dane");
        }
    }
}
