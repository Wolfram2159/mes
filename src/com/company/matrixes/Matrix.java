package com.company.matrixes;

import java.util.ArrayList;
import java.util.List;

public abstract class Matrix {
    protected List<List<Double>> matrix;

    public Matrix() {
        matrix = new ArrayList<>();
    }

    // TODO: 2019-11-03 maybe hashmap instaed of List
    public void addPointAndCalculateDifferentials(MesPoint point){
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
                System.out.print(value + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    protected abstract double firstFunction(MesPoint point);
    protected abstract double secondFunction(MesPoint point);
    protected abstract double thirdFunction(MesPoint point);
    protected abstract double fourthFunction(MesPoint point);
}
