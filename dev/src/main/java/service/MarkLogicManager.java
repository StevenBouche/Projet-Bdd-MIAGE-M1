package service;

import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.pojo.PojoPage;
import com.marklogic.client.pojo.PojoQueryBuilder;
import com.marklogic.client.pojo.PojoQueryDefinition;
import com.marklogic.client.pojo.PojoRepository;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import dal.MarkLogicUtility;
import models.Person;
import models.Post;
import models.Vendor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class MarkLogicManager<T,K extends Serializable> {

    protected MarkLogicUtility utility;
    protected PojoRepository<T,K> repository;
    protected PojoQueryBuilder<T> query;

    public MarkLogicManager(MarkLogicUtility utility, Class<T> classT, Class<K> classK){
        this.utility = utility;
        this.repository = utility.createPojoRepository(classT,classK);
        this.query = this.repository.getQueryBuilder();
        this.repository.setPageLength(100);
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



}
