package bitmexbot.service;

import bitmexbot.model.AuthenticationHeaders;
import bitmexbot.model.Order;
import bitmexbot.model.OrderRequest;
import bitmexbot.util.AuthenticationHeadersCreator;
import bitmexbot.util.JsonUtil;


import java.net.http.HttpRequest;

public class OpenOrderRequest extends BasicOrderRequest {

    private final String HTTP_METHOD_POST= "POST";
    private AuthenticationHeaders authenticationHeaders;
    private final Order order;
    private final String apiSecretKey;
    private final String apiKey;
    private JsonUtil jsonUtil = new JsonUtil();

    public OpenOrderRequest(Order order, String baseUrl, String apiSecretKey, String apiKey) {
        this.apiSecretKey = apiSecretKey;
        this.apiKey = apiKey;
        this.order = order;
        createHttpRequest(baseUrl);
    }

    private AuthenticationHeaders getAuthenticationHeaders(){
        AuthenticationHeadersCreator authenticationHeadersCreator = new AuthenticationHeadersCreator();
        authenticationHeaders = authenticationHeadersCreator.getAuthenticationHeaders(HTTP_METHOD_POST, getData(), PATH, apiSecretKey, apiKey);
    return authenticationHeaders;
    }

    private String getData() {
        OrderRequest orderRequest = OrderRequest.toRequest(order);
        return jsonUtil.toJson(orderRequest);
    }

    @Override
    void createHttpRequest(String baseUrl) {
        HttpRequest.BodyPublisher bodyPublishers = HttpRequest.BodyPublishers.ofString(getData());
        httpRequest = httpRequestCreator.getHttpRequest(baseUrl, getAuthenticationHeaders(),ENDPOINT_ORDER, HTTP_METHOD_POST, bodyPublishers );
    }


}
