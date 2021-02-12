package parser;

import java.util.ArrayList;
import java.util.List;

public abstract class Parser<K,T> {

    protected List<T> dataParse = new ArrayList<>();
    protected K data;

    public Parser(K data){
        this.data = data;
        this.dataParse = this.getObjects();
    }

    protected abstract List<T> getObjects();

    public List<T> getDataParse(){
        return this.dataParse;
    }

}
