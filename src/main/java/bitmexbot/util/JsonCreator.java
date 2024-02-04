package bitmexbot.util;

import bitmexbot.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class JsonCreator {

    private static final Gson gson = new Gson();
    private static final Gson gsonWithoutFields = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

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

    public Order [] parsOrders (String message){
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String str = jsonObject.get("data").toString();
        Order [] myOrders = gson.fromJson(str, Order[].class);
        return myOrders;
    }


    }

