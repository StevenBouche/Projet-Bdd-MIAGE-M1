package parser;

import models.LinkPerson;

import java.util.ArrayList;
import java.util.List;

public class LinkPersonParser extends Parser<List<String[]>,LinkPerson> {

   public LinkPersonParser(List<String[]> data){
        super(data);
    }

    @Override
    protected List<LinkPerson> getObjects(){List<LinkPerson>  links = new ArrayList<>();

    LinkPerson link;
    for(String[] strs : this.data){
        link = new LinkPerson();
        link.setIdPersonPrimary(strs[0]);
        link.setIdPersonSecondary(strs[1]);
        link.setDate(strs[2]) ;

        links.add(link);
    }
        return links;
    }

}
