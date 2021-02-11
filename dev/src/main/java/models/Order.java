package models;

import java.util.List;

public class Order {

    private int id;
    private int personId;
    private long orderDate;
    private float totalPrice;
    private List<OrderLine> lines;


}
