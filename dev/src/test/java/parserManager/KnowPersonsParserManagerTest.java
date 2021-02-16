package parserManager;

import models.LinkPerson;
import models.Vendor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import dal.DataAccess;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

class KnowPersonsParserManagerTest {

    @BeforeAll
    static void init() {

    }

    @Test
    void KnowPersonsManager() throws IOException, URISyntaxException {
        KnowPersons();
    }

    public void KnowPersons() throws IOException, URISyntaxException{
        KnowPersonsParserManager k = new KnowPersonsParserManager();
        List<LinkPerson> linkPerson = k.getLinksPerson() ;
        System.out.println("TEST");
    }


    /*
    List<String[]> linksStr;
    DataAccess access = new DataAccess();

    public void KnowPersons() throws IOException, URISyntaxException {
        this.linksStr = access.getDataCSV("person_knows_person_0_0.csv",'|',true);
        System.out.println(linksStr);

    } */


}