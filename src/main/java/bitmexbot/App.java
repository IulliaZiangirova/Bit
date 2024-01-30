package bitmexbot;


import bitmexbot.client.BitmexWebSocketClient;
import bitmexbot.service.BotExecutor;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {


    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {

        List.of("7e0c8e81-9f2a-4f5c-b0f5-815aea585d9d", "85557ea0-8703-4468-8ecc-cca5e2e767e9");


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
//        bitmexClient.sendOrder(order);

        //bitmexClient.cancelOrderById("b5cc1d5f-cada-4674-bd41-cb7f325515fb");

        BotExecutor botExecutor = new BotExecutor();
        botExecutor.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        botExecutor.logic();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        botExecutor.stop();






    }
}
