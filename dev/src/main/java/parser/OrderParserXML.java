package parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Order;
import models.Post;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderParserXML extends Parser<JSONObject, Order> {

    public OrderParserXML(JSONObject data) {
        super(data);
    }

    @Override
    protected List<Order> getObjects() {

        ArrayList<Order> orders = new ArrayList<>();

        JSONArray array = this.data
                .getJSONObject("Invoices")
                .getJSONArray("Invoice.xml");

        List<JSONObject> jsonObject = new ArrayList<>();

        for (int i = 0; i < array.length(); i++)
            jsonObject.add(array.getJSONObject(i));

                 /*     <OrderId>6711da51-dee6-452a-a7b8-f79a1cbb9436</OrderId>
        <PersonId>10995116278711</PersonId>
        <OrderDate>2022-09-01</OrderDate>
        <TotalPrice>723.88</TotalPrice>
        <Orderline>
            <productId>6465</productId>
            <asin>B000FIE4WC</asin>
            <title>Topeak Dual Touch Bike Storage Stand</title>
            <price>199.95</price>
            <brand>MYLAPS_Sports_Timing</brand>
        </Orderline>*/

        jsonObject.forEach(object -> {

            Order o = new Order();

            String pid = String.valueOf(object.get("PersonId"));
            String oid = object.getString("OrderId");
            String odate = object.getString("OrderDate");
            double tp = object.getDouble("TotalPrice");

            //JSONArray lines = object.getJSONArray("Orderline");

            o.setId(oid);
            o.setPersonId(pid);
            o.setOrderDate(odate);
            o.setTotalPrice(tp);

            orders.add(o);
        });


        return orders;
    }

}
