package com.company;

import com.company.matrixes.IntegralPoint;

public class Main {

    public static void main(String[] args) {
        double e = Math.sqrt((double) 1 / 3);
        double n = Math.sqrt((double) 1 / 3);
        IntegralPoint[] integralPoints = new IntegralPoint[4];
        integralPoints[0] = new IntegralPoint(-e, -n);
        integralPoints[1] = new IntegralPoint(e, -n);
        integralPoints[2] = new IntegralPoint(e, n);
        integralPoints[3] = new IntegralPoint(-e, n);

        Universal universal = new Universal(integralPoints);
        GlobalDate globalDate = new GlobalDate(1, 1, 2, 3);
        Grid grid = new Grid(globalDate);
        grid.setUniversal(universal);
        grid.calculateMatrixForAllElements(30);
    }
}
