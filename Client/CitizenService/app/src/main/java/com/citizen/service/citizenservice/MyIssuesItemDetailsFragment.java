package com.citizen.service.citizenservice;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.citizen.service.citizenservice.models.IssueListItemModel;

public class MyIssuesItemDetailsFragment extends Fragment {
    private TextView title;
    private TextView author;
    private TextView description;
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_issues_item_details, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args.getString("currentFragment") == "MyIssues") {
            IssueListItemModel model = ((InternalActivity) getActivity()).myIssuesList.get(args.getInt("currentItem"));

            title = (TextView) getActivity().findViewById(R.id.basicDetailsTitle);
            author = (TextView) getActivity().findViewById(R.id.basicDetailsAuthor);
            description = (TextView) getActivity().findViewById(R.id.basicDetailsDesciption);
            image = (ImageView) getActivity().findViewById(R.id.basicDetailsImageContainer);

            title.setText(model.getTitle());
            author.setText(model.getAuthor());
            description.setText(model.getDescription());
            //image.setImageURI(model.getImage());
        }
    }
}