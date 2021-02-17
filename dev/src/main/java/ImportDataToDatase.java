import com.fasterxml.jackson.databind.ObjectMapper;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.pojo.PojoRepository;
import dal.MarkLogicUtility;
import models.LinkPerson;
import models.Person;
import models.Vendor;
import org.json.simple.parser.ParseException;
import parserManager.KnowPersonsParserManager;
import parserManager.PersonParserManager;
import parserManager.VendorParserManager;

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
        Path linkPerson = Paths.get(currentDirectory+"\\ResultParsing\\linkPerson.json");

        ObjectMapper mapper = new ObjectMapper();

        List<Person> persons = null;
        List<Vendor> vendors = null;
        List<LinkPerson> links = null;

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

        if(Files.exists(linkPerson)){
            System.out.println("Load data vendors.");
            String linkPersonStr = Files.readString(linkPerson);
            links = Arrays.asList(mapper.readValue(linkPersonStr, LinkPerson[].class));
        }

        if(persons!=null&&vendors!=null)
            importDataToDatabase(persons,vendors, links);

    }

    private static void parseAndImport() throws InterruptedException, IOException, URISyntaxException, ParseException {


        System.out.println("Parse data and import into database.");

        PersonParserManager personManager = new PersonParserManager();
        List<Person> persons = personManager.getPersons();

        VendorParserManager vendorManager = new VendorParserManager();
        List<Vendor> vendors = vendorManager.getVendors();

        KnowPersonsParserManager k = new KnowPersonsParserManager();
        List<LinkPerson> linkPerson = k.getLinksPerson() ;

        importDataToDatabase(persons,vendors, linkPerson);

    }

    private static void importDataToDatabase(List<Person> persons, List<Vendor> vendors, List<LinkPerson> links) {

        System.out.println("Start import all in database.");

        MarkLogicUtility utility = new MarkLogicUtility("localhost", 8003, "Admin", "Admin");

        PojoRepository<Person, String> personRepo = utility.createPojoRepository(Person.class, String.class);
        PojoRepository<Vendor, String> vendorRepo = utility.createPojoRepository(Vendor.class, String.class);
        PojoRepository<LinkPerson, String> linksRepo = utility.createPojoRepository(LinkPerson.class, String.class);

        System.out.println("Delete all repository marklogic.");
        personRepo.deleteAll();
        vendorRepo.deleteAll();
        linksRepo.deleteAll();

        System.out.println("Start import persons database.");
        Thread importPerson = utility.writeDataInRepository(personRepo,persons);
        System.out.println("Start import vendors database.");
        Thread importVendor = utility.writeDataInRepository(vendorRepo,vendors);
        System.out.println("Start import links person database.");
        Thread importLinks = utility.writeDataInRepository(linksRepo,links);

        try {
            importPerson.join();
            importVendor.join();
            importLinks.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        utility.releaseConnection();

        System.out.println("Import task finished.");

    }

}
