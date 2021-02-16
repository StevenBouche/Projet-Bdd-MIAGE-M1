package service;

import models.Person;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import parserManager.PersonParserManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

class PersonParsingManagerTest {

    @Test
    void testOne() throws InterruptedException, ParseException, IOException, URISyntaxException {
        PersonParserManager manager = new PersonParserManager();
        List<Person> persons = manager.getPersons();

      /*  DateTimeFormatter format =  DateTimeFormatter.ofPattern("yyyy-MM-dd['T']HH:mm:ss.SSSX");
        long secondUnix = ZonedDateTime.parse("2011-09-15T00:45:16.684+0000",format).toEpochSecond();*/
        System.out.println("test");
    }




}