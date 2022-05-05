package com.lntptdds.core.integration.adapters.gprs.parser.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class JsonLoader {


    public static HashMap<String,String> jsonMap() {

        try {
//            Reader getLocalJsonFile = new InputStreamReader(JsonLoader.class.getResourceAsStream("/test2.json"));
            JsonReader getLocalJsonFile = new JsonReader(new FileReader("config/test2.json"));
            System.out.println("json file loaded");
            return  new Gson().fromJson(getLocalJsonFile, HashMap.class);
        } catch (Exception e) {
            System.out.println("Failed to read file");
            return new HashMap<>();
        }
    }

    public static List<String> jsonMapUnits() {

        try {

//            Reader getLocalJsonFile = new InputStreamReader(JsonLoader.class.getResourceAsStream("/unitids.json"));

            JsonReader getLocalJsonFile = new JsonReader(new FileReader("config/unitids.json"));


            System.out.println("Unit Id json file loaded");
              HashMap<String,ArrayList<String>> readVal = new Gson().fromJson(getLocalJsonFile, HashMap.class);
              List<String> retVal = new ArrayList<>();
              if(readVal.containsKey("unitIds"))
              {

                  log.info("Unit Id config file loaded");
                  log.info(readVal.toString());
                  log.info(readVal.get("unitIds").toString());
                  retVal = readVal.get("unitIds");

              }
              return  retVal;
        }
        catch (Exception e) {
            System.out.println("Failed to read file");
            return new ArrayList<>();
        }
    }
}
