package org.Thesis;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        //args[0] inputPath
        //args[1] Number of cells
        //args[2] nameOfMAp
        //args[3] number of threads

        //Read in file
        String fileName=args[0];
        int numberOfCell= Integer.parseInt(args[1]);
        String outputfilename = args[2]+"-preprocessing-"+numberOfCell+"C";
        int numberOfThreads = Integer.parseInt(args[3]);


//        String fileName="D:/Onedrives/OneDrive - KU Leuven/2022-2023/Masterproof/Testen/osm-inlezen/Aalst.json";
//        int numberOfCell= 16;
//        String outputfilename = "Test-Aalst-preprocessing-"+numberOfCell;
//        int numberOfThreads = 9;
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
        graph.getCellMap().values().removeIf(cell -> cell.getCellList().isEmpty());

        //********************************
        //RANDOM
        //********************************

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
                Map<Long, Double> map = dijkstra.solveDijkstraNormal(cell.getLandmark().getOsmId());
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

        //********************************
        //Central
        //********************************

//        DistanceComparator distanceComparator = new DistanceComparator();
//        for(Cell cell : graph.getCellMap().values()){
//            //osmId , distance
//            for(NodeParser nodeParser:cell.getCellList()){
//                double distance = getDistance(cell,nodeParser);
//                nodeParser.setDistanceToCenter(distance);
//            }
//            cell.getCellList().sort(distanceComparator);
//            long landmark = cell.getCellList().get(cell.getCurrentIndex()).getOsmId();
//            cell.updateIndex();
//            cell.setLandmark(graph.getNodesMap().get(landmark));
//        }
//
//        boolean changed = true;
//        while(changed){
//            changed = false;
//            for(Cell cell:graph.getCellMap().values()){
//                Dijkstra dijkstra = new Dijkstra(graph);
//                Map<Long, Double> map = dijkstra.solveDijkstraNormal(cell.getLandmark().getOsmId());
//                for(double distance:map.values()){
//                    if(distance==Double.MAX_VALUE){
//                        long landmark = cell.getCellList().get(cell.getCurrentIndex()).getOsmId();
//                        cell.updateIndex();
//                        cell.setLandmark(graph.getNodesMap().get(landmark));
//                        System.out.println("Changed Landmark");
//                        changed = true;
//                        break;
//                    }
//                }
//            }
//        }
        System.out.println("Landmarks chosen, start with making factor map");

        List<Long> landmarkIDs = new ArrayList<>();
        for(Cell cell:graph.getCellMap().values()){
            landmarkIDs.add(cell.getLandmark().getOsmId());
        }
        List<Double> timeToCalculate = getTimes();

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
    }

    private static double getDistance(Cell cell, NodeParser nodeParser) {
        double x1 = cell.getCenterLatitude();
        double y1 = cell.getCenterLongitude();
        double x2 = nodeParser.getLatitude();
        double y2 = nodeParser.getLongitude();
        return Math.abs(Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2)));
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