package com.company;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private Node[] nodeList;
    private Element[] elementList;
    private static final int ELEMENT_NODES_COUNT = 4;

    private GlobalDate globalDate;

    public Grid(GlobalDate globalDate) {
        this.globalDate = globalDate;
        nodeList = new Node[globalDate.getnN() + 1];
        elementList = new Element[globalDate.getnE() + 1];
        buildGrid();
    }

    private void buildGrid() {
        buildNodes();
        buildElements();
    }

    private void buildNodes() {
        int id = 0;
        for (int i = 0; i < globalDate.getnW(); i++) {
            for (int j = 0; j < globalDate.getnH(); j++) {
                Node node = new Node(id, i, j, 0, checkB(i, j));
                nodeList[id] = node;
                id++;
            }
        }
    }

    private boolean checkB(int x, int y) {
        return (x == 0 || x == (globalDate.getnW() - 1) || y == 0 || y == (globalDate.getnH() - 1));
    }

    private void buildElements() {
        int id = 0;
        for (int i = 0; i < globalDate.getnW() - 1; i++) {
            for (int j = 0; j < globalDate.getnH() - 1; j++) {
                Element element = new Element(id, i, j, determineNodesForElement(i, j));
                elementList[id] = element;
                id++;
            }
        }
    }

    private Node[] determineNodesForElement(int x, int y) {
        Node[] nodes = new Node[ELEMENT_NODES_COUNT];
        nodes[0] = getNode(x, y);
        nodes[1] = getNode(x + 1, y);
        nodes[2] = getNode(x + 1, y + 1);
        nodes[3] = getNode(x, y + 1);
        return nodes;
    }

    public Node getNode(int x, int y) {
        int id = globalDate.getnH() * x + y;
        return nodeList[id];
    }

    public Element getElement(int x, int y) {
        int id = (globalDate.getnH() - 1) * x + y;
        return elementList[id];
    }
}
