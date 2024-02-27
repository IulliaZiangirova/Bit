package bitmexbot.service;


import bitmexbot.dao.OrderDao;
import bitmexbot.model.*;
import bitmexbot.util.JsonUtil;
import lombok.ToString;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@ToString
public class BitmexClient {
    //send request to Bitmex

    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private OrderHttpRequest orderHttpRequest;
    private HttpRequest httpRequest;
    private final String baseUrl;
    private final String apiSecretKey;
    private final String apiKey;
    private final JsonUtil jsonUtil = new JsonUtil();
    private final OrderDao orderDao = new OrderDao();
    private final OrderHttpRequestFactory orderHttpRequestFactory;


    public BitmexClient(String baseUrl, String apiSecretKey, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiSecretKey = apiSecretKey;
        this.apiKey = apiKey;
        orderHttpRequestFactory = new OrderHttpRequestFactory(baseUrl, apiSecretKey, apiKey);
    }

//    public void getAllOrder()  {
//        orderHttpRequest = new GetOrderRequest(baseUrl, apiSecretKey, apiKey);
//        getResponse(orderHttpRequest);
//    }

    public void sendOrder(Order order)  {
       // basicOrderRequest = new OpenOrderRequest(order, baseUrl, apiSecretKey, apiKey);
        httpRequest = orderHttpRequestFactory.sendOrderRequest(order);
        getResponseWithOrder(httpRequest);
    }

    public void cancelOrderById(String orderId){
        httpRequest = orderHttpRequestFactory.cancelOrderRequest(orderId);
        getResponse(httpRequest);
    }

    public void cancelAllOrders(){
        //orderHttpRequest = new CancelOrderRequest(orderId, baseUrl, apiSecretKey, apiKey);
        httpRequest = orderHttpRequestFactory.cancelAllOrdersRequest();
        getResponse(httpRequest);
    }

    private void getResponse(HttpRequest httpRequest){
        try {
            HttpResponse<String> response  = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getResponseWithOrder(HttpRequest httpRequest){
        try {
            HttpResponse<String> response  = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Order newOrder = jsonUtil.fromJsonMy(response.body());
            orderDao.save(newOrder);
            System.out.println(newOrder);
            //System.out.println(response.body());
            System.out.println(response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
