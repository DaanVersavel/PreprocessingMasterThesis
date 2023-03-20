package org.Thesis;

import java.util.List;
import java.util.Map;

public class PreprocessingTask implements Runnable {
    private Cell cell;
    private List<Long> landmarkIDs;
    private Graph graph;
    private List<Double> timeToCalculate;
    public PreprocessingTask(Cell cell, List<Long> landmarkIDs, Graph graph, List<Double> timeToCalculate) {
        this.cell = cell;
        this.landmarkIDs = landmarkIDs;
        this.graph = graph;
        this.timeToCalculate = timeToCalculate;
    }

    @Override
    public void run() {
        System.out.println("Started with cell "+ cell.getCellId());
        for(double time : timeToCalculate){
            TimeDependentDijkstra td = new TimeDependentDijkstra(graph);
            Map<Long,Double> tempMap= td.solveDijkstraTimeDependant(cell.getLandmark().getOsmId(),time,landmarkIDs);
            cell.addFactorMap(time,tempMap);
        }
        System.out.println("Cell: "+cell.getCellId()+ " done");

    }
}
