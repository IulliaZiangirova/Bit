package bitmexbot.service;

import bitmexbot.model.AuthenticationHeaders;
import bitmexbot.util.AuthenticationHeadersCreator;
import bitmexbot.util.HttpRequestCreator;


import java.net.http.HttpRequest;

public class GetOrderRequest extends BasicOrderRequest {


    private final String HTTP_METHOD_GET = "GET";
    private AuthenticationHeaders authenticationHeaders;
    private String data = "";
    private final String apiSecretKey;
    private final String apiKey;

    public GetOrderRequest(String baseUrl, String apiSecretKey, String apiKey) {
        this.apiSecretKey = apiSecretKey;
        this.apiKey = apiKey;
        createHttpRequest(baseUrl);
    }

    private AuthenticationHeaders getAuthenticationHeaders(){
        AuthenticationHeadersCreator createAuthenticationHeaders = new AuthenticationHeadersCreator();
        authenticationHeaders = createAuthenticationHeaders.getAuthenticationHeaders(HTTP_METHOD_GET, data, PATH, apiSecretKey, apiKey);
        return authenticationHeaders;
    }

    @Override
    void createHttpRequest(String baseUrl) {
        HttpRequest.BodyPublisher bodyPublishers = HttpRequest.BodyPublishers.noBody();
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
        httpRequest = httpRequestCreator.getHttpRequest(baseUrl, getAuthenticationHeaders(),ENDPOINT_ORDER, HTTP_METHOD_GET, bodyPublishers );
    }

    @Override
    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}
