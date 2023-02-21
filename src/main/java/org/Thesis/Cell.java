package org.Thesis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cell {

    private long cellId;
    private Map<Long,NodeParser> cellNodesMap;
    private List<NodeParser> cellList;

    private NodeParser landmark;

    public Cell(long cellId){
        this.cellId = cellId;
        this.cellNodesMap = new HashMap<>();
        this.cellList = new ArrayList<>();
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
}
