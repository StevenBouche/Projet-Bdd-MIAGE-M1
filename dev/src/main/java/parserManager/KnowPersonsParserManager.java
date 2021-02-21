package parserManager;

import dal.DataAccess;
import models.LinkPerson;
import models.Vendor;
import parser.LinkPersonParser;

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

    public KnowPersonsParserManager() throws IOException, URISyntaxException {
        this.linksStr = access.getDataCSV("person_knows_person_0_0.csv", '|', true);
        this.linksStr.remove(0);
    }

    public List<LinkPerson> getLinksPerson(){

        if(!linksPerson.isEmpty()||linksStr.isEmpty()) return linksPerson;

        LinkPersonParser parser = new LinkPersonParser(this.linksStr);
        this.linksPerson = parser.getDataParse();

        return this.linksPerson;

        }

    }

