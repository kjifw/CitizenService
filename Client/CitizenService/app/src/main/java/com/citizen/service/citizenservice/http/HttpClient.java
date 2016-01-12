package com.citizen.service.citizenservice.http;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.citizen.service.citizenservice.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HttpClient {

    private String serverUrl;
    private Context context;
    private ViewPager viewPager;

    public HttpClient(Context context, ViewPager viewPager, String serverUrl) {
        this.context = context;
        this.viewPager = viewPager;
        this.serverUrl = serverUrl;
    }

    public boolean Register(String username, String password) {
        String registerUrl = String.format("%sapi/account/register", this.serverUrl);
        new RegisterAsync(context, viewPager, registerUrl).execute(username, password);
        return true;
    }

    class RegisterAsync extends AsyncTask<String, Void, Boolean> {

        private String registerUrl;
        private Context context;
        private ViewPager viewPager;

        public RegisterAsync(Context context, ViewPager viewPager, String registerUrl) {
            this.context = context;
            this.viewPager = viewPager;
            this.registerUrl = registerUrl;
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                String urlParameters = String.format("Email=%s&Password=%s&ConfirmPassword=%s", params[0], params[1], params[1]);
                byte[] postData = urlParameters.getBytes(Charset.forName("UTF-8"));
                int postDataLength = postData.length;

                URL url = new URL(this.registerUrl);
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

                Log.d("RESPONSE CODE", urlConnection.getResponseMessage());

                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
                Toast.makeText(this.context, "Successfully registered", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(0);
            } else {
                Toast.makeText(this.context, "Failed registration",  Toast.LENGTH_SHORT).show();
            }
        }
    }
}
