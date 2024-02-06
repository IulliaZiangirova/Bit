package bitmexbot.service;

import bitmexbot.client.BitmexWebSocketClient;
import bitmexbot.dao.OrderDao;
import bitmexbot.model.*;
import bitmexbot.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
public class BotExecutor {

    private final String apiKey = "GfibqQZKf1KvKJJ4BwK63-QJ";
    private final String apiSecret = "i_dE1FKK42t64fB7qMLcaYL0xfe3yqaR2LqouYqf-HM02QCP";
    private Double startPrice;
    private final JsonUtil jsonUtil = new JsonUtil();
    private Bot bot;
    private BitmexClient bitmexClient = BitmexClientFactory.newTestnetBitmexClient(apiKey, apiSecret);
    private final BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient();
    private OrderDao orderDao = new OrderDao();

    public void start(){
        bitmexWebSocketClient.connect();
        bitmexWebSocketClient.setBotExecutor(this);
        initBot(100, 1, 100);
        bot.setSequenceFibonacci(initSequenceFibonacci(6));
    }

    public void logic(){
        Double price = startPrice - bot.getStep();
        for (int i = 0; i < bot.getLevel(); i++) {
            Order order = Order.builder()
                    .orderQty(bot.getSequenceFibonacci()[i] * bot.getCoefficient() )
                    .ordType(OrderType.LMT)
                    .side("Buy")
                    .symbol(Symbol.XBTUSD)
                    .price(price)
                    .build();
            bitmexClient.sendOrder(order);
            price = price - bot.getStep() * bot.getSequenceFibonacci()[i];
        }
    }

    public void parsData(String message){
        if (message.contains("\"table\":\"order\",\"action\":\"update\"")){
            Order updatedOrder = jsonUtil.parserOrder(message);
            orderDao.merge(updatedOrder);
        }
        if (message.contains("\"table\":\"trade\"")){
            Double price = jsonUtil.parsStartPrice(message);
            this.startPrice = price;
            log.info("Start price: " + price);
        }
    }

    public void checkOrderForUpdating(Order order) {
        System.out.println(order);
        if(!order.isWorkingIndicator() && order.getOrdStatus() == OrderStatus.CANCELED){
            orderDao.merge(order);
        } else if (!order.isWorkingIndicator() && order.getOrdStatus() == OrderStatus.FILLED){
            orderDao.merge1(order);
        };

    }

    public void checkOrders(Order [] orders) {
        for (Order order : orders) {
            System.out.println(order);
            if(!order.isWorkingIndicator() && order.getOrdStatus() == OrderStatus.CANCELED){
                orderDao.merge(order);
            } else if (!order.isWorkingIndicator() && order.getOrdStatus() == OrderStatus.FILLED){
                orderDao.merge1(order);
            };

            }
        }


    public void stop(){
        bitmexWebSocketClient.disconnect();
    }

    private void initBot(double step, int level, double coefficient){
        this.bot = new Bot(step, level, coefficient);
    }

    public Double setStartPriceFromWebsocket(String message){
        return jsonUtil.parsStartPrice(message);
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
