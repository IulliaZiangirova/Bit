package bitmexbot.service;

import bitmexbot.util.AuthenticationHeadersCreator;
import bitmexbot.util.HttpRequestCreator;

import java.net.http.HttpRequest;

public abstract class BasicOrderRequest {

    protected final String ENDPOINT_ORDER = "/order";
    protected final String PATH = "/api/v1/order";
    protected HttpRequest httpRequest;
    protected final HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
    protected HttpRequest.BodyPublisher bodyPublishers;


    abstract void createHttpRequest(String baseUrl);

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}

