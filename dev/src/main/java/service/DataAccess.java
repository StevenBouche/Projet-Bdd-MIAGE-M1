package service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataAccess {

    public List<String[]> getDataCSV(String nameFile, char separator) throws URISyntaxException, IOException {

        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(nameFile).toURI()));

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(parser)
                .build();

        List<String[]> list = new ArrayList<>();
        list = csvReader.readAll();
        reader.close();
        csvReader.close();

        return list;
    }

    public JSONObject getDataXMLToJSON(String nameFile) throws URISyntaxException, IOException {
        String str = Files.readString(Paths.get(ClassLoader.getSystemResource(nameFile).toURI()));
        return XML.toJSONObject(str);
    }

    public JSONArray getDatasJSON(String nameFile) throws URISyntaxException, IOException, ParseException {
        String str = Files.readString(Paths.get(ClassLoader.getSystemResource(nameFile).toURI()));
        return new JSONArray(str);
    }

}
