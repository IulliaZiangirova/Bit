package bitmexbot.util;

import bitmexbot.model.Order;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class JsonCreator {

    private static final Gson gson = new Gson();

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public Order fromJson(String string) {
        Order order = gson.fromJson(string, Order.class);
        return order;
    }

    // Parse the JSON string into a JsonObject
    public Double parsStartPrice(String message) {
    JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
    // Extract the "price" value from the JsonObject
    Double price =  Double.valueOf(jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().get("price").getAsFloat());
    return price;
    }

    public void parsOrders (String message){
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);

    }
}
