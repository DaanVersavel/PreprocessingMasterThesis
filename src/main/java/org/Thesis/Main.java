package org.Thesis;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        //Read in file
        String fileName="src/main/java/org/Thesis/Input/Aalst.json";

        //******************************
        //split graph in cell and choose landmarks
        //******************************
        Graph graph = new Graph(fileName);

        //Make cells
        graph.splitGraph(9);
        for(Cell cell:graph.getCellMap().values()){
            System.out.println(cell.getCellList().size());
        }
        //choose Landmarks
        Random random = new Random(54485478445L);
        for(Cell cell:graph.getCellMap().values()){
            int index = random.nextInt(cell.getCellList().size());
            cell.setLandmark(cell.getCellList().get(index));
        }

        //time => Map<CellID,Factor
        //calculate factors
        for(Cell cell:graph.getCellMap().values()){
            System.out.println("CellID: "+cell.getCellId());
            for(double time=0;time<86400;time+=600){
                TimeDependentDijkstra td = new TimeDependentDijkstra(graph);
                Map<Long,Double> tempMap= td.solveDijkstraTimeDependant(cell.getLandmark().getOsmId(),time);
                cell.addFactorMap(time,tempMap);
            }
        }


        //uitschrijven naar file
        Output output = new Output(graph);
        try {
            output.writeToFile("out");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Done");



    }
}