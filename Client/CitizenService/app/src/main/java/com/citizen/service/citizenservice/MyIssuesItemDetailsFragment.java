package com.citizen.service.citizenservice;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyIssuesItemDetailsFragment extends Fragment {
//    private TextView title;
//    private TextView author;
//    private TextView description;
//    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_issues_item_details, container, false);

//        title = (TextView) getActivity().findViewById(R.id.detailsTitle);
//        author = (TextView) getActivity().findViewById(R.id.detailsAuthor);
//        description = (TextView) getActivity().findViewById(R.id.detailsDesciption);
//        image = (ImageView) getActivity().findViewById(R.id.detailsImageContainer);

        return  view;
    }

//    public void setItemTitle(String title){
//        this.title.setText(title);
//    }
//
//    public void setItemAuthor(String author){
//        this.author.setText(author);
//    }
//
//    public void setItemDescription(String description){
//        this.description.setText(description);
//    }
//
//    public void setItemImage(String src){
//        this.image.setImageURI(Uri.parse(src));
//    }
}