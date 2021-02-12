package parser;

import models.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

public class OrderParserXML extends Parser<JSONObject, Order> {

    public OrderParserXML(JSONObject data) {
        super(data);
    }

    @Override
    protected List<Order> getObjects() {

        JSONArray array = this.data
                .getJSONObject("Invoices")
                .getJSONArray("Invoice.xml");

        OrderParser parser = new OrderParser(array);

        return parser.getDataParse();
    }



}
