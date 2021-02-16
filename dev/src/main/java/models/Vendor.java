package models;

import java.util.ArrayList;
import java.util.List;

public class Vendor {

    private String id;

    private List<Product> products = new ArrayList<>();

    private List<AdressVendor> adresses = new ArrayList<>();

    public String getTitle() {
        return id;
    }

    public void setTitle(String id) {
        this.id = id;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<AdressVendor> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<AdressVendor> adresses) {
        this.adresses = adresses;
    }
}
