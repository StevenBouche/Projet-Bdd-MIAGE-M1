package MongoDBClient;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBClient {

    private String dataBaseName;
    private String collectionName;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoClient client;

    public MongoDBClient(String dataBaseName, String collectionName){
        this.dataBaseName = dataBaseName;
        this.collectionName = collectionName;
        this.client = MongoClients.create();
        this.database = this.client.getDatabase(this.dataBaseName);
        this.collection = this.database.getCollection(this.collectionName);
    }

    public MongoCollection<Document> getCollection(){
        return this.collection;
    }

    public static void createCollection(String databaseName, String name){
        MongoClient client = MongoClients.create();
        client.getDatabase(databaseName).createCollection(name);
    }

    public static void deleteCollection(String databaseName, String name){
        MongoDBClient client = new MongoDBClient(databaseName,name);
        client.collection.drop();
    }

}
