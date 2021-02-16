package service;

import com.marklogic.client.pojo.PojoQueryBuilder;
import com.marklogic.client.pojo.PojoRepository;
import dal.MarkLogicUtility;

import java.io.Serializable;

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
