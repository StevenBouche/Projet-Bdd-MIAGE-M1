package service;

import models.Vendor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendorManagerTest {

      @BeforeAll
      static void init() {

       }

      @Test
       void VendorManager() throws IOException, URISyntaxException {
          Vendors();
    }

    DataAccess access = new DataAccess();
    List<String[]> vendorsStr;

    public void Vendors() throws IOException, URISyntaxException{
        this.vendorsStr = access.getDataCSV("Vendor.csv",',');

        System.out.println(vendorsStr);
    }
}