package service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class KnowPersonsManager {

    DataAccess access = new DataAccess();
    List<String[]>  linksStr;


    public KnowPersonsManager() throws IOException, URISyntaxException {
    this.linksStr = access.getDataCSV("person_knows_person_0_0.csv",'|');

    }
}
