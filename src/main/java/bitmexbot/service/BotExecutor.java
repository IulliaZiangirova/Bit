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
    private Double startPrice;


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
            startPrice = jsonUtil.parsStartPrice(message);
            lastFilledPrice = startPrice;
            log.info("Start price: " + startPrice);
            sendOrdersByFibonachi(startPrice, bot.getLevel(), "Buy");
        }
    }

    private void checkUpdatedOrdersAndMakeChanges (Order [] orders) {
        boolean needToReconstructSellOrders = false;
        boolean needToReconstructBuyOrders = false;
        double sellOrderQtySum = 0;
        for (Order order : orders) {
            orderDao.merge(order);
            if(order.getSide().equals("Buy") && order.getOrdStatus() == OrderStatus.FILLED && order.getPrice() < lastFilledPrice){
                lastFilledPrice = order.getPrice();
                needToReconstructSellOrders = true;
            }else if (order.getSide().equals("Buy") && order.getOrdStatus() == OrderStatus.FILLED && order.getPrice().equals(lastFilledPrice)) {
                //sendOrdersByFibonachi();
            }else if (order.getOrdStatus() == OrderStatus.FILLED && order.getSide().equals("Sell")) {
                System.out.println(order.getPrice());
                System.out.println(startPrice);
                if (order.getPrice().equals(startPrice)) {
                    restartBot();
                    return;
                } else {
                    sellOrderQtySum += order.getOrderQty();
                    needToReconstructBuyOrders = true;
                }
            }
        }
        if (needToReconstructSellOrders) {
            reconstructSellOrders(lastFilledPrice, orders.length);
        } else if (needToReconstructBuyOrders){
            resendBuyOrder(lastFilledPrice, sellOrderQtySum);
        }
    }

    public void reconstructSellOrders(Double lastFilledPrice, int count) {
        Optional<List<Order>> sellOrders = orderDao.findSellOpenOrders();
        List<Order> ordersToClose = sellOrders.get();
        for (Order orderToClose : ordersToClose) {
            bitmexClient.cancelOrderById(orderToClose.getOrderID());
        }
        sendOrdersByFibonachi(lastFilledPrice, ordersToClose.size() + count, "Sell");
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

    public void restartBot(){
        System.out.println("Restart bot");
        bitmexClient.cancelAllOrders();
        stop();
        start();
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
