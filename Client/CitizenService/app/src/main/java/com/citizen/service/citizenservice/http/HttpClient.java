package com.citizen.service.citizenservice.http;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.citizen.service.citizenservice.contracts.IMyIssue;
import com.citizen.service.citizenservice.contracts.ISearchIssue;
import com.citizen.service.citizenservice.contracts.ITopVotedResult;
import com.citizen.service.citizenservice.navigation.NavigationService;
import com.citizen.service.citizenservice.storage.TokensDbHandler;

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

    public void UploadImage(String filePath, int issueId) {

        TokensDbHandler tokensDbHandler = new TokensDbHandler(this.context, null);
        StringBuilder url = new StringBuilder();
        url.append(this.serverUrl);
        url.append("/");
        url.append(String.valueOf(issueId));

        ImageUpload imageUpload = new ImageUpload(url, "http", filePath, tokensDbHandler.getToken("login"));
        imageUpload.upload();
    }

    public void LoadTopVotedIssues(ITopVotedResult topVotedResult, int count) {
        TokensDbHandler tokensDbHandler = new TokensDbHandler(this.context, null);
        String loadTopVotedIssuesUrl = String.format("%sapi/issues/sortedbyvotes/%d", this.serverUrl, count);
        new LoadTopVotedIssuesAsync(this.context, topVotedResult, tokensDbHandler.getToken("login"), loadTopVotedIssuesUrl).execute();
    }

    public void LoadMyIssues(IMyIssue myIssues, int count) {
        TokensDbHandler tokensDbHandler = new TokensDbHandler(this.context, null);
        String loadMyIssuesUrl = String.format("%sapi/", this.serverUrl, count);
        new LoadMyIssuesAsync(this.context, myIssues, tokensDbHandler.getToken("login"), loadMyIssuesUrl).execute();
    }

    public void LoadSearchIssues(ISearchIssue issues, int count){
        TokensDbHandler tokensDbHandler = new TokensDbHandler(this.context, null);
        String loadIssuesUrl = String.format("%sapi/", this.serverUrl, count);
        //new SearchIssue(this.context, serverUrl, tokensDbHandler.getToken("login"), loadIssuesUrl).execute();
    }
}
