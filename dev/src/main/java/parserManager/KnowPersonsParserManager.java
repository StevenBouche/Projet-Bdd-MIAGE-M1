package parserManager;

import dal.DataAccess;
import models.LinkPerson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class KnowPersonsParserManager {

    DataAccess access = new DataAccess();
    List<String[]>  linksStr;


    public KnowPersonsParserManager() throws IOException, URISyntaxException {
    this.linksStr = access.getDataCSV("person_knows_person_0_0.csv",'|',true);

    }

    public List<LinkPerson> getLinks() {
        return null;
    }
}
