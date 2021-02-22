package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private String id;
    private String personId;
    private long orderDate;
    private String orderDateStr;
    private double totalPrice;
    private List<OrderLine> lines = new ArrayList<>();

    public Order(){

    }

    public String toString(){

        StringBuilder builder = new StringBuilder();

        builder.append("Id order : ").append(id).append("\n");
        builder.append("Date of creation : ").append(orderDateStr).append("\n");
        builder.append("Price : ").append(totalPrice).append("\n");

        return builder.toString();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDateStr() {
        return orderDateStr;
    }

    public void setOrderDateStr(String orderDateStr) {
        this.orderDateStr = orderDateStr;
    }
}