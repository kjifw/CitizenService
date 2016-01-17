package com.citizen.service.citizenservice.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReportAsync extends AsyncTask<String, Void, Void> {

    Context context;
    String reportUrl;
    String authorizationToken;

    public ReportAsync(Context context, String reportUrl, String authorizationToken) {
        this.context = context;
        this.reportUrl = reportUrl;
    }

    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection urlConnection = null;

        try {

            URL url = new URL(this.reportUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Host", "localhost");
            urlConnection.setRequestProperty("Authorization", "Bearer " + this.authorizationToken);
            urlConnection.connect();

            Log.d("Response code", String.valueOf(urlConnection.getResponseCode()));

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // Remove report as incorrect
    }
}
