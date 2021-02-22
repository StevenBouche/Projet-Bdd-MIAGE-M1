package queryModel;

import models.Person;

import java.util.ArrayList;
import java.util.List;

/*

For a given product during a given period, find the people who commented or posted on it, and had bought it.

 */

public class QueryTwo {

    public String idProduct;
    public String asinProduct;
    public long startPeriod;
    public long endPeriod;
    public String startStr;
    public String endStr;

    public List<Person> personWhoHaverPostAndBuyProduct = new ArrayList<>();


    public String toString(){

        StringBuilder builder = new StringBuilder();

        builder.append("Id product : ").append(asinProduct).append("\n");
        builder.append("Start period : ").append(startStr).append("\n");
        builder.append("End period : ").append(endStr).append("\n");

        builder.append("Result : ").append(personWhoHaverPostAndBuyProduct.size()).append("\n");
        for (Person p : personWhoHaverPostAndBuyProduct) {
            builder.append("ID persons : ").append(p.getId()).append("\n");
        }

        return builder.toString();

    }


}
