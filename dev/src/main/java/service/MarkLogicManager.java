package service;

import com.marklogic.client.pojo.PojoQueryBuilder;
import com.marklogic.client.pojo.PojoRepository;
import dal.MarkLogicUtility;
import models.Person;
import models.Post;

import java.io.Serializable;
import java.util.List;

public abstract class MarkLogicManager<T,K extends Serializable> {

    protected MarkLogicUtility utility;
    protected PojoRepository<T,K> repository;
    protected PojoQueryBuilder<T> query;

    public MarkLogicManager(MarkLogicUtility utility, Class<T> classT, Class<K> classK){
        this.utility = utility;
        this.repository = utility.createPojoRepository(classT,classK);
        this.query = this.repository.getQueryBuilder();
    }

}
