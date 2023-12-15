package bitmexbot.service;

import java.net.http.HttpRequest;

public abstract class BasicOrderRequest {

    protected final String ENDPOINT_ORDER = "/order";
    protected final String PATH = "/api/v1/order";
    protected HttpRequest httpRequest;

    abstract void createHttpRequest(String baseUrl);
    abstract public HttpRequest getHttpRequest();
}
