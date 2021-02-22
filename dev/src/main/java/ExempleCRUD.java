import models.Person;
import org.json.simple.parser.ParseException;
import service.ManagersMarkLogic;

import java.io.IOException;
import java.net.URISyntaxException;


public class ExempleCRUD {

    static ManagersMarkLogic managers = new ManagersMarkLogic();


    public static void main(String[] args) throws IOException, URISyntaxException, ParseException, InterruptedException {

        String idPersonExample = "10995116283190";
        String newPersonId = "21990232607585856";

        System.out.println("Create person test");

        Person ptest = new Person();
        ptest.setId(newPersonId);
        ptest.setFirstName("test");
        ptest.setLastName("testas");

        System.out.println("Insert person test");

        managers.personManager.insertOne(ptest);

        System.out.println("Read person test");

        ptest = managers.personManager.readFirstOne(ptest.getId());

        System.out.println("Result : ");
        System.out.println(ptest);

        System.out.println("Read person already exist : "+idPersonExample);

        Person p = managers.personManager.readFirstOne(idPersonExample);

        System.out.println("Result : ");
        System.out.println(p);

        System.out.println("Try to update person already exist : "+idPersonExample);

        p.setFirstName("Toto");
        p.setLastName("Tata");

        managers.personManager.updateOne(p);

        p = managers.personManager.readFirstOne(p.getId());

        System.out.println("Result : ");
        System.out.println(p);


        System.out.println("Try to delete person already exist and person test");

        managers.personManager.deleteMany(p.getId(),ptest.getId());

        p = managers.personManager.readFirstOne(p.getId());
        ptest = managers.personManager.readFirstOne(ptest.getId());

        System.out.println("Result : ");
        System.out.println(p);
        System.out.println(ptest);

        System.out.println("Finish example");
    }









}
