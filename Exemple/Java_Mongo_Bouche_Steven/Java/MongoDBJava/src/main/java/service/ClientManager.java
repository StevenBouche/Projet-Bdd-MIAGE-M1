package service;

import MongoDBClient.MongoDBClient;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import model.Note;
import org.bson.Document;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import com.mongodb.util.JSON;

import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.fields;

public class ClientManager {

    private MongoDBClient client;
    private static String nameCollection = "clientFinal";
    private static String nameDatabase = "airbase";
    private MongoCollection<Document> collection;
    private DataAccess dataAccess;

    public ClientManager(){
        this.dataAccess = new DataAccess();
        this.resetCollection();
        this.client = new MongoDBClient(nameDatabase,nameCollection);
        this.collection = this.client.getCollection();
        this.loadBasicData();
    }

    private void loadBasicData() {
        System.out.println("loadBasicData");
        List<Document> doc = this.dataAccess.getData();
        this.collection.insertMany(doc);
        this.printAllClient();
    }

    private void resetCollection(){
        System.out.println("RESET COLLECTION CLIENT");
        MongoDBClient.deleteCollection(nameDatabase,nameCollection);
        MongoDBClient.createCollection(nameDatabase,nameCollection);
    }

    /**
     Cette fonction permet de rechercher un d�partement dans une collection
     connaissant son id.
     */
    public void getClientById(Integer ClientId){

        Document query = new Document();
        query.put("_id", ClientId );

        FindIterable<Document> listClient = this.collection.find(query);

        this.printIterable(listClient);

    }

    /**
     Cette fonction permet de rechercher des d�partements dans une collection.
     Le param�tre whereQuery : permet de passer des conditions de rechercher
     Le param�tre projectionFields : permet d'indiquer les champs � afficher
     Le param�tre sortFields : permet d'indiquer les champs de tri.
     */
    public void getClients(Document whereQuery,
                           Document projectionFields,
                           Document sortFields){

        FindIterable<Document> listClient = this.collection.find(whereQuery)
                .sort(sortFields)
                .projection(projectionFields);

        this.printIterable(listClient);

    }


    /**
     Cette fonction permet de modifier des d�partements dans une collection.
     Le param�tre whereQuery : permet de passer des conditions de recherche
     Le param�tre updateExpressions : permet d'indiquer les champs � modifier
     Le param�tre UpdateOptions : permet d'indiquer les options de mise � jour :
     .upSert : ins�re si le document n'existe pas
     */
    public void updateClients(Document whereQuery,
                              Document updateExpressions,
                              UpdateOptions updateOptions
    ){

        UpdateResult updateResult = this.collection.updateMany(whereQuery, updateExpressions, updateOptions);

        System.out.println("\nR�sultat update : "
                +"getUpdate id: "+updateResult
                +" getMatchedCount : "+updateResult.getMatchedCount()
                +" getModifiedCount : "+updateResult.getModifiedCount()
        );

    }


    /**
     Cette fonction permet de supprimer des d�partements dans une collection.
     Le param�tre filters : permet de passer des conditions de recherche des employ�s � supprimer
     */
    public void deleteClients(Document filters){

        System.out.println("\n\n\n*********** dans deleteClients *****************");
        FindIterable<Document> listClient;
        Iterator it;

        listClient = this.collection.find(filters).sort(new Document("_id", 1));
        it = listClient.iterator();// Getting the iterator
        this.displayIterator(it, "Dans deleteClients: avant suppression");

        this.collection.deleteMany(filters);
        listClient = this.collection.find(filters).sort(new Document("_id", 1));
        it = listClient.iterator();// Getting the iterator
        this.displayIterator(it, "Dans deleteClients: Apres suppression");
    }

    /**
     Parcours un it�rateur et affiche les documents qui s'y trouvent
     */
    private void displayIterator(Iterator it, String message){
        System.out.println(" \n #### "+ message + " ################################");
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

    /**
     1.6.2 Afficher tous les clients habitant Une ville donn�es et ayant plus d'un prenom

     Trouver les bons param�tres.
     */
    public void findByTown(String town){

        System.out.println("findByTown");

        Document whereQuery = new Document("adresse.ville", town).append("$where","this.prenom.length>1");

        // J'applique la requête à ma collection
        FindIterable<Document> listClient = this.collection.find(whereQuery);

        this.printIterable(listClient);

    }

    /**
     1.6.3 Afficher les clients sans leurs adresses
     Trouver les bons param�tres.
     */

    public void findClientWithOutAdress(){
        System.out.println("findClientWithOutAdress");
        FindIterable<Document> listClients = this.collection
                .find()
                .projection(
                        fields(
                                exclude("adresse")
                        )
                );
        this.printIterable(listClients);
    }

    /**
     .6.4 Afficher les informations sur 1 client ainsi que ses appr�ciations
     sur les vols
     Trouver les bons param�tres.
     */
    public void joinClientsVols(Integer filterFieldValue){

        System.out.println("joinClientsVols");

        //Utiliser les agrégats pour utiliser les étapes permettant d'effectuer une jointure
        AggregateIterable<Document> documents = this.collection.aggregate(
                Arrays.asList(
                        // restreindre la jointure sur les champs qui correcspondent à nos options de filtrage
                        Aggregates.match(Filters.eq("_id",filterFieldValue)),
                        // Utiliser look up pour effectuer la jointure
                        Aggregates.lookup(
                                "colVols",
                                Arrays.asList(
                                        Aggregates.unwind("$appreciations"),
                                        Aggregates.match(Filters.eq("appreciations.idClient",filterFieldValue))
                                ),
                                "join"
                        )
                )
        );

        for (Document document : documents) {
            System.out.println(document);
        }

    }


    /**
     charger un document (client) JSON  depuis un fichier vers une collection mongoDB
     Cr�er pour cela un fichier contenant un seul json
     Trouver les bons param�tres.
     */
    public void loadOneClientFromJsonFile(){

        System.out.println("loadOneClientFromJsonFile");

        List<String> jsonElements = this.dataAccess.getDataJSON();

        String firstElement = jsonElements.get(0);

        Document document = Document.parse(firstElement);

        this.resetCollection();
        this.collection.insertOne(document);

        this.printAllClient();

    }

    /**
     charger plusieurs documents (clients) JSON depuis un fichier vers une collection mongoDB
     Utilisez le fichier 2Json_collection_Import_Clients_Airbase.json vu dans le cours
     Trouver les bons param�tres.
     */
    public void loadManyClientsFromJsonFile(){

        System.out.println("loadManyClientsFromJsonFile");

        List<Document> documents = this.dataAccess.getDataJSON().stream()
                .map(Document::parse)
                .collect(Collectors.toList());

        this.resetCollection();
        this.collection.insertMany(documents);

        this.printAllClient();

    }

    /**
     charger des clients contenu dans un fichier CSV vers une collection mongoDB
     Construisez un fichier CSV � partir du fichier json 2Json_collection_Import_Clients_Airbase
     Trouver les bons param�tres.

     */

    public void loadClientsFromCSVFile() throws URISyntaxException, IOException {

        System.out.println("loadClientsFromCSVFile");

        List<String[]> csvElements = this.dataAccess.getDataCSV();

        List<Document> documents = csvElements.stream()
                .map(element -> this.dataAccess.parsingCSV(element))
                .collect(Collectors.toList());

        this.resetCollection();
        this.collection.insertMany(documents);

        this.printAllClient();

    }

    /**
     Ajouter des appr�ciations pour un client donn� et un vol donn�.
     Trouver les bons param�tres.
     // A compl�ter
     */
    public void ajouterUneAppreciationAUnVol(int idVol, int idClient, List<Note> notes){

        System.out.println("\n\najouterUneAppreciationAUnVol param : "+idVol+" "+idClient);

        Document client = this.collection.find(Filters.eq("_id", idClient)).first();

        //test if is an existing client
        if(client==null){
            System.out.println("Client doesn't exist");
            return;
        }

        //get collection of vol
        MongoDBClient clientVol = new MongoDBClient(nameDatabase,"colVols");
        MongoCollection<Document> collectionVol = clientVol.getCollection();

        Document vol = collectionVol.find(Filters.eq("_id", idVol)).first();

        //test if document vol exist
        if(vol==null){
            System.out.println("Vol doesn't exist");
            return;
        }

        //get appreciation where is idVol and match with client id
        AggregateIterable<Document> documents =  collectionVol.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.eq("_id", idVol)),
                        Aggregates.unwind("$appreciations"),
                        Aggregates.match(Filters.eq("appreciations.idClient", idClient))
                )
        );

        //add result in a list
        List<Document> listDocument = new ArrayList<>();
        for (Document document : documents) listDocument.add(document);

        //if is not empty cant add new appreciation
        if(!listDocument.isEmpty()) {
            System.out.println("Client "+idClient+" have already an appreciation for vol "+idVol);
            return;
        }

        List<Document> docs = new ArrayList<>();
        int index = 1;
        for (Note n : notes){
            Document dNote = new Document();
            dNote.append("apid",idClient+""+index);
            dNote.append("critereANoter",n.critere);
            dNote.append("note",n.note);
            docs.add(dNote);
            index++;
        }

        Document d = new Document();
        d.append("idClient",idClient);
        d.append("notes",docs);

        collectionVol.updateOne(Filters.eq("_id", idVol), Updates.push("appreciations", d));

        FindIterable<Document> volIter = collectionVol.find(Filters.eq("_id", idVol));

        this.printIterable(volIter);

    }

    private void printAllClient(){
        FindIterable<Document> listClient = this.collection.find();
        this.printIterable(listClient);
    }

    private void printIterable(FindIterable<Document> listClient){
        // J'itère afin d'afficher chacune des lignes de résultat
        Iterator it = listClient.iterator();
        System.out.println(it);
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

}
