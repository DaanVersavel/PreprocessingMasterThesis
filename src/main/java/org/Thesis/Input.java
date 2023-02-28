package org.Thesis;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Input {
    private Map<Long, NodeParser> nodesMap;


    public Input(String filePath){

        this.nodesMap = new HashMap<>();

        JSONParser parser = new JSONParser();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            JSONObject mapJson = (JSONObject) parser.parse(reader);

            for (Object key : mapJson.keySet()) {
                long id = Long.parseLong((String) key);
                JSONObject nodeJson = (JSONObject) mapJson.get(key);
                NodeParser node = new NodeParser((Long) nodeJson.get("osmId"), (Double) nodeJson.get("latitude"), (Double) nodeJson.get("longitude"));

                //node.setTypes(new HashSet<>((Collection) nodeJson.get("types")));

                List<EdgeParser> edges = new ArrayList<>();
                JSONArray edgesJson = (JSONArray) nodeJson.get("outgoingEdges");
                for (Object edgeObj : edgesJson) {
                    JSONObject edgeJson = (JSONObject) edgeObj;
                    EdgeParser edge = new EdgeParser((long) edgeJson.get("beginNodeOsmId"), (long) edgeJson.get("endNodeOsmId"), (double) edgeJson.get("length"));
                    edge.setEdgeType((String) edgeJson.get("edgeType"));
                    // set any other attributes for the edge
                    edges.add(edge);
                }
                node.setOutgoingEdges(edges);
                nodesMap.put(id, node);
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Long, NodeParser> getNodesMap() {
        return nodesMap;
    }
}
