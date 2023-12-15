package bitmexbot.model;

import lombok.Data;

@Data
public class AuthenticationHeaders {

    private String expires;
    private String apiKey;
    private String signature;

    public AuthenticationHeaders(String expires, String apiKey, String signature) {
        this.expires = expires;
        this.apiKey = apiKey;
        this.signature = signature;
    }

    public String getExpires() {
        return expires;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSignature() {
        return signature;
    }
}
