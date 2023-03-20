package org.Thesis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //args[0] inputPath
        //args[1] Number of cells
        //args[2] nameOfMAp
        //args[3] number of threads

        //Read in file
        String fileName=args[0];
        int numberOfCell= Integer.parseInt(args[1]);
        String outputfilename = args[2]+"-preprocessing-"+numberOfCell;
        int numberOfThreads = Integer.parseInt(args[3]);


//        String fileName="src/main/java/org/Thesis/Input/aalst.json";
//        int numberOfCell= 64;
//        String outputfilename = "TestAAlst-preprocessing-"+numberOfCell;
//        int numberOfThreads = 3;
        //******************************
        //split graph in cell and choose landmarks
        //******************************
        Graph graph = new Graph(fileName);

        //Make cells
        graph.splitGraph(numberOfCell);
        for(Cell cell:graph.getCellMap().values()){
            System.out.println("Number of nodes in cell: "+cell.getCellId()+" "+ cell.getCellList().size());
        }


        //remove empty cells
        graph.getCellMap().values().removeIf(cell -> cell.getCellList().size() == 0);

        //choose Landmarks
        Random random = new Random();

        for(Cell cell : graph.getCellMap().values()){
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
        System.out.println("Landmarks chosen, start with making factor map");

        List<Long> landmarkIDs = new ArrayList<>();
        for(Cell cell:graph.getCellMap().values()){
            landmarkIDs.add(cell.getLandmark().getOsmId());
        }
        List<Double> timeToCalculate = getTimes();


//        //time => Map<CellID,Factor
//        //calculate factors
//        for(Cell cell:graph.getCellMap().values()){
//            System.out.println("CellID: "+cell.getCellId());
//            for(double time=0;time<86400;time+=600){
//                TimeDependentDijkstra td = new TimeDependentDijkstra(graph);
//                Map<Long,Double> tempMap= td.solveDijkstraTimeDependant(cell.getLandmark().getOsmId(),time,landmarkIDs);
//                cell.addFactorMap(time,tempMap);
//            }
//        }

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for(Cell cell:graph.getCellMap().values()){
            executor.execute(new PreprocessingTask(cell,landmarkIDs,graph,timeToCalculate));
        }
        executor.shutdown();

        while (!executor.isTerminated()) {
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                // Handle interruption if necessary
            }
        }

        Output2 output = new Output2(graph.getCellMap());
        System.out.println("Start writing to file");
        output.writeToFile(outputfilename);
        System.out.println("Done");

//        //uitschrijven naar file
//        try {
//            Output output = new Output(graph);
//            output.writeToFile(outputfilename);
//            System.out.println("Done writing to file");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }


    }

    private static List<Double> getTimes() {
        List<Double> times = new ArrayList<>();
        times.add(0.0);
        times.add(4*3600.0);
        times.add(7*3600.0);
        times.add(8*3600.0);
        times.add(9*3600.0);
        times.add(12*3600.0);
        times.add(13*3600.0);
        times.add(16*3600.0);
        times.add(17*3600.0);
        times.add(18*3600.0);
        times.add(19*3600.0);
        times.add(21*3600.0);
        times.add(22*3600.0);
        return times;
    }
}