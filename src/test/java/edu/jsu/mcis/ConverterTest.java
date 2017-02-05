package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConverterTest {
    private String csv;
    private String json;


    private static String readFile(String path) throws IOException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String newline = System.getProperty("line.separator");
        String out = "";

        try {
            while(scanner.hasNextLine()) {
                out += scanner.nextLine() + newline;
            }
            return out;
        } finally {
            scanner.close();
        }
    }

    @Before
    public void setUp() {
        try {
            json = readFile("src/test/resources/grades.json");
            csv = readFile("src/test/resources/grades.csv");
        } catch(IOException e) {}
    }

    @Test
    public void testConvertCSVtoJSON() {
        assertTrue(Converter.jsonStringsAreEqual(Converter.csvToJson(csv), json));
    }

    @Test
    public void testConvertJSONtoCSV() {
        //assertEquals(Converter.jsonToCsv(json), csv);
    }
}