package parser;


import models.Post;

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
        for(String[] strs : this.data){
               p = new Post();
               p.setId(strs[0]);
               p.setCreateDate(strs[2]);
               p.setLocation(strs[3]);
               p.setBrowserUsed(strs[4]);
               p.setLanguage(strs[5]);
               p.setContent(strs[6]);
               p.setLength(strs[7]);
               posts.add(p);
        }
        return posts;
    }

}
