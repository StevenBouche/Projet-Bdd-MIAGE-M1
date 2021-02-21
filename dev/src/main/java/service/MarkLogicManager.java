package service;

import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.pojo.PojoPage;
import com.marklogic.client.pojo.PojoQueryBuilder;
import com.marklogic.client.pojo.PojoQueryDefinition;
import com.marklogic.client.pojo.PojoRepository;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import dal.MarkLogicUtility;
import models.Order;
import models.Person;
import models.Post;
import models.Vendor;
import viewModel.NodePerson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class MarkLogicManager<T,K extends Serializable> {

    protected MarkLogicUtility utility;
    protected PojoRepository<T,K> repository;
    protected PojoQueryBuilder<T> query;

    public MarkLogicManager(MarkLogicUtility utility, Class<T> classT, Class<K> classK){
        this.utility = utility;
        this.repository = utility.createPojoRepository(classT,classK);
        this.query = this.repository.getQueryBuilder();
        this.repository.setPageLength(1000);
    }

    public void release(){
        this.utility.client.release();
    }

    protected QueryManager getNewQueryManager(){
        return this.utility.client.newQueryManager();
    }

    protected T readFirstOne(PojoQueryDefinition query){

        PojoPage<T> element = this.repository.search(query,1);

        if(element.size()>=1) return element.next();

        return null;
    }

    protected List<T> readAll(PojoQueryDefinition query){

        List<T> persons = new ArrayList<>();
        int startIndex = 1;

        PojoPage<T> elements = this.repository.search(query,startIndex);
        long totalSize = elements.getTotalSize();

        while(startIndex<=totalSize){

            if(startIndex>1) elements = this.repository.search(query,startIndex);

            while(elements.hasNext()) persons.add(elements.next());

            startIndex += elements.getPageSize();

        }

        return persons;
    }

    protected List<T> readAll() throws InterruptedException {

        List<T> elements = new ArrayList<>();
        int startIndex = 1;
        long total = this.repository.count();
        long pageSize = this.repository.getPageLength();
        List<Thread> threads = new ArrayList<>();
        Runnable r;

        while(startIndex <= total){
            long finalStartIndex = startIndex;
            r = () -> {
                PojoPage<T> persons = this.repository.readAll(finalStartIndex);
                while(persons.hasNext()) elements.add(persons.next());
            };
            threads.add(new Thread(r));
            startIndex += pageSize;
        }

        this.executeTasks(threads);

        return elements;

    }

    protected void executeTasks(List<Thread> threads) throws InterruptedException {

        long startProcessingTime = System.currentTimeMillis();

        for (Thread t: threads) t.start();

        for (Thread t: threads) t.join();

        long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;

        System.out.println("Total time execute tasks : "+totalProcessingTime);

    }

}
