package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import dal.MarkLogicUtility;
import models.Product;
import models.Vendor;

import java.util.List;

public class VendorManager extends MarkLogicManager<Vendor,String> {

    List<Vendor> vendors = null;


    public VendorManager(MarkLogicUtility utility) {
        super(utility, Vendor.class, String.class);
    }

    public Vendor getVendorHaveProduct(String idProduct){

        QueryManager queryMgr = this.getNewQueryManager();
        StringQueryDefinition stringQry = queryMgr.newStringDefinition();
        stringQry.setCriteria(idProduct);

        JacksonHandle results = queryMgr.search(stringQry, new JacksonHandle());

        JsonNode node = results.get();

        return this.readFirstOne(stringQry);
    }

    public Product getProductOfVendor(String idProduct){
        Vendor v = this.getVendorHaveProduct(idProduct);
        return v != null ? this.getProductOfVendor(v,idProduct) : null;
    }

    public Product getProductOfVendor(Vendor v, String idProduct) {
        return v.getProducts().stream()
                .filter(product -> product.getAsin().equals(idProduct))
                .findFirst()
                .orElse(null);
    }

}
