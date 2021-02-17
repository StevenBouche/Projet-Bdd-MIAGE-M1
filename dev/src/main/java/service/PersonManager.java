package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.pojo.PojoPage;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import dal.MarkLogicUtility;
import models.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonManager extends MarkLogicManager<Person,String> {

    public PersonManager(MarkLogicUtility utility) {
        super(utility, Person.class, String.class);
    }

    public Person getPerson(String id){
        return this.repository.read(id);
    }

    public void test(String id){

       // this.query.


    }



    public List<Post> getPostsLastMonth(Person p) {

        LocalDateTime ldt = this.getDateLastMonth();

        return p.getPosts().stream().filter(post -> {
            LocalDateTime dt = LocalDateTime.ofInstant(Instant.ofEpochMilli(post.getCreateDate()*1000), ZoneOffset.UTC);
            return ldt.getYear() == dt.getYear() && ldt.getMonth() == dt.getMonth();
        }).collect(Collectors.toList());

    }

    public List<Order> getOrdersLastMonth(Person p) {

        LocalDateTime ldt = this.getDateLastMonth();

        return p.getOrders() .stream().filter(order -> {
            LocalDateTime dt = LocalDateTime.ofInstant(Instant.ofEpochMilli(order.getOrderDate()*1000),ZoneOffset.UTC);
            return ldt.getYear() == dt.getYear() && ldt.getMonth() == dt.getMonth();
        }).collect(Collectors.toList());

    }

    public String getMostTagPosts(Person p) {
        List<List<String>> tagMostPost = p.getPosts().stream().map(Post::getTags).collect(Collectors.toList());
        List<String> allTag = new ArrayList<>();
        tagMostPost.forEach(allTag::addAll);
        Map<String, Long> occurs = allTag.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting()));
        return Collections.max(occurs.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private LocalDateTime getDateLastMonth(){
        Date date = new Date(System.currentTimeMillis());
        return LocalDateTime.ofInstant(date.toInstant(),ZoneOffset.UTC).minusMonths(1);
    }

    public String getMostBrandProduct(Person p) {

        Map<String, Integer> occurs = new HashMap<>();

        for(Order o : p.getOrders()){
            for(OrderLine ol : o.getLines()){
                String brand = ol.getBrand();
                if(occurs.containsKey(brand)) occurs.put(brand,occurs.get(brand)+1);
                else occurs.put(brand,1);
            }
        }

        return Collections.max(occurs.entrySet(), Map.Entry.comparingByValue()).getKey();

    }

    public List<Person> getPersonInRelationWithProduct(Product p){

        if(p==null) return null;

        QueryManager queryMgr = this.getNewQueryManager();
        StringQueryDefinition stringQry = queryMgr.newStringDefinition();
        stringQry.setCriteria(p.getTitle());

        List<Person> persons = this.readAll(stringQry);

        return persons;

    }

    public List<Person> getPersonWhoHaveBuyProduct(Product p) {

        QueryManager queryMgr = this.getNewQueryManager();
        StringQueryDefinition stringQry = queryMgr.newStringDefinition();
        stringQry.setCriteria(p.getTitle());

        PojoPage<Person> vendors = this.repository.search(stringQry,1);
        System.out.println(vendors.getTotalPages());
        System.out.println(vendors.getTotalSize());

        return null;
    }

}
