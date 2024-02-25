package bitmexbot.service;

import bitmexbot.model.AuthenticationHeaders;
import bitmexbot.model.Order;
import bitmexbot.util.AuthenticationHeadersCreator;
import bitmexbot.util.JsonUtil;

import java.net.http.HttpRequest;

public class CancelOrderRequest extends OrderHttpRequest {
    public CancelOrderRequest(HttpRequest httpRequest) {
        super(httpRequest);
    }

//
//    private final String HTTP_METHOD_DELETE = "DELETE";
//    private AuthenticationHeaders authenticationHeaders;
//    private String orderId;
//    private final String apiSecretKey;
//    private final String apiKey;
//    private JsonUtil jsonUtil = new JsonUtil();
//
//
//    public CancelOrderRequest(String orderId, String baseUrl, String apiSecretKey, String apiKey) {
//        this.apiSecretKey = apiSecretKey;
//        this.apiKey = apiKey;
//        this.orderId = orderId;
//        bodyPublishers = HttpRequest.BodyPublishers.ofString(getData());
//        createHttpRequest(baseUrl);
//    }
//
//
//    private String getData() {
//        if (orderId == null){
//            return "";
//        }else {
//        Order order = Order.builder()
//                .orderID(orderId)
//                .build();
//        return jsonUtil.toJson(order);}
//    }
//
//    private AuthenticationHeaders getAuthenticationHeaders(){
//        AuthenticationHeadersCreator authenticationHeadersCreator = new AuthenticationHeadersCreator();
//        authenticationHeaders = authenticationHeadersCreator.getAuthenticationHeaders(HTTP_METHOD_DELETE, getData(), PATH, apiSecretKey, apiKey);
//        return authenticationHeaders;
//    }
//
//
//    @Override
//     void createHttpRequest(String baseUrl) {
//        httpRequest = httpRequestCreator.getHttpRequest(baseUrl, getAuthenticationHeaders(),ENDPOINT_ORDER, HTTP_METHOD_DELETE, bodyPublishers );
//    }
}
