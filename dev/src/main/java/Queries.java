import dal.MarkLogicUtility;
import models.Feedback;
import models.Order;
import models.Person;
import models.Post;
import org.json.simple.parser.ParseException;
import queryModel.QueryOne;
import service.PersonManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class Queries {

    static MarkLogicUtility utility = new MarkLogicUtility("localhost",8003, "Admin", "Admin");
    static PersonManager personManager = new PersonManager(utility);

    public static void main(String[] args) throws IOException, URISyntaxException, ParseException, InterruptedException {

       queryOne();

    }

    private static void queryOne() {

        /*
            Pour un client donné, retrouvez toutes ses données liées y compris son profil, ses commandes, ses factures, ses retours, commentaires,
            et les publications du mois dernier, renvoyer la catégorie dans laquelle il / elle a acheté le plus grand nombre de produits,
            et renvoyer le tag qu'il / elle a engagé les plus grands moments dans les posts.
         */

        String idPerson = "10995116284677";
        QueryOne qone = new QueryOne(idPerson);

        Person p = personManager.getPerson(idPerson);

        qone.setFirstNamePerson(p.getFirstName());
        qone.setLastNamePerson(p.getLastName());
        qone.setGenderPerson(p.getGender());
        qone.setBirthdayPerson(p.getBirthday());
        qone.setLocationPerson(p.getLocation());
        qone.setPlacePerson(p.getPlace());

        qone.setLastMonthPosts(personManager.getPostsLastMonth(p));
        qone.setLastMonthOrders(personManager.getOrdersLastMonth(p));
        qone.setMostTagPost(personManager.getMostTagPosts(p));

        System.out.println("test");

    }


}
