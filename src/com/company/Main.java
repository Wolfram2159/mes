package com.company;

import com.company.matrixes.IntegralPoint;

public class Main {

    private final static int K = 30;
    private final static int c = 700;
    private final static int ro = 7800;
    private final static int alfa = 25;

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
        try {
            grid.calculateMatrixesForAllElements(K, c, ro, alfa);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
