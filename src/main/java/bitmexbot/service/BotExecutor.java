package bitmexbot.service;

import bitmexbot.client.BitmexWebSocketClient;
import bitmexbot.dao.OrderDao;
import bitmexbot.model.*;
import bitmexbot.util.JsonUtil;
import bitmexbot.util.PropertyUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Setter
@Getter
@Slf4j
public class BotExecutor {

    private final String apiKey;
    private final String apiSecret;
    private final BitmexClient bitmexClient;
    private final JsonUtil jsonUtil = new JsonUtil();
    private final BitmexWebSocketClient bitmexWebSocketClient;
    private final OrderDao orderDao = new OrderDao();
    private final PropertyUtil propertyUtil = new PropertyUtil();
    private Bot bot;


    public BotExecutor(){
        this.apiKey = propertyUtil.get("apiKey");
        this.apiSecret = propertyUtil.get("apiSecret");
        this.bitmexClient = BitmexClientFactory.newTestnetBitmexClient(propertyUtil.get("apiKey"), propertyUtil.get("apiSecret"));
        this.bitmexWebSocketClient = new BitmexWebSocketClient(propertyUtil.get("apiKey"), propertyUtil.get("apiSecret"));
    }

    public void start(){
        bitmexWebSocketClient.connect();
        bitmexWebSocketClient.setBotExecutor(this);
        initBot(100, 5, 100);
        bot.setSequenceFibonacci(initSequenceFibonacci(6));
    }


    private void sendOrders(Double price, int orderCount, String side){
        Double orderPrice = price;
        int sign = side.equals("Buy") ? -1 : 1;
        for (int i = 0; i < orderCount; i++) {
            orderPrice = orderPrice + sign * bot.getStep() * bot.getSequenceFibonacci()[i];
            Order order = Order.builder()
                    .orderQty(bot.getSequenceFibonacci()[i] * bot.getCoefficient() )
                    .ordType(OrderType.LMT)
                    .side(side)
                    .symbol(Symbol.XBTUSD)
                    .price(orderPrice)
                    .build();
            bitmexClient.sendOrder(order);
        }
    }

    public void parsData(String message){
        if (message.contains("\"table\":\"order\",\"action\":\"update\"")){
            Order [] updatedOrders = jsonUtil.parsOrders(message);
            checkUpdatedOrdersAndMakeChanges(updatedOrders);
        }
        if (message.contains("\"table\":\"trade\"")){
            Double price = jsonUtil.parsStartPrice(message);
            log.info("Start price: " + price);
            sendOrders(price, bot.getLevel(), "Buy");
        }
    }

    public void reconstructSellOrders(Order order) {
       List<Order> ordersToClose = orderDao.findSellOrders();
        for (Order orderToClose : ordersToClose) {
            bitmexClient.cancelOrderById(orderToClose.getOrderID());
        }
        sendOrders(order.getPrice(), ordersToClose.size() + 1, "Sell");
       }

    private void checkUpdatedOrdersAndMakeChanges (Order [] orders){
        for (Order order: orders) {
            orderDao.merge(order);
            if (order.getOrdStatus() == OrderStatus.FILLED && order.getSide().equals("Buy")){
                reconstructSellOrders(order);
            }
        }
    }

    public void stop(){
        bitmexWebSocketClient.disconnect();
    }

    private void initBot(double step, int level, double coefficient){
        this.bot = new Bot(step, level, coefficient);
    }


    private int[]  initSequenceFibonacci(int levelFibonacci){
        int[] sequence = new int[levelFibonacci];
        sequence[0] = 1;
        sequence[1] = 1;
        sequence[2] = 1;
        for (int i = 3; i < levelFibonacci; i++) {
            sequence[i] = sequence[i - 1] + sequence[i - 2];
        }
        return sequence;
    }
}
