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
import java.util.Optional;

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

    private Double lastFilledPrice;


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


    private void sendOrdersByFibonachi(Double price, int orderCount, String side){
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
            sendOrdersByFibonachi(price, bot.getLevel(), "Buy");
        }
    }

    public void reconstructSellOrders(Order order, int count) {
        Optional<List<Order>> sellOr = orderDao.findSellOr();
        List<Order> ordersToClose = sellOr.get();
        System.out.println(ordersToClose);
        for (Order orderToClose : ordersToClose) {
            bitmexClient.cancelOrderById(orderToClose.getOrderID());
        }
        sendOrdersByFibonachi(order.getPrice(), ordersToClose.size() + count, "Sell");
        //sendOrders(order.getPrice(), count, "Sell");
       }

    private void resendBuyOrder(Double price, double orderQty){
        double qty = orderQty;
        Optional<Order> buyOrder = orderDao.findBuyOrderWithLastPrice(price);
        if (buyOrder.isPresent()){
            Order order = buyOrder.get();
            qty += order.getOrderQty();
            bitmexClient.cancelOrderById(order.getOrderID());
        }
        Order order = Order.builder()
                .orderQty(qty)
                .ordType(OrderType.LMT)
                .side("Buy")
                .symbol(Symbol.XBTUSD)
                .price(price)
                .build();
        bitmexClient.sendOrder(order);
    }

    private void checkUpdatedOrdersAndMakeChanges (Order [] orders){
        for (Order order: orders) {
            orderDao.merge(order);
        }
        if (orders[0].getOrdStatus() == OrderStatus.FILLED && orders[0].getSide().equals("Buy")){
            lastFilledPrice = orders[0].getPrice();
            reconstructSellOrders(orders[0], orders.length);
        }
        else if (orders[0].getOrdStatus() == OrderStatus.FILLED && orders[0].getSide().equals("Sell")){
            resendBuyOrder(lastFilledPrice, orders[0].getOrderQty()); //тут сумма орднров должна быть
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
