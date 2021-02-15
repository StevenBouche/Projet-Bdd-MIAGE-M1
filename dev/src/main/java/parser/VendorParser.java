package parser;

import models.Vendor;

import java.util.ArrayList;
import java.util.List;

public class VendorParser extends  Parser<List<String[]>, Vendor> {

    public VendorParser(List<String[]> data){
        super(data);
    }

    @Override
    protected List<Vendor> getObjects(){
        List<Vendor> vendors = new ArrayList<>();

        Vendor vendor;
        for (String[] strs : this.data){
            vendor = new Vendor();
            vendor.setTitle(strs[0]);

            vendors.add(vendor);
        }

        return vendors;
    }

}
