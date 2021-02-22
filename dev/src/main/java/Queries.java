import dal.MarkLogicUtility;
import models.*;
import org.json.simple.parser.ParseException;
import queryModel.QueryOne;
import queryModel.QueryThree;
import queryModel.QueryTwo;
import service.FeedbackManager;
import service.LinkPersonManager;
import service.PersonManager;
import service.VendorManager;
import viewModel.NodePerson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Queries {

    static MarkLogicUtility utility = new MarkLogicUtility("localhost",8003, "Admin", "Admin");
    static PersonManager personManager = new PersonManager(utility);
    static VendorManager vendorManager = new VendorManager(utility);
    static LinkPersonManager linkManager = new LinkPersonManager(utility);
    static FeedbackManager feedbackManager = new FeedbackManager(utility);

    public static void main(String[] args) throws IOException, URISyntaxException, ParseException, InterruptedException {

        queryOne();
        queryTwo();
        queryThree();

       finishqueries();
    }

    private static void finishqueries() {
        personManager.release();
        vendorManager.release();
        linkManager.release();
    }

    private static void queryOne() {

        /*

         */

        System.out.println("QUERY ONE \n");

        System.out.println("Pour un client donné, retrouvez toutes ses données liées y compris son profil, ses commandes, ses factures, ses retours, commentaires,\n" +
                "et les publications du mois dernier, renvoyer la catégorie dans laquelle il / elle a acheté le plus grand nombre de produits,\n" +
                "et renvoyer le tag qu'il / elle a engagé les plus grands moments dans les posts.\n");

        String idPerson = "8796093022539";
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
        qone.setMostBuyProductBrand(personManager.getMostBrandProduct(p));

        System.out.println(qone.toString());

    }


    private static void queryTwo(){

        /*
            For a given product during a given period, find the people who commented or posted on it, and had bought it.
        */

        System.out.println("QUERY TWO \n");

        System.out.println("Pour un produit donné pendant une période donnée, trouvez les personnes qui ont commenté ou posté dessus, et l'ont acheté.\n");

        QueryTwo q2 = new QueryTwo();

        q2.asinProduct = "B004H4VSI8";
        q2.startStr = "2010-01-01T00:00:00.000+0000";
        q2.endStr = "2030-01-01T00:00:00.000+0000";

        DateTimeFormatter format =  DateTimeFormatter.ofPattern("yyyy-MM-dd['T']HH:mm:ss.SSSX");
        q2.startPeriod = ZonedDateTime.parse(q2.startStr,format).toEpochSecond();
        q2.endPeriod = ZonedDateTime.parse(q2.endStr,format).toEpochSecond();

        Product p = vendorManager.getProductOfVendor( q2.asinProduct);
        List<Person> persons = personManager.getPersonInRelationWithProduct(p);

        q2.personWhoHaverPostAndBuyProduct = personManager.getPersonHaveBuyAndPostProduct(q2.startPeriod, q2.endPeriod,  persons, p);

        System.out.println(q2);

    }

    private static void queryThree() throws InterruptedException {
        /*
        For a given product during a given period, find people who have undertaken
activities related to it, e.g., posts, comments, and review, and return sentences from these texts
that contain negative sentiments


         */

        System.out.println("QUERY Three \n");

        System.out.println("Pour un produit donné au cours d'une période donnée, trouvez des personnes qui ont entrepris des\n" +
                "activités liées à celui-ci, par exemple des publications, des commentaires et des critiques,\n" +
                "et renvoyez des phrases de ces textes qui contiennent des sentiments négatifs.\n");

        QueryThree q3 = new QueryThree();

        q3.asinProduct = "B005FUKW6M";
        q3.startStr = "2010-01-01T00:00:00.000+0000";
        q3.endStr = "2021-01-01T00:00:00.000+0000";

        DateTimeFormatter format =  DateTimeFormatter.ofPattern("yyyy-MM-dd['T']HH:mm:ss.SSSX");
        q3.startPeriod = ZonedDateTime.parse(q3.startStr,format).toEpochSecond();
        q3.endPeriod = ZonedDateTime.parse(q3.endStr,format).toEpochSecond();

        Product p = vendorManager.getProductOfVendor( q3.asinProduct);
        List<Person> persons = personManager.getPersonInRelationWithProduct(p);

        List<Person> p2 = personManager.getPersonHaveBuyAndPostProduct(q3.startPeriod, q3.endPeriod,  persons, p);

        q3.map = feedbackManager.getFeedbackPersonJoinProduct(p2,p);

        System.out.println(q3);

    }

    private static void queryFour() throws InterruptedException {

        /*
        Find the top-2 persons who spend the highest amount of money in orders. Then for
each person, traverse her knows-graph with 3-hop to find the friends, and finally return the
common friends of these two persons.

Trouvez les 2 personnes qui dépensent le plus d'argent en commandes. Ensuite, pour chaque personne, parcourez son know-graphe avec 3-hop pour trouver les amis, et enfin renvoyer les amis communs de ces deux personnes.
         */

        System.out.println("QUERY Four \n");

        System.out.println("Trouvez les 2 personnes qui dépensent le plus d'argent en commandes. " +
                "Ensuite, pour chaque personne, parcourez son know-graphe avec 3-hop pour trouver les amis, " +
                "et enfin renvoyer les amis communs de ces deux personnes.\n");

        Map<String,Double> element = personManager.getAverageValueBuyAllPersons();

        Map<String, NodePerson> nodes = linkManager.getGraph();

        List<String> personId = new ArrayList<>(nodes.keySet());

        String idFirst = personId.get(0);
        String idSecond = personId.get(1);

        //int i, String currentPerson, int currentI, List<String> result, Map<String, NodePerson> nodes
        List<String> friendsHop1 = new ArrayList<>();
        List<String> friendsHop2 = new ArrayList<>();
        linkManager.getFriends(3,idFirst,0,friendsHop1,nodes);
        linkManager.getFriends(3,idSecond,0,friendsHop2,nodes);

        friendsHop1 = friendsHop1.stream().distinct().collect(Collectors.toList());
        friendsHop2 = friendsHop2.stream().distinct().collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        for (String str : friendsHop1) {
            for(String str2 : friendsHop2){
                if(str.equals(str2))  result.add(str);
            }
        }

        System.out.println("test");

    }

    private static void queryFive(){


        /*
        Given a start customer and a product category, find persons who are this customer's
friends within 3-hop friendships in Knows graph, besides, they have bought products in the
given category. Finally, return feedback with the 5-rating review of those bought products.

Étant donné un client de départ et une catégorie de produits,
trouvez des personnes qui sont les amis de ce client au sein d'amitiés à 3 sauts dans le graphique Knows,
en plus, ils ont acheté des produits dans la catégorie donnée. Enfin, retournez vos commentaires avec l'évaluation 5 de ces produits achetés.
         */

    }

    private static void querySix(){

        /*
        Given customer 1 and customer 2, find persons in the shortest path between them
in the subgraph, and return the TOP 3 best sellers from all these persons' purchases.

Étant donné le client 1 et le client 2, trouvez les personnes dans le chemin le plus court entre eux dans le sous-graphique et renvoyez les 3 meilleurs vendeurs parmi les achats de toutes ces personnes.
         */



    }

    private static void querySeven(){

        /*
        For the products of a given vendor with declining sales compare to the former
quarter, analyze the reviews for these items to see if there are any negative sentiments.

Pour les produits d'un fournisseur donné avec des ventes en baisse par rapport au trimestre précédent,
analysez les critiques de ces articles pour voir s'il y a des sentiments négatifs.
         */


    }

    private static void queryEight(){
/*
For all the products of a given category during a given year, compute its total sales
amount, and measure its popularity in the social media.
 */
    }

    private static void queryNine(){

        /*
        Find top-3 companies who have the largest amount of sales at one country, for each
company, compare the number of the male and female customers, and return the most recent
posts of them.
         */

    }

    private static void queryTen(){

        /*
        Find the top-10 most active persons by aggregating the posts during the last year,
then calculate their RFM (Recency, Frequency, Monetary) value in the same period, and return
their recent reviews and tags of interest.
         */


    }


}
