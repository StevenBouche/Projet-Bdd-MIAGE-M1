package models;

import com.marklogic.client.pojo.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Vendor {

    private String id;
    private String title;
    private List<Product> products = new ArrayList<>();
    private List<AdressVendor> adresses = new ArrayList<>();

    public Vendor(){

    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
