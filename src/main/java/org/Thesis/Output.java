package org.Thesis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class Output {
    private Map<Long,Cell> cellMap;

    public Output(Map<Long,Cell> cellMap) {
        this.cellMap = cellMap;
    }

    public void writeToFile(String path){
        try (Writer writer = new FileWriter(path+".json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(cellMap, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
