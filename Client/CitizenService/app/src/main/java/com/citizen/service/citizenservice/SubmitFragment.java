package com.citizen.service.citizenservice;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.citizen.service.citizenservice.http.HttpClient;
import com.citizen.service.citizenservice.http.PublishIssue;
import com.citizen.service.citizenservice.http.PublishIssueData;
import com.citizen.service.citizenservice.storage.TokensDbHandler;

import java.util.ArrayList;

public class SubmitFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_submit, container, false);

        Button button = (Button) view.findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cityInput = (EditText)view.findViewById(R.id.submit_city);
                EditText titleInput = (EditText)view.findViewById(R.id.submit_title);
                EditText descriptionInput = (EditText) view.findViewById(R.id.submit_description);
                Switch isAnonymousInput = (Switch) view.findViewById(R.id.submit_is_anonymous);

                String city = cityInput.getText().toString();
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                boolean isAnonymous = isAnonymousInput.isChecked();

                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.submit_images);
                int imagesForUploadCount = linearLayout.getChildCount();

                ArrayList<String> imagesPaths = new ArrayList<String>();

                for(int i = 0; i < imagesForUploadCount; i++) {
                    ImageView imageForUpload = (ImageView) linearLayout.getChildAt(i);
                    String imagePath = (String)imageForUpload.getTag(R.integer.photo_path);
                    imagesPaths.add(imagePath);
                }

                PublishIssueData publishIssueData = new PublishIssueData(city, title, description, isAnonymous, imagesPaths);

                TokensDbHandler tokensDbHandler = new TokensDbHandler(getContext(), null);
                String url = getResources().getString(R.string.server_url);
                PublishIssue publishIssue = new PublishIssue(getContext(), url, tokensDbHandler.getToken("login"));
                publishIssue.execute(publishIssueData);
                /*
                String url = getResources().getString(R.string.server_url);
                HttpClient httpClient = new HttpClient(view.getContext(), url);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.submit_images);
                int imagesForUploadCount = linearLayout.getChildCount();

                for(int i = 0; i < imagesForUploadCount; i++) {
                    ImageView imageForUpload = (ImageView) linearLayout.getChildAt(i);
                    String filePath = (String)imageForUpload.getTag(R.integer.photo_path);
                    //httpClient.UploadImage(filePath);
                }
                */
            }
        });

        return  view;
    }
}
