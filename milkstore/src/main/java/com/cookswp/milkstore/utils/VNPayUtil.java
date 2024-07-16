package com.cookswp.milkstore.utils;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class VNPayUtil {

    private static final Map<Integer, String> paymentMap = new HashMap<>();

    public static void addTxnRef(int userID, String txnRef){
        paymentMap.put(userID, txnRef);
    }

    public String getTxnRef(int userId) {
        return paymentMap.get(userId);
    }

    public boolean hasPaid(int userId) {
        String txnRef = paymentMap.get(userId);
        return txnRef != null && !txnRef.isEmpty();
    }

    public static String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    public static String convertPayDate(String payDate) {
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = originalFormat.parse(payDate);

            DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
            return targetFormat.format(date);
        } catch (Exception e) {
            System.out.println("Error at convertPayDate");
            return null;
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }


    public static String getPaymentURL(Map<String, String> paramsMap, boolean encodeKey) {
        return paramsMap.entrySet().stream()
                .filter(entry -> isNotEmpty(entry.getValue()))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> buildQueryParam(entry, encodeKey))
                .collect(Collectors.joining("&"));
    }

    //Check if the value if null or empty
    private static boolean isNotEmpty(String value){
        return value != null && !value.isEmpty();
    }

    //Create key and value mapping each other then code it
    private static String buildQueryParam(Map.Entry<String, String> entry, boolean encodeKey){
        String key = encodeKey ? encode(entry.getKey()) : entry.getKey();
        String value = encode(entry.getValue());
        return key + "=" + value;
    }

    //Encode a string
    private static String encode(String value){
        return URLEncoder.encode(value, StandardCharsets.US_ASCII);
    }
}
