import com.marklogic.client.pojo.PojoRepository;
import dal.MarkLogicUtility;
import models.Feedback;
import models.Person;
import models.Vendor;
import org.json.simple.parser.ParseException;
import service.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class CleanDataBase {

    static ManagersMarkLogic managers = new ManagersMarkLogic();

    public static void main(String[] args) {

        managers.feedbackManager.deleteAll();
        managers.linkManager.deleteAll();
        managers.vendorManager.deleteAll();
        managers.personManager.deleteAll();

        managers.release();

    }


}
