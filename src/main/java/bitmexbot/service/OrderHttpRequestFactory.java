package bitmexbot.service;

import bitmexbot.model.AuthenticationHeaders;
import bitmexbot.model.Order;
import bitmexbot.model.OrderRequest;
import bitmexbot.util.AuthenticationHeadersCreator;
import bitmexbot.util.Endpoints;
import bitmexbot.util.HttpRequestCreator;
import bitmexbot.util.JsonUtil;

import java.net.http.HttpRequest;

public class OrderHttpRequestFactory {


    private String httpMethod;
    private final String apiSecretKey;
    private final String apiKey;
    private final String baseUrl;
    private static final JsonUtil jsonUtil = new JsonUtil();

    public OrderHttpRequestFactory(String baseUrl, String apiSecretKey, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiSecretKey = apiSecretKey;
        this.apiKey = apiKey;
    }

    public HttpRequest sendOrderRequest(Order order){
        httpMethod = "POST";
        return createHttpRequest(order, httpMethod);
    }

    public HttpRequest cancelOrderRequest(String orderId){
        httpMethod = "DELETE";
        Order order = Order.builder()
                .orderID(orderId)
                .build();
        return createHttpRequest(order, httpMethod);
    }


    private HttpRequest createHttpRequest(Order order, String httpMethod) {
        HttpRequest.BodyPublisher bodyPublishers = HttpRequest.BodyPublishers.ofString(getData(order));
        return HttpRequestCreator.getHttpRequest(baseUrl, getAuthenticationHeaders(order, httpMethod), Endpoints.ORDER_ENDPOINT, httpMethod, bodyPublishers );
    }

    private String getData(String orderId) {
        if (orderId == null){
            return "";
        }else {
            Order order = Order.builder()
                    .orderID(orderId)
                    .build();
            return jsonUtil.toJson(order);}
    }

    private String getData(Order order) {
        OrderRequest orderRequest = OrderRequest.toRequest(order);
        return jsonUtil.toJsonForRequest(orderRequest);
    }

    private AuthenticationHeaders getAuthenticationHeaders(Order order, String httpMethod){
        AuthenticationHeadersCreator authenticationHeadersCreator = new AuthenticationHeadersCreator();
        return authenticationHeadersCreator.getAuthenticationHeaders(httpMethod, getData(order), Endpoints.PATH_FOR_REQUEST, apiSecretKey, apiKey);
    }



}
