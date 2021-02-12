import com.fasterxml.jackson.databind.ObjectMapper;
import models.Order;
import models.Post;
import org.json.JSONArray;
import org.json.JSONObject;
import parser.OrderParserXML;
import parser.PostParser;
import service.DataAccess;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        DataAccess access = new DataAccess();
       /* List<String[]> postsStr = access.getDataCSV("post_0_0.csv",'|');
        PostParser postParser = new PostParser(postsStr);
        List<Post> posts = postParser.getDataParse();
        System.out.print("test");*/

        JSONObject json = access.getDataXMLToJSON("Invoice.xml");

        OrderParserXML orderXML = new OrderParserXML(json);
        List<Order> orders = orderXML.getDataParse();

    }

}
