package com.citizen.service.citizenservice.http;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.citizen.service.citizenservice.R;
import com.citizen.service.citizenservice.navigation.NavigationService;

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

    public HttpClient(Context context, String serverUrl) {
        this.context = context;
        this.serverUrl = serverUrl;
    }

    public void Register(String username, String password, String confirmPassword, ViewPager viewPager) {
        String registerUrl = String.format("%sapi/account/register", this.serverUrl);
        new RegisterAsync(context, viewPager, registerUrl).execute(username, password, confirmPassword);
    }

    public void Login(String username, String password, NavigationService navigationService) {
        String loginUrl = String.format("%s/token", this.serverUrl);
        new LoginAsync(context, navigationService, loginUrl).execute(username, password);
    }
}
