package service;

import models.LinkPerson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

class KnowPersonsManagerTest {

    @BeforeAll
    static void init() {

    }

    @Test
    void KnowPersonsManager() throws IOException, URISyntaxException {
    KnowPersons();
}

        List<String[]>  linksStr;
        DataAccess access = new DataAccess();

        public void KnowPersons() throws IOException, URISyntaxException {
            this.linksStr = access.getDataCSV("person_knows_person_0_0.csv",'|');


            System.out.println(linksStr);

    }
}