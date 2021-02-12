package service;

import models.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import parser.OrderParser;
import parser.OrderParserXML;
import parser.PersonParser;
import parser.PostParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonParsingManager {

    DataAccess access = new DataAccess();

    List<String[]> postsStr;
    List<String[]> personStr;
    List<String[]> linkPostPersonStr;
    List<String[]> linkPersonTagStr;
    List<String[]> linkPostTagStr;
    JSONObject jsonOrderXML;
    JSONArray jsonOrder;


    List<Person> persons;

    public PersonParsingManager() throws InterruptedException {

        List<Runnable> tasks = this.getTasks();
        List<Thread> threads = new ArrayList<>();

        for (Runnable r : tasks){
            Thread t = new Thread(r);
            threads.add(t);
            t.start();
        }

        for(Thread t : threads) t.join();

        /*  this.jsonOrderXML = access.getDataXMLToJSON("Invoice.xml");
        this.jsonOrder = access.getDatasJSON("Order.json");
        this.personStr = access.getDataCSV("person_0_0.csv",'|');
        this.linkPostPersonStr = access.getDataCSV("post_hasCreator_person_0_0.csv",'|');
        this.linkPersonTagStr = access.getDataCSV("person_hasInterest_tag_0_0.csv",'|');
        this.linkPostTagStr = access.getDataCSV("post_hasTag_tag_0_0.csv",'|');*/

        this.postsStr.remove(0);
        this.personStr.remove(0);
        this.linkPostPersonStr.remove(0);
        this.linkPersonTagStr.remove(0);
        this.linkPostTagStr.remove(0);
    }

    public List<Person> getPersons() throws ParseException, IOException, URISyntaxException {
        if(this.persons==null){
            PersonParser pparser = new PersonParser(personStr);
            this.persons = pparser.getDataParse();
            this.buildOtherDatasPersons();
        }
        return this.persons;
    }

    private void buildOtherDatasPersons() throws ParseException, IOException, URISyntaxException {

        List<Order> orders = this.getOrders();
        List<Post> posts = this.getPosts();
        List<LinkPostToPerson> links = this.getPostPersonLink();

        Map<String, Person> mapPerson = this.persons.stream().collect(Collectors.toMap(Person::getId, p -> p));
        Map<String, Post> mapPost = posts.stream().collect(Collectors.toMap(Post::getId, p -> p));

        //Link tag to post
        this.getPostTag().parallelStream().forEach(lp -> {
            String postId = lp.getPostId();
            if(mapPost.containsKey(postId)){
                mapPost.get(postId).getTags().add(lp.getTagId());
            }
        });

        //Link tag to person
        this.getPersonTag().parallelStream().forEach(lp -> {
            String personId = lp.getPersonId();
            if(mapPerson.containsKey(personId)){
                mapPerson.get(personId).getInterestTag().add(lp.getTagId());
            }
        });

        //Link order to person
        orders.parallelStream().forEach(o -> {
            String idPerson = o.getPersonId();
            if(mapPerson.containsKey(idPerson)){
                mapPerson.get(idPerson).getOrders().add(o);
            }
        });

        //link post to person
        links.parallelStream().forEach(l -> {
            String idPerson = l.getPersonId();
            String idPost = l.getPostId();
            if(mapPerson.containsKey(idPerson)&&mapPost.containsKey(idPost)){
                mapPerson.get(idPerson).getPosts().add(mapPost.get(idPost));
            }
        });

    }

    private List<Order> getOrders() throws ParseException, IOException, URISyntaxException {

        JSONObject json = access.getDataXMLToJSON("Invoice.xml");
        OrderParserXML orderXML = new OrderParserXML(json);
        List<Order> orders = orderXML.getDataParse();

        JSONArray jsonOrder = access.getDatasJSON("Order.json");
        OrderParser parser = new OrderParser(jsonOrder);
        List<Order> orders2 = parser.getDataParse();

        orders.addAll(orders2);
        //verif doublon
       /* Map<String, List<Order>> postsPerType = orders.stream()
                .collect(groupingBy(Order::getId));*/

       return orders;
    }

    private List<Post> getPosts(){
        PostParser postParser = new PostParser(postsStr);
        return postParser.getDataParse();
    }

    private List<LinkPostToPerson> getPostPersonLink(){
        List<LinkPostToPerson> link = new ArrayList<>();
        LinkPostToPerson l;
        for(String[] strs : this.linkPostPersonStr){
            l = new LinkPostToPerson();
            l.setPostId(strs[0]);
            l.setPersonId(strs[1]);
            link.add(l);
        }
        return link;
    }

    private List<LinkPersonToTag> getPersonTag(){
        List<LinkPersonToTag> link = new ArrayList<>();
        LinkPersonToTag l;
        for(String[] strs : this.linkPersonTagStr){
            l = new LinkPersonToTag();
            l.setPersonId(strs[0]);
            l.setTagId(strs[1]);
            link.add(l);
        }
        return link;
    }

    private List<LinkPostToTag> getPostTag(){
        List<LinkPostToTag> link = new ArrayList<>();
        LinkPostToTag l;
        for(String[] strs : this.linkPostTagStr){
            l = new LinkPostToTag();
            l.setPostId(strs[0]);
            l.setTagId(strs[1]);
            link.add(l);
        }
        return link;
    }

    private List<Runnable> getTasks(){

        List<Runnable> r = new ArrayList<>();

        r.add(() -> {
            try {
                this.postsStr =access.getDataCSV("post_0_0.csv",'|');
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

       r.add(() -> {
           try {
               this.jsonOrderXML = access.getDataXMLToJSON("Invoice.xml");
           } catch (URISyntaxException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       });

        r.add(() -> {
            try {
                this.jsonOrder = access.getDatasJSON("Order.json");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        r.add(() -> {
            try {
                this.personStr = access.getDataCSV("person_0_0.csv",'|');
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        r.add(() -> {
            try {
                this.linkPostPersonStr = access.getDataCSV("post_hasCreator_person_0_0.csv",'|');
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        r.add(() -> {
            try {
                this.linkPersonTagStr = access.getDataCSV("person_hasInterest_tag_0_0.csv",'|');
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        r.add(() -> {
            try {
                this.linkPostTagStr = access.getDataCSV("post_hasTag_tag_0_0.csv",'|');
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return r;

    }

}
