package models;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int personId;
    private Date orderDate;
    private float totalPrice;
    private List<OrderLine> lines;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}