package service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.bson.Document;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DataAccess {

    private Map<Integer,String> parsing = new HashMap<>();
    private Map<Integer,String> parsingAdresse = new HashMap<>();

    public DataAccess(){
        this.initMap();
    }

    private void initMap() {

        parsing.put(0,"_id");
        parsing.put(1,"nom");
        parsing.put(2,"prenom");
        parsing.put(3,"telephone");
        parsing.put(4,"DateNaiss");
        parsing.put(5,"adresse");

        parsingAdresse = new HashMap<>();
        parsingAdresse.put(0, "numero");
        parsingAdresse.put(1, "rue");
        parsingAdresse.put(2, "codePostal");
        parsingAdresse.put(3, "ville");
        parsingAdresse.put(4, "pays");

    }


    public List<String> getDataJSON(){
        return this.getData()
                .stream()
                .map(Document::toJson)
                .collect(Collectors.toList());
    }

    public List<String[]> getDataCSV() throws URISyntaxException, IOException {

        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("clients.csv").toURI()));

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
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

    public Document parsingCSV(String[] strings) {

        Document d = new Document();

        for (Integer i : parsing.keySet()) {

            if(parsing.get(i).equals("adresse")){

                Document addr = new Document();
                String[] split = strings[i].split(";");

                for (Integer iA : parsingAdresse.keySet())
                    addr.append(parsingAdresse.get(iA),split[iA]);

                d.append(parsing.get(i),addr);

            } else if(parsing.get(i).equals("prenom")){

                List<String> split = Arrays.asList(strings[i].split(";"));
                d.append(parsing.get(i),split);

            }
            else {
                d.append(parsing.get(i),strings[i]);
            }

        }

        return d;

    }

    public List<Document> getData(){
        return Arrays.asList(
                new Document("_id", 1)
                        .append("nom",  "Martin")
                        .append("prenom", Arrays.asList("Aaron", "Frida"))
                        .append("telephone", "673212284")
                        .append("DateNaiss", "01/01/1980")
                        .append("adresse",  new Document("numero",  11)
                                .append("rue", "Allee Cavendish")
                                .append("codePostal",  "06000")
                                .append("ville",  "Nice")
                                .append("pays",  "France")
                        ),

                new Document("_id",  2)
                        .append("nom",  "Bernard")
                        .append("prenom",  Arrays.asList("Abel"))
                        .append("telephone", "673212285")
                        .append("DateNaiss", "05/05/1984")
                        .append("adresse",
                                new Document("numero",  12)
                                        .append("rue", "Allee de la Chapelle Saint-Pierre")
                                        .append("codePostal",  "06000")
                                        .append("ville",  "Nice")
                                        .append("pays",  "France")
                        ),

                new Document("_id",  3)
                        .append("nom",  "Dubois")
                        .append("prenom", Arrays.asList("Abella", "Mehdi"))
                        .append("telephone", "673212286")
                        .append("DateNaiss", "02/02/1990")
                        .append("adresse", new Document("numero",  13)
                                .append("rue", "Rue la Fontaine aux Oiseaux")
                                .append("codePostal",  "06000")
                                .append("ville",  "Nice")
                                .append("pays",  "France")
                        ),

                new Document("_id",  4)
                        .append("nom",  "Thomas")
                        .append("prenom", Arrays.asList("Abelard"))
                        .append("telephone", "673212287")
                        .append("DateNaiss", "01/06/1987")
                        .append("adresse", new Document("numero",  14)
                                .append("rue", "Rue La Palmeraie")
                                .append("codePostal",  "France")
                                .append("ville",  "Nice")
                                .append("pays",  "France")
                        ),

                new Document("_id",  5)
                        .append("nom",  "Walter")
                        .append("prenom", Arrays.asList("Robert"))
                        .append("telephone", "673212288")
                        .append("DateNaiss", "01/08/1983")
                        .append("adresse",  new Document("numero",  15)
                                .append("rue", "Rue de la Resistance")
                                .append("codePostal",  "10001")
                                .append("ville",  "New-york")
                                .append("pays",  "USA")
                        ),

                new Document("_id",  6)
                        .append("nom",  "Richard")
                        .append("prenom", Arrays.asList("Maria", "Abondance"))
                        .append("telephone", "673212289")
                        .append("DateNaiss", "12/01/1980")
                        .append("adresse", new Document("numero",  16)
                                .append("rue", "Allee des Citronniers")
                                .append("codePostal",  "75001")
                                .append("ville",  "Paris")
                                .append("pays",  "France")
                        ),

                new Document("_id",  7)
                        .append("nom",  "Petit")
                        .append("prenom", Arrays.asList("Abraham", "Leonard"))
                        .append("telephone", "673212290")
                        .append("DateNaiss", "01/08/1980")
                        .append("adresse",  new Document("numero",  17)
                                .append("rue", "Allee des Faunes")
                                .append("codePostal",  "69001")
                                .append("ville",  "Lyon")
                                .append("pays",  "France")
                        ),

                new Document("_id",  8)
                        .append("nom",  "Durand")
                        .append("prenom", Arrays.asList("Mari", "Achille"))
                        .append("telephone", "673212291")
                        .append("DateNaiss", "01/09/1989")
                        .append("adresse",  new Document("numero",  18)
                                .append("rue", "Rue des Isnards")
                                .append("codePostal",  "75001")
                                .append("ville",  "Paris")
                                .append("pays",  "France")
                        ),

                new Document("_id",  9)
                        .append("nom",  "Leroy")
                        .append("prenom", Arrays.asList("Ada", "Mousse"))
                        .append("telephone", "673212292")
                        .append("DateNaiss", "28/07/1985")
                        .append("adresse",  new Document("numero",  19)
                                .append("rue", "Rue des Lucioles")
                                .append("codePostal",  "13001")
                                .append("ville",  "Marseille")
                                .append("pays",  "France")
                        ),

                new Document("_id",  10)
                        .append("nom", "Moreau")
                        .append("prenom", Arrays.asList("Adam"))
                        .append("telephone", "673212293")
                        .append("DateNaiss", "03/02/1990")
                        .append("adresse", new Document("numero", 20)
                                .append("rue", "Allee des Palmiers")
                                .append("codePostal",  "31000")
                                .append("ville",  "Toulouse")
                                .append("pays", "France")
                        )
        );

    }




}
