import MongoDBClient.MongoDBClient;
import model.Note;
import service.ClientManager;
import service.VolManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static ClientManager manager;

    public static void main(String[] args) throws IOException, URISyntaxException {

        // hard reset of collection vol
        MongoDBClient.deleteCollection("airbase","colVols");
        VolManager.main(new String[]{});

        //create the client collection manager
        manager = new ClientManager();

        System.out.println("\n\n");
        manager.findByTown("Nice");

        System.out.println("\n\n");
        manager.findClientWithOutAdress();

        manager.joinClientsVols(7);

        System.out.println("\n\n");
        manager.loadOneClientFromJsonFile();

        System.out.println("\n\n");
        manager.loadManyClientsFromJsonFile();

        System.out.println("\n\n");
        manager.loadClientsFromCSVFile();

        //hard reset client for retrieve all client
        manager = new ClientManager();

        // create notes for test
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("TOTO","BIEN"));
        notes.add(new Note("TATA","BIEN"));
        notes.add(new Note("TUTU","BIEN"));

        //execute test appreciation
        manager.ajouterUneAppreciationAUnVol(100,1, notes);
        manager.ajouterUneAppreciationAUnVol(400,1, notes);
        manager.ajouterUneAppreciationAUnVol(400,40, notes);
        manager.ajouterUneAppreciationAUnVol(100,3, notes);

    }

}
