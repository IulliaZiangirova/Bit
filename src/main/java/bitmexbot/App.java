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

//        List.of("7e0c8e81-9f2a-4f5c-b0f5-815aea585d9d", "85557ea0-8703-4468-8ecc-cca5e2e767e9");
//
//        String apiKey = "GfibqQZKf1KvKJJ4BwK63-QJ";
//        String apiSecret = "i_dE1FKK42t64fB7qMLcaYL0xfe3yqaR2LqouYqf-HM02QCP";
//        BitmexClient bitmexClient = BitmexClientFactory.newTestnetBitmexClient(apiKey, apiSecret);
//
//        Order order = Order.builder()
//                .orderQty(300)
//                .ordType(OrderType.LMT)
//                .side("Sell")
//                .symbol(Symbol.XBTUSD)
//                .price(40000.)
//                .build();
//////
////        bitmexClient.getAllOrder();
////
//     bitmexClient.sendOrder(order);

        //bitmexClient.cancelOrderById("b5cc1d5f-cada-4674-bd41-cb7f325515fb");

        BotExecutor botExecutor = new BotExecutor();
        botExecutor.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        botExecutor.logic();
        try {
            TimeUnit.SECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        botExecutor.stop();






    }
}
