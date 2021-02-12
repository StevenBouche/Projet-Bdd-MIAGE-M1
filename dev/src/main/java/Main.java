import models.Post;
import parser.PostParser;
import service.DataAccess;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        DataAccess access = new DataAccess();
        List<String[]> postsStr = access.getDataCSV("post_0_0.csv",'|');
        PostParser postParser = new PostParser(postsStr);
        List<Post> posts = postParser.getDataParse();
        System.out.print("test");

    }

}
