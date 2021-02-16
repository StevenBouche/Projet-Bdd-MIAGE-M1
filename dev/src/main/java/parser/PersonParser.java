package parser;

import models.Person;
import models.Post;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PersonParser extends Parser<List<String[]>, Person> {

    public PersonParser(List<String[]> data) {
        super(data);
    }

    @Override
    protected List<Person> getObjects() {
        List<Person> persons = new ArrayList<>();
        Person p;
        DateTimeFormatter format =  DateTimeFormatter.ofPattern("yyyy-MM-dd['T']HH:mm:ss.SSSX");
        for(String[] strs : this.data){
            p = new Person();
            p.setId(strs[0]);
            p.setFirstName(strs[1]);
            p.setLastName(strs[2]);
            p.setGender(strs[3]);
            p.setBirthday(strs[4]);
            p.setCreateDateStr(strs[5]);
            p.setCreateDate(ZonedDateTime.parse(strs[5],format).toEpochSecond());
            p.setLocation(strs[6]);
            p.setBrowserUsed(strs[7]);
            p.setPlace(Integer.valueOf(strs[8]));
            persons.add(p);
        }
        return persons;
    }

}
