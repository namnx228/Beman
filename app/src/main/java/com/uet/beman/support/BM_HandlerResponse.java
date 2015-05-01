package com.uet.beman.support;

import android.os.SystemClock;

import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.object.SentenceNode;
import com.uet.beman.operator.BM_MessageHandler;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nam on 30/04/2015.
 */
public class BM_HandlerResponse extends Thread{
    @Override
    public void run()
    {
        String replyMessage = getReplyMessage(request);
        if (replyMessage == null) replyMessage = "Xin lỗi anh bận tí đã nhé, bye em.";
        int timeToWait = SharedPreferencesHelper.getInstance().getReplyWaitTime();
        Long timeToSend = System.currentTimeMillis() + timeToWait*1000;
        BM_MessageHandler.getInstance().sendReply(replyMessage, timeToSend.toString());
    }

    public BM_HandlerResponse(String request)
    {
        this.request = request;
    }

    private String request;
    public void setRequest(String request)
    {
        this.request = request;
    }

    private String getReplyMessage(String request)
    {
        try
        {
            URL url = new URL(request);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String line, lastLine = null;
            while((line = reader.readLine()) != null)
            {
                lastLine = line;
            }

            int pos = lastLine.lastIndexOf("<br>") + 4;
            JSONObject response = new JSONObject(lastLine.substring(pos));
            JSONObject mes = (JSONObject) response.get("message");
            String resultMessage = mes.get("message").toString();
            return resultMessage;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }
    }

}
