package bitmexbot.util;

import com.google.gson.Gson;
import jakarta.websocket.OnMessage;

public class JsonCreator {

    private static final Gson gson = new Gson();

    public String toJson (Object object){
        return gson.toJson(object);
    }
}
