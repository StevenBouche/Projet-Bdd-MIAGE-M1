package queryModel;

import models.Person;

import java.util.ArrayList;
import java.util.List;

/*

For a given product during a given period, find people who have undertaken activities related to it, e.g.,
 posts, comments, and review, and return sentences from these texts that contain negative sentiments.


 */
public class QueryThree {

    private String idProduct;
    private String asinProduct;
    private long startPeriod;
    private long finishPeriod;

    private List<Person> personWhoInteractWithProduct = new ArrayList<>();
    private List<String> sentencesNegative = new ArrayList<>();


}
