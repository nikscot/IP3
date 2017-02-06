package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import au.com.bytecode.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        JSONObject json = new JSONObject();

        JSONArray colHeaders = new JSONArray();
        colHeaders.add("Total");
        colHeaders.add("Assignment 1");
        colHeaders.add("Assignment 2");
        colHeaders.add("Exam 1");
        json.put("colHeaders", colHeaders);
        JSONArray rowHeaders = new JSONArray();
        json.put("rowHeaders", rowHeaders);
        JSONArray data = new JSONArray();
        json.put("data", data);

        CSVParser parser = new CSVParser();
        BufferedReader reader = new BufferedReader(new StringReader(csvString));

        try {
            String line = reader.readLine(); 

            while ((line = reader.readLine()) != null ) {
                String[] info = parser.parseLine(line);
                rowHeaders.add(info[0]);
                JSONArray row = new JSONArray();
                row.add(new Long(info[1]));
                row.add(new Long(info[2]));
                row.add(new Long(info[3]));
                row.add(new Long(info[4]));
                data.add(row);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        return json.toString();
    }

    public static String jsonToCsv(String jsonString) {
        JSONObject json = null;

        try {
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String csv = "\"ID\"," + Converter.<String>joinArray((JSONArray) json.get("colHeaders")) + "\n";

        JSONArray headers = (JSONArray) json.get("rowHeaders");
        JSONArray data = (JSONArray) json.get("data");

        for (int i = 0, js = headers.size(); i < js; i++) {
            csv += (
                "\""+ (String) headers.get(i) + "\"," +
                Converter.<Long>joinArray((JSONArray) data.get(i)) + "\n"
            );
        }

        return csv;
    }

    @SuppressWarnings("unchecked")
    private static <T> String joinArray(JSONArray array) {
        String output = "";
        for (int i = 0, js = array.size(); i < js; i++) {
            output += "\"" + ((T) array.get(i)) + "\"";
            if (i < js - 1) {
                output += ",";
            }
        }
        return output;
    }

    public static boolean jsonStringsAreEqual(String s, String t) {        
		JSONParser item = new JSONParser();
		try {
			Object sObj = item.parse(s);
			Object tObj = item.parse(t);
			return sObj.equals(tObj);
		}
		catch(ParseException e) {
			return true;
		}
	}

    
}