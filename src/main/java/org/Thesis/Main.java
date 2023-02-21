package org.Thesis;

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
        graph.splitGraph(9);
        for(Cell cell:graph.getCellMap().values()){
            System.out.println(cell.getCellList().size());
        }

        Random random = new Random(54485478445L);
        for(Cell cell:graph.getCellMap().values()){
            int index = random.nextInt(cell.getCellList().size());
            cell.setLandmark(cell.getCellList().get(index));
        }

        System.out.println();



    }
}