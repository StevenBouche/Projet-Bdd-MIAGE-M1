import dal.MarkLogicUtility;
import models.*;
import org.json.simple.parser.ParseException;
import queryModel.QueryOne;
import service.PersonManager;
import service.VendorManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        qone.setMostBuyProductBrand(personManager.getMostBrandProduct(p));

        // TODO FEEDBACK

        System.out.println(qone);
        System.out.println("test");

    }


    private static void queryTwo(){

        /*
            For a given product during a given period, find the people who commented or posted on it, and had bought it.

            Pour un produit donné pendant une période donnée, trouvez les personnes qui ont commenté ou posté dessus, et l'ont acheté.
        */

        String idProduct = "B0007SOLDM";
        String startDate = "2017-01-01T00:00:00.000+0000";
        String endDate = "2020-01-01T00:00:00.000+0000";
        DateTimeFormatter format =  DateTimeFormatter.ofPattern("yyyy-MM-dd['T']HH:mm:ss.SSSX");
        long startTime = ZonedDateTime.parse(startDate,format).toEpochSecond();
        long endTime = ZonedDateTime.parse(endDate,format).toEpochSecond();

        VendorManager manager = new VendorManager(utility);
        Product p = manager.getProductOfVendor(idProduct);
        List<Person> persons = personManager.getPersonInRelationWithProduct(p);

        List<Person> persons2 = persons.stream().filter(person -> person.getOrders().stream().anyMatch(order -> {
            long date = order.getOrderDate();
            return startTime <= date && endTime >= date &&
                    order.getLines().stream().anyMatch(orderLine -> orderLine.getTitle().equals(p.getTitle()));
        })).collect(Collectors.toList());

        List<Person> person3 = persons.stream().filter(person -> person.getPosts().stream().anyMatch(post -> {
            long date = post.getCreateDate();
            return startTime <= date && endTime >= date;
        }) || person.getOrders().stream().anyMatch(post -> {
            long date = post.getOrderDate();
            return startTime <= date && endTime >= date;
        })).collect(Collectors.toList());

        //todo verif
        System.out.println(p);

    }

    private static void queryThree(){
        /*
        For a given product during a given period, find people who have undertaken
activities related to it, e.g., posts, comments, and review, and return sentences from these texts
that contain negative sentiments

Pour un produit donné au cours d'une période donnée, trouvez des personnes qui ont entrepris des
activités liées à celui-ci, par exemple des publications, des commentaires et des critiques,
et renvoyez des phrases de ces textes qui contiennent des sentiments négatifs
         */

    }

    private static void queryFour(){

        /*
        Find the top-2 persons who spend the highest amount of money in orders. Then for
each person, traverse her knows-graph with 3-hop to find the friends, and finally return the
common friends of these two persons.

Trouvez les 2 personnes qui dépensent le plus d'argent en commandes.
Ensuite, pour chaque personne, parcourez son know-graphe avec 3-hop pour trouver les amis, et enfin renvoyer les amis communs de ces deux personnes.
         */

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
