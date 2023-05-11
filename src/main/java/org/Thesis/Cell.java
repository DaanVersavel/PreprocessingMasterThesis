package org.Thesis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cell {

    private long cellId;
    private transient double centerLatitude;
    private transient double centerLongitude;
    private transient int currentIndex=0;
    private Map<Long,NodeParser> cellNodesMap;
    private List<NodeParser> cellList;
    private NodeParser landmark;
    //map with in every entry a map of cellid and factor
    private Map<Double,Map<Long,Double>> factorMap;

    public Cell(long cellId){
        this.cellId = cellId;
        this.cellNodesMap = new HashMap<>();
        this.cellList = new ArrayList<>();
        this.factorMap= new HashMap<>();
    }

    public void addNode(NodeParser node) {
        this.cellNodesMap.put(node.getOsmId(), node);
        this.cellList.add(node);
    }

    public List<NodeParser> getCellList() {
        return cellList;
    }

    public void setCellList(List<NodeParser> cellList) {
        this.cellList = cellList;
    }

    public long getCellId() {
        return cellId;
    }

    public void setCellId(long cellId) {
        this.cellId = cellId;
    }

    public Map<Long, NodeParser> getCellNodesMap() {
        return cellNodesMap;
    }

    public void setCellNodesMap(Map<Long, NodeParser> cellNodes) {
        this.cellNodesMap = cellNodes;
    }

    public NodeParser getLandmark() {
        return landmark;
    }

    public void setLandmark(NodeParser landmark) {
        this.landmark = landmark;
    }

    public Map<Double, Map<Long, Double>> getFactorMap() {
        return factorMap;
    }

    public void setFactorMap(Map<Double, Map<Long, Double>> factorMap) {
        this.factorMap = factorMap;
    }

    public void addFactorMap(double time, Map<Long, Double> factorMapForTime) {
        this.factorMap.put(time, factorMapForTime);
    }
    public double getCenterLatitude() {
        return centerLatitude;
    }
    public void setCenterLatitude(double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }
    public double getCenterLongitude() {
        return centerLongitude;
    }
    public void setCenterLongitude(double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }
    public void updateIndex(){
        this.currentIndex++;
    }
    public int getCurrentIndex() {
        return currentIndex;
    }
}
