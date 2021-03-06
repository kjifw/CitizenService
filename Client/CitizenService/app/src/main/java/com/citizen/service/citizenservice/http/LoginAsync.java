package com.citizen.service.citizenservice.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.citizen.service.citizenservice.navigation.NavigationService;
import com.citizen.service.citizenservice.storage.TokensDbHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class LoginAsync extends AsyncTask<String, Void, Boolean> {

    private Context context;
    private NavigationService navigationService;
    private String loginUrl;

    public LoginAsync(Context context, NavigationService navigationService, String loginUrl) {
        this.context = context;
        this.navigationService = navigationService;
        this.loginUrl = loginUrl;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String urlParameters = String.format("Username=%s&Password=%s&grant_type=password", params[0], params[1]);
            byte[] postData = urlParameters.getBytes(Charset.forName("UTF-8"));
            int postDataLength = postData.length;

            URL url = new URL(this.loginUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Charset", "utf-8");
            urlConnection.setRequestProperty("Host", "localhost");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.write(postData);
            outputStream.flush();
            outputStream.close();
            urlConnection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 512);
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JSONObject json = new JSONObject(response.toString());
                TokensDbHandler db = new TokensDbHandler(this.context, null);
                db.addToken(json.getString("access_token"), "login");
                return true;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result.booleanValue() == true) {
            Toast.makeText(this.context, "Successful login", Toast.LENGTH_SHORT).show();
            this.navigationService.goToInternalActivity();
        } else {
            Toast.makeText(this.context, "Failed login",  Toast.LENGTH_SHORT).show();
        }
    }
}
