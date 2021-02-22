import com.fasterxml.jackson.databind.ObjectMapper;
import models.Feedback;
import models.LinkPerson;
import models.Person;
import models.Vendor;
import org.json.simple.parser.ParseException;
import parserManager.FeedbackParserManager;
import parserManager.KnowPersonsParserManager;
import parserManager.PersonParserManager;
import parserManager.VendorParserManager;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExportData {


    public static void main(String[] args) throws IOException, URISyntaxException, ParseException, InterruptedException {

        String currentDirectory = System.getProperty("user.dir");
        Path path = Paths.get(currentDirectory+"\\ResultParsing");

        Files.deleteIfExists(path);
        Files.createDirectories(path);

        if(Files.exists(path)){

            PersonParserManager personManager = new PersonParserManager();
            List<Person> persons = personManager.getPersons();

            VendorParserManager vendorManager = new VendorParserManager();
            List<Vendor> vendors = vendorManager.getVendors();

            KnowPersonsParserManager linkPerson = new KnowPersonsParserManager();
            List<LinkPerson> links = linkPerson.getLinksPerson();

            FeedbackParserManager f = new FeedbackParserManager();
            List<Feedback> feedback = f.getFeedback();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(path+"\\persons.json"), persons);
            objectMapper.writeValue(new File(path+"\\vendors.json"), vendors);
            objectMapper.writeValue(new File(path+"\\links.json"), links);
            objectMapper.writeValue(new File(path+"\\feedback.json"), feedback);

        }

    }

}
