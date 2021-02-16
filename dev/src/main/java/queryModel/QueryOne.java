package queryModel;

import models.Feedback;
import models.Order;
import models.Post;
import java.util.ArrayList;
import java.util.List;

/*
For a given customer, find his/her all related data including profile, orders, invoices, feedback, comments,
and posts in the last month, return the category in which he/she has bought the largest number of products,
and return the tag which he/she has engaged the greatest times in the posts.
 */

public class QueryOne {

    private String idPerson;
    private String firstNamePerson;
    private String lastNamePerson;
    private String genderPerson;
    private String birthdayPerson;
    private String locationPerson;
    private int placePerson;

    private List<Feedback> lastMonthFeedback = new ArrayList<>();
    private List<Post> lastMonthPosts = new ArrayList<>();
    private List<Order> lastMonthOrders = new ArrayList<>();
    private String mostBuyProductBrand;
    private String mostTagPost;

    public QueryOne(String idPerson){
        this.idPerson = idPerson;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }

    public String getFirstNamePerson() {
        return firstNamePerson;
    }

    public void setFirstNamePerson(String firstNamePerson) {
        this.firstNamePerson = firstNamePerson;
    }

    public String getLastNamePerson() {
        return lastNamePerson;
    }

    public void setLastNamePerson(String lastNamePerson) {
        this.lastNamePerson = lastNamePerson;
    }

    public String getGenderPerson() {
        return genderPerson;
    }

    public void setGenderPerson(String genderPerson) {
        this.genderPerson = genderPerson;
    }

    public String getBirthdayPerson() {
        return birthdayPerson;
    }

    public void setBirthdayPerson(String birthdayPerson) {
        this.birthdayPerson = birthdayPerson;
    }

    public int getPlacePerson() {
        return placePerson;
    }

    public void setPlacePerson(int placePerson) {
        this.placePerson = placePerson;
    }

    public List<Feedback> getLastMonthFeedback() {
        return lastMonthFeedback;
    }

    public void setLastMonthFeedback(List<Feedback> lastMonthFeedback) {
        this.lastMonthFeedback = lastMonthFeedback;
    }

    public String getMostBuyProductBrand() {
        return mostBuyProductBrand;
    }

    public void setMostBuyProductBrand(String mostBuyProductBrand) {
        this.mostBuyProductBrand = mostBuyProductBrand;
    }

    public String getMostTagPost() {
        return mostTagPost;
    }

    public void setMostTagPost(String mostTagPost) {
        this.mostTagPost = mostTagPost;
    }

    public String getLocationPerson() {
        return locationPerson;
    }

    public void setLocationPerson(String locationPerson) {
        this.locationPerson = locationPerson;
    }

    public List<Post> getLastMonthPosts() {
        return lastMonthPosts;
    }

    public void setLastMonthPosts(List<Post> lastMonthPosts) {
        this.lastMonthPosts = lastMonthPosts;
    }

    public List<Order> getLastMonthOrders() {
        return lastMonthOrders;
    }

    public void setLastMonthOrders(List<Order> lastMonthOrders) {
        this.lastMonthOrders = lastMonthOrders;
    }
}
