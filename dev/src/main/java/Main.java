import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.pojo.PojoRepository;
import models.Person;
import org.json.simple.parser.ParseException;
import service.PersonParsingManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException, ParseException, InterruptedException {

        DatabaseClient client = DatabaseClientFactory.newClient("localhost", 8003, new DatabaseClientFactory.DigestAuthContext("Admin", "Admin"));
        PojoRepository myClassRepo = client.newPojoRepository(Person.class, String.class);

        myClassRepo.deleteAll();

        PersonParsingManager manager = new PersonParsingManager();
        List<Person> persons = manager.getPersons();

        for(Person p : persons){
            myClassRepo.write(p);
        }

        client.release();

        System.out.println("Finish");

    }

}
