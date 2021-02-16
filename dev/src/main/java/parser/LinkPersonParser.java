package parser;

import models.LinkPerson;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LinkPersonParser extends Parser<List<String[]>,LinkPerson> {

   public LinkPersonParser(List<String[]> data){
        super(data);
    }

    @Override
    protected List<LinkPerson> getObjects(){List<LinkPerson>  links = new ArrayList<>();

    LinkPerson link;
        DateTimeFormatter format =  DateTimeFormatter.ofPattern("yyyy-MM-dd['T']HH:mm:ss.SSSX");


    for(String[] strs : this.data){
        link = new LinkPerson();
        link.setIdPersonPrimary(strs[0]);
        link.setIdPersonSecondary(strs[1]);
        link.setDateStr(strs[2]) ;
        link.setDate(ZonedDateTime.parse(strs[2],format).toEpochSecond());
        links.add(link);
    }
        return links;
    }

}
