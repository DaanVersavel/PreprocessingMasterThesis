package org.Thesis;

import java.util.HashMap;
import java.util.Map;

public class Graph {

    private Map<Long, NodeParser> nodesMap;
    private Map<Long,Cell> cellMap;




    public Graph(String filename) {
        Input input=new Input(filename);
        this.nodesMap= new HashMap<>();
        this.nodesMap = input.getNodesMap();
        this.cellMap = new HashMap<>();

    }

    public Map<Long, NodeParser> getNodesMap() {
        return nodesMap;
    }

    public void setNodesMap(Map<Long, NodeParser> nodesMap) {
        this.nodesMap = nodesMap;
    }

    public Map<Long, Cell> getCellMap() {
        return cellMap;
    }

    public void setCellMap(Map<Long, Cell> cellMap) {
        this.cellMap = cellMap;
    }

    public void splitGraph(int numberOfCell) {

        //Horizontal Latitude
        //Vertical Longitude
        double minLat = Double.MAX_VALUE;
        double maxLat = Double.MIN_VALUE;
        double minLong = Double.MAX_VALUE;
        double maxLong = Double.MIN_VALUE;
        for(NodeParser node : nodesMap.values()) {
            if(node.getLatitude() < minLat) minLat = node.getLatitude();
            if(node.getLatitude() > maxLat) maxLat = node.getLatitude();
            if(node.getLongitude() < minLong) minLong = node.getLongitude();
            if(node.getLongitude() > maxLong) maxLong = node.getLongitude();
        }
        double horizontalDifferences= maxLat-minLat;
        double verticalDifferences= maxLong-minLong;
        System.out.println();

        int cellsPerEdge= (int) Math.floor(Math.sqrt(numberOfCell));

        //Make the cells
        long itteration= (long) cellsPerEdge *cellsPerEdge;
        for(long i=0; i<itteration; i++) {
            cellMap.put(i, new Cell(i));
        }


        double cellWidthHorizontal=horizontalDifferences/cellsPerEdge;
        double cellWidthVertical=verticalDifferences/cellsPerEdge;


        for(NodeParser node : nodesMap.values()) {
            //horizontal cell
            int horizontalCell= (int) Math.floor((node.getLatitude()-minLat)/cellWidthHorizontal);
            if(horizontalCell==cellsPerEdge) horizontalCell--;

            //vertical cell
            int verticalCell= (int) Math.floor((node.getLongitude()-minLong)/cellWidthVertical);
            if(verticalCell==cellsPerEdge) verticalCell--;

            long cellIndex= ((long) verticalCell *cellsPerEdge)+horizontalCell;

             //add Values
            node.setCelID(cellIndex);
            cellMap.get(cellIndex).addNode(node);
        }
    }

}
