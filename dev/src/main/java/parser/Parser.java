package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Parser<T> {

    protected List<T> dataParse = new ArrayList<>();
    protected Map<Integer,String> parsingMap = new HashMap<>();
    protected List<String[]> data;

    public Parser(List<String[]> data){
        this.data = data;
        this.setupParsingMap();
        this.dataParse = this.getObjects();
    }

    public abstract void setupParsingMap();
    protected abstract List<T> getObjects();

    public List<T> getDataParse(){
        return this.dataParse;
    }

}
