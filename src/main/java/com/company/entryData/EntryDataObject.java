package com.company.entryData;

public class EntryDataObject {
    private double conductivity;
    private double specificHeat;
    private double density;
    private double alfa;
    private double ambientTemperature;
    private double initialTemperature;
    private int nH;
    private int nW;
    private float gridH;
    private float gridW;
    private double stepTime;
    private double simulationTime;

    public EntryDataObject() {
    }

    public EntryDataObject(double conductivity, double specificHeat, double density, double alfa, double ambientTemperature, double initialTemperature, int nH, int nW, float gridH, float gridW, double stepTime, double simulationTime) {
        this.conductivity = conductivity;
        this.specificHeat = specificHeat;
        this.density = density;
        this.alfa = alfa;
        this.ambientTemperature = ambientTemperature;
        this.initialTemperature = initialTemperature;
        this.nH = nH;
        this.nW = nW;
        this.gridH = gridH;
        this.gridW = gridW;
        this.stepTime = stepTime;
        this.simulationTime = simulationTime;
    }

    public double getConductivity() {
        return conductivity;
    }

    public void setConductivity(double conductivity) {
        this.conductivity = conductivity;
    }

    public double getSpecificHeat() {
        return specificHeat;
    }

    public void setSpecificHeat(double specificHeat) {
        this.specificHeat = specificHeat;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public double getAmbientTemperature() {
        return ambientTemperature;
    }

    public void setAmbientTemperature(double ambientTemperature) {
        this.ambientTemperature = ambientTemperature;
    }

    public double getInitialTemperature() {
        return initialTemperature;
    }

    public void setInitialTemperature(double initialTemperature) {
        this.initialTemperature = initialTemperature;
    }

    public int getnH() {
        return nH;
    }

    public void setnH(int nH) {
        this.nH = nH;
    }

    public int getnW() {
        return nW;
    }

    public void setnW(int nW) {
        this.nW = nW;
    }

    public float getGridH() {
        return gridH;
    }

    public void setGridH(float gridH) {
        this.gridH = gridH;
    }

    public float getGridW() {
        return gridW;
    }

    public void setGridW(float gridW) {
        this.gridW = gridW;
    }

    public double getStepTime() {
        return stepTime;
    }

    public void setStepTime(double stepTime) {
        this.stepTime = stepTime;
    }

    public double getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(double simulationTime) {
        this.simulationTime = simulationTime;
    }
}
