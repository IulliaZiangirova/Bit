package bitmexbot.service;

import bitmexbot.client.BitmexWebSocketClient;
import bitmexbot.model.Bot;

public class BotExecutor {

    private Bot bot;
    private BitmexClient bitmexClient;
    private final BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient();

    public void start(){
        bitmexWebSocketClient.connect();
    }
    public void stop(){
        bitmexWebSocketClient.disconnect();
    }

    public void getSequenceFibonacci(){
        //на основе лвл генерируем уровни фибоначи
    }
}
