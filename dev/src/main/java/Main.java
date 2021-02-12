import models.Person;
import org.json.simple.parser.ParseException;
import service.PersonManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException, ParseException, InterruptedException {

        PersonManager manager = new PersonManager();
        List<Person> persons = manager.getPersons();

        System.out.println("Finish");

    }

}
