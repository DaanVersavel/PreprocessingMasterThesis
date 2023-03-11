package org.Thesis;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //args[0] inputPath
        //args[1] Number of cells
        //args[2] nameOfMAp

        //Read in file
        String fileName=args[0];
        int numberOfCell= Integer.parseInt(args[1]);
//        String fileName="src/main/java/org/Thesis/Input/Aalst.json";
//        int numberOfCell= 9;

        String outputfilename = args[2]+"-preprocessing-"+numberOfCell;
        //String outputfilename = "-preprocessing-"+numberOfCell;

        //******************************
        //split graph in cell and choose landmarks
        //******************************
        Graph graph = new Graph(fileName);

        //Make cells
        graph.splitGraph(numberOfCell);
        for(Cell cell:graph.getCellMap().values()){
            System.out.println(cell.getCellList().size());
        }
        //choose Landmarks
        Random random = new Random();
        for(Cell cell:graph.getCellMap().values()){
            int index = random.nextInt(cell.getCellList().size());
            cell.setLandmark(cell.getCellList().get(index));
        }

        boolean changed = true;
        while(changed){
            changed = false;
            for(Cell cell:graph.getCellMap().values()){
                Dijkstra dijkstra = new Dijkstra(graph);
                Map<Long, Double> map = dijkstra.solveDijkstra(cell.getLandmark().getOsmId());
                for(double distance:map.values()){
                    if(distance==Double.MAX_VALUE){
                        int index = random.nextInt(cell.getCellList().size());
                        cell.setLandmark(cell.getCellList().get(index));
                        changed = true;
                        break;
                    }
                }
            }
        }
        List<Long> landmarkIDs = new ArrayList<>();
        for(Cell cell:graph.getCellMap().values()){
            landmarkIDs.add(cell.getLandmark().getOsmId());
        }


        //time => Map<CellID,Factor
        //calculate factors
        for(Cell cell:graph.getCellMap().values()){
            System.out.println("CellID: "+cell.getCellId());
            for(double time=0;time<86400;time+=600){
                TimeDependentDijkstra td = new TimeDependentDijkstra(graph);
                Map<Long,Double> tempMap= td.solveDijkstraTimeDependant(cell.getLandmark().getOsmId(),time,landmarkIDs);
                cell.addFactorMap(time,tempMap);
            }
        }


        //uitschrijven naar file
        Output output = new Output(graph);
        try {
            output.writeToFile(outputfilename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Done");



    }
}