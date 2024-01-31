package bitmexbot.service;

import bitmexbot.client.BitmexWebSocketClient;
import bitmexbot.model.*;
import bitmexbot.util.JsonCreator;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BotExecutor {

    private final String apiKey = "GfibqQZKf1KvKJJ4BwK63-QJ";
    private final String apiSecret = "i_dE1FKK42t64fB7qMLcaYL0xfe3yqaR2LqouYqf-HM02QCP";
    private Double startPrice;
    private JsonCreator jsonCreator = new JsonCreator();
    private Bot bot;
    private BitmexClient bitmexClient = BitmexClientFactory.newTestnetBitmexClient(apiKey, apiSecret);
    private final BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient();

    public void start(){
        bitmexWebSocketClient.connect();
        bitmexWebSocketClient.setBotExecutor(this);
        initBot(100, 2, 100);
        bot.setSequenceFibonacci(initSequenceFibonacci(6));
        //logic();
    }

    public void logic(){
        Double price = startPrice;
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

    public void stop(){
        bitmexWebSocketClient.disconnect();
    }

    private void initBot(double step, int level, double coefficient){
        this.bot = new Bot(step, level, coefficient);
    }

    public Double setStartPriceFromWebsocket(String message){
        return jsonCreator.parsStartPrice(message);
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
