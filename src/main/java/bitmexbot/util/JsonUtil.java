package bitmexbot.util;

import bitmexbot.model.Order;
import com.google.gson.*;


public class JsonUtil {

    private static final Gson gson = new Gson();

    private static final Gson gsonWithoutFields = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public Order fromJson(String string) {
        Order order = gsonWithoutFields.fromJson(string, Order.class);
        return order;
    }

    // Parse the JSON string into a JsonObject
    public Double parsStartPrice(String message) {
    JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
    // Extract the "price" value from the JsonObject
    Double price =  Double.valueOf(jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().get("price").getAsFloat());
    return price;
    }

    public Order [] parsOrders (String message){
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String str = jsonObject.get("data").toString();
        Order [] myOrders = gsonWithoutFields.fromJson(str, Order[].class);
        return myOrders;
    }

}

