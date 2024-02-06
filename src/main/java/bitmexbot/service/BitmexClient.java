package bitmexbot.service;


import bitmexbot.dao.OrderDao;
import bitmexbot.model.*;
import bitmexbot.repository.OrderRepository;
import bitmexbot.util.JsonUtil;

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
    private final JsonUtil jsonUtil = new JsonUtil();
    private OrderRepository orderRepository = new OrderRepository();
    private OrderDao orderDao = new OrderDao();


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
        getResponseWithOrder(httpRequest);
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

    private void getResponseWithOrder(BasicOrderRequest httpRequest){
        try {
            HttpResponse<String> response  = httpClient.send(httpRequest.getHttpRequest(), HttpResponse.BodyHandlers.ofString());
            Order newOrder = jsonUtil.fromJsonMy(response.body());
            orderDao.save(newOrder);
            System.out.println(newOrder);
            System.out.println(response.body());
            System.out.println(response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
