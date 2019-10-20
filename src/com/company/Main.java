package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here

        GlobalDate globalDate = new GlobalDate(1,1,6,4);
        Grid grid = new Grid(globalDate);
        System.out.println("Last el = " + grid.getElement(2, 4) + " , Last node = " + grid.getNode(3, 5));
    }
}
