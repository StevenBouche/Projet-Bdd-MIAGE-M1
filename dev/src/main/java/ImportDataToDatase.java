import com.fasterxml.jackson.databind.ObjectMapper;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.pojo.PojoRepository;
import dal.MarkLogicUtility;
import models.Feedback;
import models.LinkPerson;
import models.Person;
import models.Vendor;
import org.json.simple.parser.ParseException;
import parserManager.FeedbackParserManager;
import parserManager.KnowPersonsParserManager;
import parserManager.PersonParserManager;
import parserManager.VendorParserManager;
import service.ManagersMarkLogic;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class ImportDataToDatase {

    static boolean importDirectly = false;

    public static void main(String[] args) throws IOException, URISyntaxException, ParseException, InterruptedException {

        if(importDirectly)
            loadAndImport();
        else parseAndImport();

    }

    private static void loadAndImport() throws IOException {

        System.out.println("Load data and import into database.");

        String currentDirectory = System.getProperty("user.dir");

        Path pathPersons = Paths.get(currentDirectory+"\\ResultParsing\\persons.json");
        Path pathVendors = Paths.get(currentDirectory+"\\ResultParsing\\vendors.json");
        Path pathLinks = Paths.get(currentDirectory+"\\ResultParsing\\links.json");
        Path pathFeedback = Paths.get(currentDirectory+"\\ResultParsing\\feedback.json");

        ObjectMapper mapper = new ObjectMapper();

        List<Person> persons = null;
        List<Vendor> vendors = null;
        List<LinkPerson> links = null;
        List<Feedback> feedbacks = null;

        if(Files.exists(pathPersons)){
            System.out.println("Load data persons.");
            String personsStr = Files.readString(pathPersons);
            persons = Arrays.asList(mapper.readValue(personsStr, Person[].class));
        }

        if(Files.exists(pathVendors)){
            System.out.println("Load data vendors.");
            String vendorsStr = Files.readString(pathVendors);
            vendors = Arrays.asList(mapper.readValue(vendorsStr, Vendor[].class));
        }

        if(Files.exists(pathLinks)){
            System.out.println("Load data links.");
            String linkPersonStr = Files.readString(pathLinks);
            links = Arrays.asList(mapper.readValue(linkPersonStr, LinkPerson[].class));
        }

        if(Files.exists(pathFeedback)){
            System.out.println("Load data feedback.");
            String linkPersonStr = Files.readString(pathFeedback);
            feedbacks = Arrays.asList(mapper.readValue(linkPersonStr, Feedback[].class));
        }

        if(persons!=null&&vendors!=null)
            importDataToDatabase(persons,vendors, links, feedbacks);

    }

    private static void parseAndImport() throws InterruptedException, IOException, URISyntaxException, ParseException {


        System.out.println("Parse data and import into database.");

        PersonParserManager personManager = new PersonParserManager();
        List<Person> persons = personManager.getPersons();

        VendorParserManager vendorManager = new VendorParserManager();
        List<Vendor> vendors = vendorManager.getVendors();

        KnowPersonsParserManager k = new KnowPersonsParserManager();
        List<LinkPerson> linkPerson = k.getLinksPerson() ;

        FeedbackParserManager f = new FeedbackParserManager();
        List<Feedback> feedback = f.getFeedback();

        importDataToDatabase(persons,vendors, linkPerson, feedback);

    }

    private static void importDataToDatabase(List<Person> persons, List<Vendor> vendors, List<LinkPerson> links, List<Feedback> feedbacks) {

        System.out.println("Start import all in database.");

        ManagersMarkLogic managers = new ManagersMarkLogic();
        managers.deleteAll();

        System.out.println("Start import persons database.");
        Thread importPerson = managers.personManager.utility.writeDataInRepository(managers.personManager.repository,persons);
        System.out.println("Start import vendors database.");
        Thread importVendor = managers.vendorManager.utility.writeDataInRepository(managers.vendorManager.repository,vendors);
        System.out.println("Start import links person database.");
        Thread importLinks = managers.linkManager.utility.writeDataInRepository(managers.linkManager.repository,links);
        System.out.println("Start import links person database.");
        Thread importFeedback = managers.feedbackManager.utility.writeDataInRepository(managers.feedbackManager.repository,feedbacks);

        try {
            importPerson.join();
            importVendor.join();
            importLinks.join();
            importFeedback.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        managers.release();

        System.out.println("Import task finished.");

    }

}
