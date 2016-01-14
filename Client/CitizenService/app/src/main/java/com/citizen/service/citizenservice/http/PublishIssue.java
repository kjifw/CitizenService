package com.citizen.service.citizenservice.http;

import android.content.Context;
import android.os.AsyncTask;

import com.citizen.service.citizenservice.storage.TokensDbHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class PublishIssue extends AsyncTask<PublishIssueData, Void, Integer> {

    private Context context;
    private String serverUrl;
    private String authorizationToken;

    public PublishIssue(Context context, String serverUrl, String authorizationToken) {
        this.context = context;
        this.serverUrl = serverUrl;
        this.authorizationToken = authorizationToken;
    }

    @Override
    protected Integer doInBackground(PublishIssueData... params) {
        try {
            PublishIssueData publishIssueData = params[0];
            JSONObject publishIssueDataObject = new JSONObject();
            publishIssueDataObject.put("City", publishIssueData.getCity());
            publishIssueDataObject.put("Title", publishIssueData.getTitle());
            publishIssueDataObject.put("Description", publishIssueData.getDescription());
            publishIssueDataObject.put("IsAnonymous", publishIssueData.getIsAnonymous());
            byte[] postData = publishIssueDataObject.toString().getBytes(Charset.forName("UTF-8"));
            int postDataLength = postData.length;

            String publishIssueUrl = String.format("%sapi/issues", this.serverUrl);
            URL url = new URL(publishIssueUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + this.authorizationToken);
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

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                JSONObject json = new JSONObject(response.toString());
                int issueId = (Integer) json.get("Id");

                ArrayList<String> imagesPaths = publishIssueData.getImagesPaths();
                if(imagesPaths.size() > 0) {
                    String uploadImageUrl = String.format("%sapi/images", this.serverUrl);
                    HttpClient httpClient = new HttpClient(this.context, uploadImageUrl);

                    for (int i = 0; i < imagesPaths.size(); i++) {
                        httpClient.UploadImage(imagesPaths.get(i), issueId);
                    }
                }

                return 0;
            }

            return -1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
