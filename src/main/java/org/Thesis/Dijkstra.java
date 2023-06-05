package org.Thesis;

import java.util.*;

public class Dijkstra {

    private Graph graph;

    public Dijkstra(Graph graph) {
       this.graph=graph;
    }

    //return map of shortest time to landmarks
    public Map<Long, Double> solveDijkstra(Long startNode, List<Long> landmarksID){
        PriorityQueue<NodeParser> pq = new PriorityQueue<>(new NodeComparator());
        Map<Long,Double> shortestTimeMap = new HashMap<>();
        Map<Long,NodeParser> nodeMap = new HashMap<>();
        List<Long> landmarksIDs = new ArrayList<>(landmarksID);

        for(NodeParser node : graph.getNodesMap().values()){
            shortestTimeMap.put(node.getOsmId(),Double.MAX_VALUE);
            nodeMap.put(node.getOsmId(),new NodeParser(node));         //Copy of node
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
                System.out.println("Node met current cost max");
                break;
            }

            //update the adjacent node-time
            for(EdgeParser edge: removedNode.getOutgoingEdges()){
                //when reaching the node
                double travelTimeAtNode = shortestTimeMap.get(edge.getBeginNodeOsmId())+ edge.getDefaultTravelTime();
                //If better time update time and read to pq
                if(travelTimeAtNode<shortestTimeMap.get(edge.getEndNodeOsmId())){
                    shortestTimeMap.put(edge.getEndNodeOsmId(),travelTimeAtNode);
                    NodeParser tempnode=nodeMap.get(edge.getEndNodeOsmId());
                    tempnode.setCurrenCost(travelTimeAtNode);
                    if(pq.remove(tempnode)){
                        pq.add(tempnode);
                    }
                }
            }
            landmarksIDs = landmarksIDs.stream().filter(id -> id != removedNode.getOsmId()).toList();
            if(landmarksIDs.isEmpty())break;
        }

        Map<Long,Double> outputMap = new HashMap<>();
        for(Long cellID :graph.getCellMap().keySet()){
            Cell cell = graph.getCellMap().get(cellID);
            NodeParser landmark= cell.getLandmark();
            outputMap.put(cellID,shortestTimeMap.get(landmark.getOsmId()));
        }
        return outputMap;
    }

    public Map<Long, Double> solveDijkstraNormal(long startNode) {
        PriorityQueue<NodeParser> pq = new PriorityQueue<>(new NodeComparator());
        Map<Long,Double> shortestTimeMap = new HashMap<>();
        Map<Long,NodeParser> nodeMap = new HashMap<>();

        for(NodeParser node : graph.getNodesMap().values()){
            shortestTimeMap.put(node.getOsmId(),Double.MAX_VALUE);
            nodeMap.put(node.getOsmId(),new NodeParser(node));         //Copy of node
            nodeMap.get(node.getOsmId()).setCurrenCost(Double.MAX_VALUE);
        }
        pq.addAll(nodeMap.values());

        shortestTimeMap.put(startNode,0.0);
        NodeParser tempNode = nodeMap.get(startNode);
        tempNode.setCurrenCost(0.0);
        pq.remove(nodeMap.get(startNode));
        pq.add(tempNode);

        //dijkstra algorithm
        for (int i = 1; i <= nodeMap.size(); i++) {
            //shortest time search
            NodeParser removedNode= pq.remove();
            if(shortestTimeMap.get(removedNode.getOsmId())==Double.MAX_VALUE){
                System.out.println("Node met current cost max");
                break;
            }

            //update the adjacent node-time
            for(EdgeParser edge: removedNode.getOutgoingEdges()){
                //when reaching the node
                double travelTimeAtNode = shortestTimeMap.get(edge.getBeginNodeOsmId())+ edge.getDefaultTravelTime();
                //If better time update time and readd to pq
                if(travelTimeAtNode<shortestTimeMap.get(edge.getEndNodeOsmId())){
                    shortestTimeMap.put(edge.getEndNodeOsmId(),travelTimeAtNode);
                    NodeParser tempnode=nodeMap.get(edge.getEndNodeOsmId());
                    tempnode.setCurrenCost(travelTimeAtNode);
                    if(pq.remove(tempnode)){
                        pq.add(tempnode);
                    }
                }
            }
        }

        Map<Long,Double> outputMap = new HashMap<>();
        for(Long cellID :graph.getCellMap().keySet()){
            Cell cell = graph.getCellMap().get(cellID);
            NodeParser landmark= cell.getLandmark();
            outputMap.put(cellID,shortestTimeMap.get(landmark.getOsmId()));
        }
        return outputMap;
    }
}
