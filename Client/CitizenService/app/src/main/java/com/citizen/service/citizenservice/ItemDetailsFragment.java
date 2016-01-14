package com.citizen.service.citizenservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.citizen.service.citizenservice.models.IssueListItemModel;

public class ItemDetailsFragment extends Fragment {
    private TextView title;
    private TextView author;
    private TextView description;
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle args = getArguments();
        title = (TextView) getActivity().findViewById(R.id.detailsTitle);
        author = (TextView) getActivity().findViewById(R.id.detailsAuthor);
        description = (TextView) getActivity().findViewById(R.id.detailsDesciption);
        image = (ImageView) getActivity().findViewById(R.id.detailsImageContainer);
        if (args.getString("currentFragment") == "Issues") {
            IssueListItemModel model = ((InternalActivity) getActivity()).issuesList.get(args.getInt("currentItem"));
            setData(model);
        }

        if (args.getString("currentFragment") == "SearchResult"){
            IssueListItemModel model = ((InternalActivity) getActivity()).searchResultList.get(args.getInt("currentItem"));
            setData(model);
        }
    }

    private void setData(IssueListItemModel model) {
        title.setText(model.getTitle());
        author.setText(model.getAuthor());
        description.setText(model.getDescription());
        image.setImageURI(model.getImage());
    }
}
