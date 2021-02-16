import com.fasterxml.jackson.databind.ObjectMapper;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.pojo.PojoRepository;
import dal.MarkLogicUtility;
import models.Person;
import models.Vendor;
import org.json.simple.parser.ParseException;
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

        ObjectMapper mapper = new ObjectMapper();

        List<Person> persons = null;
        List<Vendor> vendors = null;

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

        if(persons!=null&&vendors!=null)
            importDataToDatabase(persons,vendors);

    }

    private static void parseAndImport() throws InterruptedException, IOException, URISyntaxException, ParseException {


        System.out.println("Parse data and import into database.");

        PersonParserManager personManager = new PersonParserManager();
        List<Person> persons = personManager.getPersons();

        VendorParserManager vendorManager = new VendorParserManager();
        List<Vendor> vendors = vendorManager.getVendors();

        importDataToDatabase(persons,vendors);

    }

    private static void importDataToDatabase(List<Person> persons, List<Vendor> vendors) {

        System.out.println("Start import all in database.");

        MarkLogicUtility utility = new MarkLogicUtility("localhost", 8003, "Admin", "Admin");

        PojoRepository<Person, String> personRepo = utility.createPojoRepository(Person.class, String.class);
        PojoRepository<Vendor, String> vendorRepo = utility.createPojoRepository(Vendor.class, String.class);

        System.out.println("Delete all repository marklogic.");
        personRepo.deleteAll();
        vendorRepo.deleteAll();

        System.out.println("Start import persons database.");
        Thread importPerson = utility.writeDataInRepository(personRepo,persons);
        System.out.println("Start import vendors database.");
        Thread importVendor = utility.writeDataInRepository(vendorRepo,vendors);

        try {
            importPerson.join();
            importVendor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        utility.releaseConnection();

        System.out.println("Import task finished.");

    }

}
