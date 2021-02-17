package queryModel;

import models.Person;

import java.util.ArrayList;
import java.util.List;

/*

For a given product during a given period, find the people who commented or posted on it, and had bought it.

 */

public class QueryTwo {

    private String idProduct;
    private String asinProduct;
    private long startPeriod;
    private long finishPeriod;

    private List<Person> personWhoHaveCommentOrPostProduct = new ArrayList<>();
    private List<Person> personWhoHaveBuyProduct = new ArrayList<>();

}
