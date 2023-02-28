package org.Thesis;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Output {
    private Map<Long,Cell> cellMap = new HashMap<>();


    public Output(Graph graph) {
        this.cellMap = graph.getCellMap();
    }


    public void writeToFile(String filepath) throws FileNotFoundException {

        JSONObject jo = new JSONObject();
        for(Cell cell : cellMap.values()) {
            Map josnMapCell = new HashMap();
            josnMapCell.put("cellId",cell.getCellId());
            josnMapCell.put("LandmarkId",cell.getLandmark().getOsmId());

            //nodes
            JSONArray jsonarrayCellNodes = new JSONArray();
            for(NodeParser node: cell.getCellList()) {
                Map nodeMapJson = new HashMap();
                nodeMapJson.put("osmId", node.getOsmId());
                nodeMapJson.put("latitude", node.getLatitude());
                nodeMapJson.put("longitude", node.getLongitude());
                JSONArray edgesJsonArray = new JSONArray();
                for (EdgeParser edge : node.getOutgoingEdges()) {
                    JSONObject edgeJson = new JSONObject();
                    //edgeJson.put("length", edge.getLength());
                    edgeJson.put("beginNodeOsmId", edge.getBeginNodeOsmId());
                    edgeJson.put("endNodeOsmId", edge.getEndNodeOsmId());
                    //edgeJson.put("edgeType", edge.getEdgeType());
                    edgeJson.put("defaultTravelTime",edge.getDefaultTravelTime());
                    // add any other attributes for the edge
                    edgesJsonArray.add(edgeJson);
                }
                nodeMapJson.put("outgoingEdges", edgesJsonArray);
                jsonarrayCellNodes.add(nodeMapJson);
            }
            josnMapCell.put("cellList",jsonarrayCellNodes);

            //factorMap
            Map jsonFactorMap = new HashMap();
            for(Map.Entry<Double, Map<Long, Double>> entry1 : cell.getFactorMap().entrySet()) {
                Map jsonMap = new HashMap();
                for(Map.Entry<Long,Double> entry2 : entry1.getValue().entrySet()) {
                    jsonMap.put(entry2.getKey(),entry2.getValue());
                }
                jsonFactorMap.put(entry1.getKey(),jsonMap);
            }
            josnMapCell.put("FactorMap",jsonFactorMap);

            jo.put(cell.getCellId(), josnMapCell);
        }

        PrintWriter pw = new PrintWriter(filepath + ".json");
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
}
