package parser;


import models.Post;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PostParser extends Parser<List<String[]>,Post> {

    public PostParser(List<String[]> data) {
        super(data);
    }

    @Override
    protected List<Post> getObjects() {
        List<Post> posts = new ArrayList<>();
        Post p;
        DateTimeFormatter format =  DateTimeFormatter.ofPattern("yyyy-MM-dd['T']HH:mm:ss.SSSX");
        for(String[] strs : this.data){
            p = new Post();
            p.setId(strs[0]);
            p.setLocation(strs[3]);
            p.setBrowserUsed(strs[4]);
            p.setLanguage(strs[5]);
            p.setContent(strs[6]);
            p.setLength(strs[7]);
            p.setCreateDateStr(strs[2]);
            p.setCreateDate(ZonedDateTime.parse(strs[2],format).toEpochSecond());
            posts.add(p);
        }
        return posts;
    }

}
