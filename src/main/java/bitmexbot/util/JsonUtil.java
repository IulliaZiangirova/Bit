package bitmexbot.util;

import bitmexbot.model.Order;
import com.google.gson.*;


public class JsonUtil {

    private static final Gson gson = new Gson();

    private static final Gson gsonWithoutFields = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public Order fromJsonMy(String string) {
        Order order = gsonWithoutFields.fromJson(string, Order.class);
        return order;
    }

    public Double parsStartPrice(String message) {
    JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
    Double price =  Double.valueOf(jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().get("price").getAsFloat());
    return price;
    }

    public Order parserOrder (String message){
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String str = jsonObject.get("data").toString();
        Order order = gsonWithoutFields.fromJson(str.substring(1, str.length()-1),Order.class);
        return order;
    }

    public Order [] parsOrders (String message){
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String str = jsonObject.get("data").toString();
        Order [] orders = gsonWithoutFields.fromJson(str,Order[].class);
        return orders;
    }

}

