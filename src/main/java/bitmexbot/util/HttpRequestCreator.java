package bitmexbot.util;

import bitmexbot.model.AuthenticationHeaders;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestCreator {


    public static HttpRequest getHttpRequest(String baseUrl, AuthenticationHeaders authenticationHeaders, String endpoint, String method,HttpRequest.BodyPublisher bodyPublishers){

       HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .method(method, bodyPublishers)
                .header("api-key", authenticationHeaders.getApiKey())
                .headers("api-signature", authenticationHeaders.getSignature())
                .header("api-expires", authenticationHeaders.getExpires())
                .header("Content-Type", "application/json")
                .build();
       return httpRequest;
    }
}
