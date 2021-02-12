package parser;

import models.Order;
import models.OrderLine;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderParser extends Parser<JSONArray, Order> {

    public OrderParser(JSONArray data) {
        super(data);
    }

    @Override
    protected List<Order> getObjects() {

        List<JSONObject> jsonObject = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();

        for (int i = 0; i < this.data.length(); i++)
            jsonObject.add(this.data.getJSONObject(i));

        jsonObject.forEach(object -> {

            Order o = new Order();

            String pid = String.valueOf(object.get("PersonId"));
            String oid = object.getString("OrderId");
            String odate = object.getString("OrderDate");
            double tp = object.getDouble("TotalPrice");

            Object tmp = object.get("Orderline");

            o.setId(oid);
            o.setPersonId(pid);
            o.setOrderDate(odate);
            o.setTotalPrice(tp);

            try{
                if(tmp instanceof JSONArray){
                    JSONArray lines = (JSONArray) tmp;
                    for(int i = 0; i < lines.length(); i++){
                        JSONObject tmpobj = lines.getJSONObject(i);
                        OrderLine line = this.parseOrderLine(tmpobj);
                        o.getLines().add(line);
                    }

                } else if(tmp instanceof JSONObject){
                    JSONObject obj = (JSONObject) tmp;
                    OrderLine line = this.parseOrderLine(obj);
                    o.getLines().add(line);
                }
            } catch(JSONException e){
                e.printStackTrace();
            }

            orders.add(o);

        });

        return orders;
    }

    private OrderLine parseOrderLine(JSONObject obj){
        OrderLine line = new OrderLine();
        line.setProductId(obj.getInt("productId"));
        line.setPrice(obj.getDouble("price"));
        line.setAsin(obj.getString("asin"));
        line.setBrand(obj.getString("brand"));

        Object str = obj.getString("title");
        if(str!=null&&str instanceof String) line.setTitle((String)str);
        else line.setTitle("");

        return line;
    }

}
