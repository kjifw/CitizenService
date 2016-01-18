package com.citizen.service.citizenservice.http;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListAdapter;

import com.citizen.service.citizenservice.ListItemAdapter;
import com.citizen.service.citizenservice.contracts.IMyIssue;
import com.citizen.service.citizenservice.models.IssueListItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoadMyIssuesAsync extends AsyncTask<String, Void, JSONArray>{
    private String myIssuesUrl;
    private Context context;
    private IMyIssue myIssues;
    private String authorizationToken;
    private ListItemAdapter adapter;
    private String serverUrl;

    public LoadMyIssuesAsync(Context context, IMyIssue myIssues, String authorizationToken,
                             String myIssuesUrl, String serverUrl, ListItemAdapter adapter) {
        this.myIssuesUrl = myIssuesUrl;
        this.context = context;
        this.myIssues = myIssues;
        this.authorizationToken = authorizationToken;
        this.adapter = adapter;
        this.serverUrl = serverUrl;
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        JSONArray listOfIssues = null;
        HttpURLConnection urlConnection = null;

        try{
            URL url = new URL(this.myIssuesUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Host", "localhost");
            urlConnection.setRequestProperty("Authorization", "Bearer " + this.authorizationToken);
            urlConnection.connect();

        }  catch (Exception e){

        }
        StringBuilder response = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 512);
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException ex) {

        }

        try {
            listOfIssues = new JSONArray(response.toString());
        } catch (JSONException e) {

        }

        return listOfIssues;
    }


    @Override
    protected void onPostExecute(JSONArray listOfIssues) {
        List<IssueListItemModel> issuesReadyForListing = new ArrayList<IssueListItemModel>();

        for(int i = 0; i < listOfIssues.length(); i++) {
            try {
                JSONObject issue = listOfIssues.getJSONObject(i);

                String imageUrl = issue.getString("ImageUrl").replace("http://localhost/", this.serverUrl);

                IssueListItemModel issueForListing = new IssueListItemModel(imageUrl, issue.getString("Title"),
                        String.valueOf((Integer) issue.getInt("UpVotesCount")), issue.getString("Description"),
                        issue.getString("Author"), issue.getString("City"), issue.getString("IsAnonymous"));

                JSONArray issueImagesUrls = issue.getJSONArray("ImagesUrls");

                for(int j = 0; j < issueImagesUrls.length(); j++) {
                    JSONObject entry = issueImagesUrls.getJSONObject(j);
                    issueForListing.addImageUrl(entry.getString("Url").replace("http://localhost/", this.serverUrl));
                }

                issuesReadyForListing.add(issueForListing);
            } catch (JSONException ex){

            }
        }

        this.myIssues.setMyIssueData(issuesReadyForListing);
        this.adapter.notifyDataSetChanged();
    }
}