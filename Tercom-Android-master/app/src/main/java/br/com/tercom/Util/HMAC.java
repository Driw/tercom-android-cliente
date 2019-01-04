package br.com.tercom.Util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static br.com.tercom.Util.Util.print;

public class HMAC {

    private final static String HASH = "ABC";

    public static String encrypt(String message) {
        try {
            final String hashingAlgorithm = "HmacSHA256";

            byte[] bytes = hmac(hashingAlgorithm, HASH.getBytes(), message.getBytes());

            final String messageDigest = bytesToHex(bytes);

            print(messageDigest);
            return messageDigest;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}

