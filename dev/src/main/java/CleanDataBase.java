import com.marklogic.client.pojo.PojoRepository;
import dal.MarkLogicUtility;
import models.Person;
import models.Vendor;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

public class CleanDataBase {

    public static void main(String[] args) throws IOException, URISyntaxException, ParseException, InterruptedException {

        MarkLogicUtility utility = new MarkLogicUtility("localhost", 8003, "Admin", "Admin");

        PojoRepository<Person, String> personRepo = utility.createPojoRepository(Person.class, String.class);
        PojoRepository<Vendor, String> vendorRepo = utility.createPojoRepository(Vendor.class, String.class);

        System.out.println("Delete all repository marklogic.");
        personRepo.deleteAll();
        vendorRepo.deleteAll();

        utility.releaseConnection();

    }


}
