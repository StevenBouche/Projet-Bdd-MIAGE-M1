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

}