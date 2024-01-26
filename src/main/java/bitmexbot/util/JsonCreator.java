package bitmexbot.util;

import bitmexbot.model.Order;
import com.google.gson.Gson;
import jakarta.websocket.OnMessage;

public class JsonCreator {

    private static final Gson gson = new Gson();

    public String toJson (Object object){
        return gson.toJson(object);
    }

    public Order fromJson(String string){
        Order order = gson.fromJson(string, Order.class);
        return order;
    }
}
