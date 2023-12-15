package bitmexbot.service;

import bitmexbot.util.Endpoints;

public class BitmexClientFactory {

    public static BitmexClient newTestnetBitmexClient(String apiKey, String apiSecret) {
        return new BitmexClient(Endpoints.BASE_TEST_URL,  apiSecret, apiKey );
    }

//    public BitmexClient newRealBitmexClient(String apiKey) {
//        return new BitmexClient(Endpoints.BASE_REAL_URL, true, apiKey, apiKey);
//    }
}
