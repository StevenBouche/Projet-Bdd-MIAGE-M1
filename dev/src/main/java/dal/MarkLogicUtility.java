package dal;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.pojo.PojoRepository;

public class MarkLogicUtility {

    public DatabaseClient client;
    public String host;
    public int port;
    public String username;
    public String password;

    public MarkLogicUtility() {

        this.host = "localhost";
        this.port = 8001;
        this.username = "Admin";
        this.password = "Admin";
        this.createClient(host, port, username, password);

    }

    public void createClient(String host, int port, String username, String password) {

        this.client = DatabaseClientFactory.newClient(host, port, new DatabaseClientFactory.DigestAuthContext(username, password));

    }
    
}
