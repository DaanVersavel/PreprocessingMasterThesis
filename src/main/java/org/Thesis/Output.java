package org.Thesis;

import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Map;

public class Output {

    public void writeToFile(String filepath, Map<Integer, Duration> timeMapLandmark,Map<Integer, Duration> timeMapFactor) throws FileNotFoundException {

        JSONObject jo = new JSONObject();

        JSONObject map1 = new JSONObject();
        for(Map.Entry<Integer, Duration> entry : timeMapLandmark.entrySet()) {
            map1.put(entry.getKey(), entry.getValue());
        }
        JSONObject map2 = new JSONObject();
        for(Map.Entry<Integer, Duration> entry : timeMapFactor.entrySet()) {
            map2.put(entry.getKey(), entry.getValue());
        }


        jo.put("choose landmark",map1);
        jo.put("Calculate Factor",map2);

        PrintWriter pw = new PrintWriter(filepath + ".json");
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
}
