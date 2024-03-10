package bitmexbot;


import bitmexbot.client.BitmexWebSocketClient;
import bitmexbot.dao.OrderDao;
import bitmexbot.dao.UserDao;
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
import bitmexbot.model.User;
import bitmexbot.util.PropertyUtil;

public class App {

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {

     // BotExecutor botExecutor = new BotExecutor();

       // botExecutor.reconstructSellOrders(50000., 1);

//      botExecutor.start();
//
//
//
//        try {
//            TimeUnit.SECONDS.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        botExecutor.stop();
//
//

//        User user = new User ("Yulia", "1111");
//        UserDao userDao = new UserDao();
//        userDao.save(user);

 //      PropertyUtil propertyUtil = new PropertyUtil();
//        BitmexClient bitmexClient = BitmexClientFactory.newTestnetBitmexClient(propertyUtil.get("apiKey"), propertyUtil.get("apiSecret"));
//        Order order = Order.builder()
//                .orderQty(100 )
//                .ordType(OrderType.LMT)
//                .side("Buy")
//                .symbol(Symbol.XBTUSD)
//                .price(50000.)
//                .build();
//
  //      bitmexClient.cancelAllOrders();




    }
}
