package parserManager;

import models.AdressVendor;
import models.Product;
import models.Vendor;
import dal.DataAccess;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class VendorParserManager {

    DataAccess access = new DataAccess();

    List<Vendor> vendors = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<LinkProductVendor> linkProductVendors = new ArrayList<>();

    List<String[]> linkProductVendorsStr;
    List<String[]> productsStr;
    List<String[]> vendorsStr;


    public VendorParserManager() throws IOException, URISyntaxException{
        this.vendorsStr = access.getDataCSV("Vendor.csv",',',true);
        this.productsStr = access.getDataCSV("Product.csv",',',false);
        this.linkProductVendorsStr = access.getDataCSV("BrandByProduct.csv",',',true);
        this.vendorsStr.remove(0);
        this.productsStr.remove(0);
    }

    public List<Vendor> getVendors(){

        if(!vendors.isEmpty()||vendorsStr.isEmpty()) return vendors;

        Map<String,Vendor> mapVendor = new HashMap<>();

        this.vendorsStr.forEach(vendor ->{

            String idVendor = vendor[0];
            Vendor seller;

            if(mapVendor.containsKey(idVendor)) seller = mapVendor.get(idVendor);
            else {
                seller = new Vendor();
                seller.setTitle(vendor[0]);
                mapVendor.put(idVendor,seller);
            }

            AdressVendor adressVendor = new AdressVendor();
            adressVendor.setCountry(vendor[1]);
            adressVendor.setIndustry(vendor[2]);
            seller.getAdresses().add(adressVendor);

        });

        this.vendors = new ArrayList<>(mapVendor.values());

        this.buildProduct();

        return this.vendors;

    }

    private void buildProduct() {


        this.productsStr.forEach(product ->{

            try{
                Product p = new Product();
                p.setTitle(product[1]);
                p.setAsin(product[0]);
                p.setPrice(Float.parseFloat(product[2]));
                p.setImgUrl(product[3]);
                this.products.add(p);
            } catch (NumberFormatException nb){
                nb.printStackTrace();
            }

        });

        this.linkProductsToVendors();

    }

    private void linkProductsToVendors() {

        Map<String,Vendor> mapVendor = this.vendors.stream().collect(Collectors.toMap(Vendor::getTitle, v -> v));
        Map<String,Product> mapProduct = this.products.stream().collect(Collectors.toMap(Product::getAsin, p -> p));

        this.linkProductVendorsStr.forEach(link -> {
            String idv = link[0];
            String idp = link[1];

            if (mapVendor.containsKey(idv)&&mapProduct.containsKey(idp)) {
                mapVendor.get(idv).getProducts().add(mapProduct.get(idp));
            }
        });

    }
}
