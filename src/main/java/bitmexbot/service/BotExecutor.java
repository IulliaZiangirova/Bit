package bitmexbot.service;

import bitmexbot.client.BitmexWebSocketClient;
import bitmexbot.model.Bot;

public class BotExecutor {

    private final String apiKey = "GfibqQZKf1KvKJJ4BwK63-QJ";
    private final String apiSecret = "i_dE1FKK42t64fB7qMLcaYL0xfe3yqaR2LqouYqf-HM02QCP";

    private Bot bot;
    private BitmexClient bitmexClient = BitmexClientFactory.newTestnetBitmexClient(apiKey, apiSecret);
    private final BitmexWebSocketClient bitmexWebSocketClient = new BitmexWebSocketClient();

    public void start(){
        bitmexWebSocketClient.connect();
    }
    public void stop(){
        bitmexWebSocketClient.disconnect();
    }

    public void initBot(double step, int level, double coefficient){
        this.bot = new Bot(step, level, coefficient);
    }

    private void getSequenceFibonacci(){
        int size = bot.getLevel();
        int[] sequence = new int[size];
        sequence[0] = 1;
        sequence[1] = 1;
        sequence[2] = 1;
        for (int i = 3; i < size; i++) {
            sequence[i] = sequence[i - 1] + sequence[i - 2];
        }
        bot.setSequenceFibonacci(sequence);
    }
}
