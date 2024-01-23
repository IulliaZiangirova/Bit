package bitmexbot.service;


import bitmexbot.model.*;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;


public class BitmexClient {
    //send request to Bitmex

    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private BasicOrderRequest httpRequest;
    private final String baseUrl;
    private final String apiSecretKey;
    private final String apiKey;


    public BitmexClient(String baseUrl, String apiSecretKey, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiSecretKey = apiSecretKey;
        this.apiKey = apiKey;
    }

    public void getAllOrder()  {
        httpRequest = new GetOrderRequest( baseUrl, apiSecretKey, apiKey);
        getResponse(httpRequest);
    }

    public void sendOrder(Order order)  {
        httpRequest = new OpenOrderRequest(order, baseUrl, apiSecretKey, apiKey);
        getResponse(httpRequest);
    }

    public void cancelOrderById(String orderId){
        httpRequest = new CancelOrderRequest(orderId, baseUrl, apiSecretKey, apiKey);
        getResponse(httpRequest);
    }

    public void cancelAllOrders(String orderId){
        httpRequest = new CancelOrderRequest(orderId, baseUrl, apiSecretKey, apiKey);
        getResponse(httpRequest);
    }

    private void getResponse(BasicOrderRequest httpRequest){
        try {
            HttpResponse<String> response  = httpClient.send(httpRequest.getHttpRequest(), HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            System.out.println(response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
