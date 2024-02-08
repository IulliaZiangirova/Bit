package bitmexbot;


import bitmexbot.client.BitmexWebSocketClient;
import bitmexbot.model.Order;
import bitmexbot.model.OrderType;
import bitmexbot.model.Symbol;
import bitmexbot.service.BitmexClient;
import bitmexbot.service.BitmexClientFactory;
import bitmexbot.service.BotExecutor;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {

        BotExecutor botExecutor = new BotExecutor();
        botExecutor.start();

        try {
            TimeUnit.SECONDS.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        botExecutor.stop();


    }
}
