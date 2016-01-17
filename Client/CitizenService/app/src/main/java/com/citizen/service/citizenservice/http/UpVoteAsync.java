package com.citizen.service.citizenservice.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpVoteAsync extends AsyncTask<String, Void, JSONObject> {

    private Context context;
    private String upVoteUrl;
    private String authorizationToken;

    public UpVoteAsync(Context context, String upVoteUrl, String authorizationToken)
    {
        this.context = context;
        this.upVoteUrl = upVoteUrl;
        this.authorizationToken = authorizationToken;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        HttpURLConnection urlConnection = null;

        try {

            URL url = new URL(this.upVoteUrl);
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

        StringBuilder response = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 512);
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JSONObject result = null;

        try {
            result = new JSONObject(response.toString());
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject result)
    {
        try {
            int votesCount = result.getInt("VotesCount");
            Log.d("VOTES_COUNT", String.valueOf(votesCount));
            // Update issue votes count
        } catch (JSONException ex) {

        }
    }
}
