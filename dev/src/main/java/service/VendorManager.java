package service;

import models.AdressVendor;
import models.Product;
import models.Vendor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

class LinkProductVendor{
    private String idVendor;
    private String idProduct;

    public String getIdVendor() {
        return idVendor;
    }

    public void setIdVendor(String idVendor) {
        this.idVendor = idVendor;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
}

public class VendorManager {

    DataAccess access = new DataAccess();

    List<Vendor> vendors = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<LinkProductVendor> linkProductVendors = new ArrayList<>();

    List<String[]> linkProductVendorsStr;
    List<String[]> productsStr;
    List<String[]> vendorsStr;


    public VendorManager() throws IOException, URISyntaxException{
        this.vendorsStr = access.getDataCSV("Vendor.csv",',');
        this.productsStr = access.getDataCSV("Product.csv",',');
        this.linkProductVendorsStr = access.getDataCSV("BrandByProduct.csv",',');

    }

    public List<Vendor> getVendors(){

        if(vendors.size()>0) return vendors;
        Map<String, List<Vendor>> groupVendor = vendorsStr.stream()
                .collect(groupingBy(Vendor::getTitle));

        this.vendorsStr.forEach(vendor ->{
            Vendor seller = new Vendor();
            seller.setTitle(vendor[0]);
            if(groupVendor.containsKey(seller.getTitle())){
                groupVendor.get(seller.getTitle()).forEach(group -> {
                    AdressVendor adressVendor = new AdressVendor();
                    adressVendor.setCountry(vendor[1]);
                    adressVendor.setIndustry(vendor[2]);

                });
                groupVendor.remove(seller.getTitle());
            }
            this.vendors.add(seller);
        });

        this.buildProduct();

        return this.vendors;

    }
//asin,title,price,imgUrl
    private void buildProduct() {
        this.productsStr.forEach(product ->{
            Product p = new Product();
            p.setTitle(product[1]);
            p.setAsin(product[0]);
       //     p.setPrice(Float.valueOf(product[2]));
            p.setImgUrl(product[3]);

            this.products.add(p);
        });



        this.linkProductsToVendors();


    }

    private void linkProductsToVendors() {


        Map<String,Vendor> mapVendor = this.vendors.stream().collect(Collectors.toMap(Vendor::getTitle, v -> v));
        Map<String,Product> mapProduct = this.products.stream().collect(Collectors.toMap(Product::getAsin, p -> p));

        this.linkProductVendors.forEach(link -> {
            String idv = link.getIdVendor();
            String idp = link.getIdProduct();

            if (mapVendor.containsKey(idv)&&mapProduct.containsKey(idp)) {
                mapVendor.get(idv).getProducts().add(mapProduct.get(idp));
            }
        });

    }
}
