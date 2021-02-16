package parserManager;

import dal.DataAccess;
import models.LinkPerson;
import models.Vendor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KnowPersonsParserManager {

    DataAccess access = new DataAccess();
    List<String[]>  linksStr;
    List<LinkPerson> linksPerson = new ArrayList<>();
    DateTimeFormatter format =  DateTimeFormatter.ofPattern("yyyy-MM-dd['T']HH:mm:ss.SSSX");



    public KnowPersonsParserManager() throws IOException, URISyntaxException {

        this.linksStr = access.getDataCSV("person_knows_person_0_0.csv", '|', true);
    }

    public List<LinkPerson> getLinksPerson(){
        if(!linksPerson.isEmpty()||linksStr.isEmpty()) return linksPerson;

        Map<String,LinkPerson> mapLinksPerson = new HashMap<>();
        this.linksStr.forEach(link ->{

            String idPerson = link[0];
            LinkPerson personLink;

            if(mapLinksPerson.containsKey(idPerson)) personLink = mapLinksPerson.get(idPerson);
            else{
                personLink = new LinkPerson();
                personLink.setIdPersonPrimary(link[0]);
                personLink.setIdPersonSecondary(link[1]);
                personLink.setDateStr(link[2]);
              //  personLink.setDate(ZonedDateTime.parse(link[2],format).toEpochSecond());

                mapLinksPerson.put(idPerson,personLink);
            }
        });

        this.linksPerson = new ArrayList<>(mapLinksPerson.values());

        return this.linksPerson;


        }

    }

