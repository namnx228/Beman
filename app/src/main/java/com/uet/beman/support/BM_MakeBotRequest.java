package com.uet.beman.support;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class BM_MakeBotRequest {

    private static final String apiKey = "bWTNi96npipaiudF";
    private static final String apiSecret = "deBcd03etjpESp62ZE1l2mTNoMr8KcOZ";

    private static final String url = "http://www.personalityforge.com/api/chat/";
    private static final String botId = "124766";

    private static Mac sha256HMAC = null;

    public static String makeRequest(String message) {
        try {
            JSONObject messageJSON = createMessageJSON(message);
            String hash = encodeMessage(messageJSON.toString());
            String urlEncodedMessage = URLEncoder.encode(messageJSON.toString(), "UTF-8");

            String request = url + "?apiKey=" + apiKey + "&hash=" + hash + "&message=" + urlEncodedMessage;

            Log.d("request url", request);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String encodeMessage(String s) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        if (sha256HMAC == null)
            init();

        byte[] bytes = sha256HMAC.doFinal(s.getBytes("UTF-8"));
        String hash = "";
        for (byte b : bytes)
            hash += Integer.toString((b & 0xff) + 0x100, 16).substring(1);

        return hash;
    }

    private static void init() throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec secretKey = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
        sha256HMAC = Mac.getInstance("HmacSHA256");
        sha256HMAC.init(secretKey);
    }

    private static JSONObject createMessageJSON(String message) throws JSONException {
        JSONObject mess = new JSONObject();
        mess.accumulate("message", message);
        mess.accumulate("chatBotID", botId);
        mess.accumulate("timestamp", System.currentTimeMillis() / 1000);

        JSONObject user = new JSONObject();
        user.accumulate("externalID", "demo_girl_friend");

        JSONObject result = new JSONObject();
        result.accumulate("message", mess);
        result.accumulate("user", user);
        return result;
    }
}
