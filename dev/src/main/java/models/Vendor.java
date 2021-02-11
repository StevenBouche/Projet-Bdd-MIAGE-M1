package models;

import java.util.List;

public class Vendor {

    private String id;
    private String country;
    private String industry;
    private List<Product> products;

    public String getTitle() {
        return id;
    }

    public void setTitle(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
