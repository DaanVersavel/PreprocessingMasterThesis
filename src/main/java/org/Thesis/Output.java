package org.Thesis;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Output {

    public void writeToFile(String filepath, Map<Integer, Duration> timeMap) throws FileNotFoundException {

        JSONObject jo = new JSONObject();
        for(Map.Entry<Integer, Duration> entry : timeMap.entrySet()) {
            jo.put(entry.getKey(), entry.getValue());
        }

        PrintWriter pw = new PrintWriter(filepath + ".json");
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
}
