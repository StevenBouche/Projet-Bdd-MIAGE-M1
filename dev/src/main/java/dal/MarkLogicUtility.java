package dal;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.pojo.PojoRepository;
import models.Person;

import java.io.Serializable;
import java.util.List;

public class MarkLogicUtility {

    public DatabaseClient client;
    public String host;
    public int port;
    public String username;
    public String password;

    public MarkLogicUtility(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.createClient(host, port, username, password);
    }

    public void releaseConnection(){
        this.client.release();
    }

    public void createClient(String host, int port, String username, String password) {
        this.client = DatabaseClientFactory.newClient(host, port, new DatabaseClientFactory.DigestAuthContext(username, password));
    }

    public <T,K extends Serializable> PojoRepository<T,K> createPojoRepository(Class<T> classData, Class<K> classId){
        return this.client.newPojoRepository(classData, classId);
    }

    public <T,K extends Serializable> Thread writeDataInRepository(PojoRepository<T,K> repo, List<T> datas){
        Runnable r = () -> {
            for(T data : datas){
                repo.write(data);
            }
        };
        Thread t = new Thread(r);
        t.start();
        return t;
    }
    
}
