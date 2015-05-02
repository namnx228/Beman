package com.uet.beman.support;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BM_RequestTask extends AsyncTask<String, String, String> {

    DisplayResponse callback;

    public void setCallback(DisplayResponse _callback) {
        callback = _callback;
    }

    @Override
    protected String doInBackground(String... uri) {
        try {
            URL openURL = new URL(uri[0]);
            HttpURLConnection httpConn = (HttpURLConnection) openURL.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            InputStream is = openURL.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line, lastLine = null;
            while ((line = reader.readLine()) != null)
                lastLine = line;

            if (lastLine == null)
                return "Xin lỗi, anh có việc bận. Bye em";
            int pos = lastLine.lastIndexOf("<br>") + 4;

            org.json.JSONObject response = new org.json.JSONObject(lastLine.substring(pos));
            org.json.JSONObject message = (org.json.JSONObject) response.get("message");
            String res = (String) message.get("message");
            Log.d("response message", res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return "Xin lỗi, anh có việc bận. Bye em";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        callback.displayResponse(result);
    }

    public interface DisplayResponse {
        public void displayResponse(String response);
    }
}