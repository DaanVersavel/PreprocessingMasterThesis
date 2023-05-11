package org.Thesis;

import java.util.*;

public class TimeDependentDijkstra {

    private Graph graph;

    public TimeDependentDijkstra(Graph graph){
        this.graph = graph;
    }

    //Offset from starting time reason why the value for startnode is 0
    public Map<Long, Double> solveDijkstraTimeDependant(long startNode, double startTime, List<Long> landmarksID){
        PriorityQueue<NodeParser> pq = new PriorityQueue<>(new NodeComparator());
        Map<Long,Double> shortestTimeMap = new HashMap<>();
        Map<Long,NodeParser> nodeMap = new HashMap<>();
        List<Long> landmarksIDs = new ArrayList<>(landmarksID);

        for(NodeParser node : graph.getNodesMap().values()){
            shortestTimeMap.put(node.getOsmId(),Double.MAX_VALUE);
            nodeMap.put(node.getOsmId(),new NodeParser(node));//Copy of node
            nodeMap.get(node.getOsmId()).setCurrenCost(Double.MAX_VALUE);
        }
        pq.addAll(nodeMap.values());

        shortestTimeMap.put(startNode,0.0);
        NodeParser tempNode = nodeMap.get(startNode);
        tempNode.setCurrenCost(0.0);
        pq.remove(nodeMap.get(startNode));
        pq.add(tempNode);

        //dijkstra algorithm
        for (int i = 1; i <= shortestTimeMap.size(); i++) {
            //shortest time search
            NodeParser removedNode= pq.remove();
            if(shortestTimeMap.get(removedNode.getOsmId())==Double.MAX_VALUE){
                System.out.println("ERROR Double met Max Value");
                break;
            }

            //update the adjacent node-time
            double currenTimeAtNode=startTime+shortestTimeMap.get(removedNode.getOsmId());
            for(EdgeParser edge: removedNode.getOutgoingEdges()){
                //when reaching the node
                double travelTimeToNextEdge = shortestTimeMap.get(edge.getBeginNodeOsmId()) +edge.getTravelTime(currenTimeAtNode,graph.getSpeedMatrixMap());
                //If better time update time and read to pq
                if(travelTimeToNextEdge<shortestTimeMap.get(edge.getEndNodeOsmId())){
                    shortestTimeMap.put(edge.getEndNodeOsmId(),travelTimeToNextEdge);
                    NodeParser tempnode=nodeMap.get(edge.getEndNodeOsmId());
                    tempnode.setCurrenCost(travelTimeToNextEdge);
                    if(pq.remove(tempnode)){
                        pq.add(tempnode);
                    }
                }
            }
            landmarksIDs = landmarksIDs.stream().filter(id -> id != removedNode.getOsmId()).toList();
            if(landmarksIDs.isEmpty())break;
        }

        //MAP of cellID and travelTime
        //Divide the travel time by the default travelTime

        Map<Long,Double> outputMap = new HashMap<>();

        Dijkstra dijkstra = new Dijkstra(graph);
        Map<Long,Double> defaulMap = dijkstra.solveDijkstra(startNode,landmarksID);


        for(Long cellID :graph.getCellMap().keySet()){
            Cell cell = graph.getCellMap().get(cellID);
            NodeParser landmark= cell.getLandmark();
            if(startNode!=landmark.getOsmId()){
                double factor=shortestTimeMap.get(landmark.getOsmId())/defaulMap.get(cellID);
                if(Math.abs(1-factor)<0.0000001) factor=1.0;
                outputMap.put(cellID,factor);
            }else{
                outputMap.put(cellID,1.0);
            }
        }
        return outputMap;
    }
}
