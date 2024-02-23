package bitmexbot.service;

import bitmexbot.util.HttpRequestCreator;
import lombok.Data;
import lombok.Getter;

import java.net.http.HttpRequest;

@Data
@Getter
public class OrderHttpRequest {

    protected final String ENDPOINT_ORDER = "/order";//
    protected final String PATH = "/api/v1/order";//
    protected HttpRequest httpRequest;
    protected final HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
    protected HttpRequest.BodyPublisher bodyPublishers;//


     void createHttpRequest(String baseUrl){};

    public OrderHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }


}

