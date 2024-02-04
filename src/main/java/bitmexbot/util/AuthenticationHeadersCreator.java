package bitmexbot.util;

import bitmexbot.model.AuthenticationHeaders;

public class AuthenticationHeadersCreator {

    private final int EXPIRES_DELAY = 10;
    private final SignatureCreator signature =  new SignatureCreator();

    public AuthenticationHeaders getAuthenticationHeaders(String httpMethod, String data, String path, String apiSecretKey, String apiKey)  {
        long expires = System.currentTimeMillis() / 1000 + EXPIRES_DELAY;
        String message = httpMethod + path + String.valueOf(expires) + data;
        String signatureStr = signature.getSignature(apiSecretKey, message);
        return  new AuthenticationHeaders(String.valueOf(expires), apiKey, signatureStr);
    }
}
