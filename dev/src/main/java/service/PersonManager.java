package service;

import dal.MarkLogicUtility;
import models.Person;

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





}
