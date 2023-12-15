package bitmexbot.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureCreator {

    public String getSignature (String apiSecret, String message) {
        byte[] keyBytes = apiSecret.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(keySpec);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        StringBuilder hexString = new StringBuilder();
        byte[] bytes = mac.doFinal(message.getBytes());
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    }

