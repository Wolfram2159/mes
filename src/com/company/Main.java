package com.company;

public class Main {

    private final static int K = 30;
    private final static int c = 700;
    private final static int ro = 7800;
    private final static int alfa = 25;

    public static void main(String[] args) {
        Universal universal = new Universal();
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
