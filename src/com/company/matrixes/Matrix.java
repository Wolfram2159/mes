package com.company.matrixes;

import java.util.ArrayList;
import java.util.List;

public abstract class Matrix {
    protected List<List<Double>> matrix;

    public Matrix() {
        matrix = new ArrayList<>();
    }

    public void addPointAndCalculateDifferentials(IntegralPoint point){
        List<Double> valuesList = new ArrayList<>();
        valuesList.add(firstFunction(point));
        valuesList.add(secondFunction(point));
        valuesList.add(thirdFunction(point));
        valuesList.add(fourthFunction(point));
        matrix.add(valuesList);
    }

    public void printMatrix(){
        System.out.println("Values for " + getClass().getSimpleName());
        for (List<Double> doubleList : matrix) {
            for (Double value : doubleList) {
                System.out.format("%.3f \t", value);
            }
            System.out.println();
        }
        System.out.println();
    }

    protected abstract double firstFunction(IntegralPoint point);
    protected abstract double secondFunction(IntegralPoint point);
    protected abstract double thirdFunction(IntegralPoint point);
    protected abstract double fourthFunction(IntegralPoint point);

    public List<Double> getMatrixForPoint(int index) {
        return matrix.get(index);
    }
}
