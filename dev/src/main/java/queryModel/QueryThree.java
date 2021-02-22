package queryModel;

import models.Feedback;
import models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*

For a given product during a given period, find people who have undertaken activities related to it, e.g.,
 posts, comments, and review, and return sentences from these texts that contain negative sentiments.


 */
public class QueryThree {

    public String asinProduct;

    public Map<String,List<Feedback>> map;

    public long startPeriod;
    public long endPeriod;
    public String startStr;
    public String endStr;

}
