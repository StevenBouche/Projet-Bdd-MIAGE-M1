package service;

import models.Vendor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class VendorManager {

    DataAccess access = new DataAccess();
    List<String[]> vendorsStr;

    public VendorManager() throws IOException, URISyntaxException{
        this.vendorsStr = access.getDataCSV("Vendor.csv",',');
    }

}
