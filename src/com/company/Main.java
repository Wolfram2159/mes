package com.company;

import com.company.matrixes.MesPoint;
import com.company.matrixes.SimpleMatrix;

public class Main {

    public static void main(String[] args) {
        // write your code here

        GlobalDate globalDate = new GlobalDate(1, 1, 6, 4);
        Grid grid = new Grid(globalDate);
        System.out.println("Last el = " + grid.getElement(2, 4) + " , Last node = " + grid.getNode(3, 5));
        System.out.println("ById = " + grid.getElementById(14));

        double e = Math.sqrt((double) 1 / 3);
        double n = Math.sqrt((double) 1 / 3);
        MesPoint p1 = new MesPoint(-e, -n);
        MesPoint p2 = new MesPoint(e, -n);
        MesPoint p3 = new MesPoint(e, n);
        MesPoint p4 = new MesPoint(-e, n);


        Universal universal = new Universal(p1, p2, p3, p4);
        universal.printEMatrix();
        universal.printNMatrix();
        universal.printValuesMatrix();

        SimpleMatrix a = new SimpleMatrix(2,2);
        SimpleMatrix b = new SimpleMatrix(2,1);
        a.addValueAt(0,0,1);
        a.addValueAt(0,1,2);
        a.addValueAt(1,0,3);
        a.addValueAt(1,1,4);
        b.addValueAt(0,0,4);
        b.addValueAt(1,0,5);
        try {
            SimpleMatrix multiplicate = SimpleMatrix.multiplicate(a, b);
            multiplicate.printMatrix();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
