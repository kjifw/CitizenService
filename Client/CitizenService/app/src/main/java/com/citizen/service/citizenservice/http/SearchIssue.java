package com.citizen.service.citizenservice.http;

import android.content.Context;
import android.os.AsyncTask;

import com.citizen.service.citizenservice.contracts.ISearchResult;
import com.citizen.service.citizenservice.models.IssueListItemModel;

import java.util.List;

public class SearchIssue extends AsyncTask<SearchIssueData, Void, List<IssueListItemModel>> {
    private Context context;
    private String serverUrl;
    private String authorizationToken;
    private ISearchResult searchResult = null;

    public SearchIssue(Context context, String serverUrl, String authorizationToken) {
        this.context = context;
        this.serverUrl = serverUrl;
        this.authorizationToken = authorizationToken;
    }

    @Override
    protected List<IssueListItemModel> doInBackground(SearchIssueData... params) {
        return null;
    }

    @Override
    protected void onPostExecute(List<IssueListItemModel> models) {
        searchResult.setResultData(models);
    }
}
