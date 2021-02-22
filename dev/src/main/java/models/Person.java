package models;

import com.marklogic.client.pojo.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthday;
    private String createDateStr;
    private long createDate;
    private String location;
    private String browserUsed;
    private int place;
    private List<Post> posts = new ArrayList<>();
    private List<String> interestTag  = new ArrayList<>();
    private List<Order> orders  = new ArrayList<>();

    public Person(){

    }

    public String toString(){

        StringBuilder builder = new StringBuilder();

        builder.append("Profile of person : ").append(id).append("\n");
        builder.append("First name : ").append(firstName).append("\n");
        builder.append("Last name : ").append(lastName).append("\n");
        builder.append("Gender : ").append(gender).append("\n");
        builder.append("Birthday : ").append(birthday).append("\n");
        builder.append("Create date : ").append(createDateStr).append("\n");
        builder.append("Location : ").append(location).append("\n");
        builder.append("Place : ").append(place).append("\n");


        return builder.toString();

    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBrowserUsed() {
        return browserUsed;
    }

    public void setBrowserUsed(String browserUsed) {
        this.browserUsed = browserUsed;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<String> getInterestTag() {
        return interestTag;
    }

    public void setInterestTag(List<String> interestTag) {
        this.interestTag = interestTag;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
