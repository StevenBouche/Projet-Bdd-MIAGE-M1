import dal.MarkLogicUtility;
import org.json.simple.parser.ParseException;
import queryModel.QueryOne;
import service.PersonManager;

import java.io.IOException;
import java.net.URISyntaxException;


public class Queries {

    MarkLogicUtility utility = new MarkLogicUtility("localhost",8003, "Admin", "Admin");
    PersonManager personManager = new PersonManager(utility);

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

       // personManager.



    }


}
