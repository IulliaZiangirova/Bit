package bitmexbot.service;

import bitmexbot.model.AuthenticationHeaders;
import bitmexbot.model.Order;
import bitmexbot.util.AuthenticationHeadersCreator;
import bitmexbot.util.HttpRequestCreator;
import bitmexbot.util.JsonCreator;
import java.net.URI;
import java.net.http.HttpRequest;

public class CancelOrderRequest extends BasicOrderRequest {


    private final String HTTP_METHOD_DELETE = "DELETE";
    private AuthenticationHeaders authenticationHeaders;
    private String orderId;
    private final String apiSecretKey;
    private final String apiKey;
    private JsonCreator jsonCreator = new JsonCreator();


    public CancelOrderRequest(String orderId, String baseUrl, String apiSecretKey, String apiKey) {
        this.apiSecretKey = apiSecretKey;
        this.apiKey = apiKey;
        this.orderId = orderId;
        createHttpRequest(baseUrl);
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    private String getData() {
        if (orderId == null){
            return "";
        }else {
        Order order = Order.builder()
                .orderID(orderId)
                .build();
        return jsonCreator.toJson(order);}
    }

    private AuthenticationHeaders getAuthenticationHeaders(){
        AuthenticationHeadersCreator createAuthenticationHeaders = new AuthenticationHeadersCreator();
        authenticationHeaders = createAuthenticationHeaders.getAuthenticationHeaders(HTTP_METHOD_DELETE, getData(), PATH, apiSecretKey, apiKey);
        return authenticationHeaders;
    }


    @Override
     void createHttpRequest(String baseUrl) {
        HttpRequest.BodyPublisher bodyPublishers = HttpRequest.BodyPublishers.ofString(getData());
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
        httpRequest = httpRequestCreator.getHttpRequest(baseUrl, getAuthenticationHeaders(),ENDPOINT_ORDER, HTTP_METHOD_DELETE, bodyPublishers );
    }
}
