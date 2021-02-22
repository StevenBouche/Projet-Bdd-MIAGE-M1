package parser;

import models.Feedback;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.List;

public class FeedbackParser extends Parser<List<String[]>,Feedback> {

    public int size;

    public FeedbackParser(List<String[]> data){
        super(data);
    }

    @Override
    protected List<Feedback> getObjects(){List<Feedback> feeds = new ArrayList<>();

        Feedback feed;

        int i = 1;
        for(String[] strs : this.data){
            feed = new Feedback();
            feed.setIdPerson(strs[0]);
            feed.setAsinProduct(strs[1]);
            feed.setNote(strs[2].substring(0,4));
            size = strs[2].length();
            feed.setComment(strs[2].substring(5,size));
            i++;
            feed.setId(String.valueOf(i));
            feeds.add(feed);
        }

        return feeds;
    }

}

//String test = "salut les bolosses";
//System.out.println(test.substring(2,5));
// Bien faire les commentaires et notes séparé lors de la construction
// pour la note on prend le trucStr[2].substring(0,4)
