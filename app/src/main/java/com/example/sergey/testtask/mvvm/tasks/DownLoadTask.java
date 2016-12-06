package com.example.sergey.testtask.mvvm.tasks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Sergey Rodionov
 */

public class DownLoadTask extends AsyncTaskLoader<String> {

    public static final String KEY = "key";
    private String mUrl;

    public DownLoadTask(Context context, Bundle args) {
        super(context);
        if (args != null)
            mUrl = args.getString(KEY);
    }

    @Override
    public String loadInBackground() {
        try {
            return getXml(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
      //  MyApplication.showDialog(true);
    }

    public String getXml(String urlString) throws IOException {

        InputStream is = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            int response = urlConnection.getResponseCode();
            Log.d("TAG", "The response is: " + response);
            is = urlConnection.getInputStream();

            return readStream(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String readStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "windows-1251"));
        StringBuilder buffer = new StringBuilder();
        String response;
        while ((response = reader.readLine()) != null) {
            buffer.append(response);
        }
        return (buffer.toString());
    }
}
