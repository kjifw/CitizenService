package com.citizen.service.citizenservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.citizen.service.citizenservice.helpers.PicassoBuilder;
import com.citizen.service.citizenservice.models.IssueListItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemDetailsFragment extends Fragment {
    private TextView title;
    private TextView author;
    private TextView description;
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                Toast.makeText(getContext(), "Reported as incorrect", Toast.LENGTH_SHORT).show();
            }
        });

        TextView report = (TextView) view.findViewById(R.id.detailsButtonReport);
        report.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Button voteButton = (Button) getActivity().findViewById(R.id.detailsButtonVote);
                voteButton.setVisibility(View.INVISIBLE);
                return gestureDetector.onTouchEvent(event);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Bundle args = getArguments();
        title = (TextView) getActivity().findViewById(R.id.detailsTitle);
        author = (TextView) getActivity().findViewById(R.id.detailsAuthor);
        description = (TextView) getActivity().findViewById(R.id.detailsDescription);
        image = (ImageView) getActivity().findViewById(R.id.detailsImageContainer);

        if (args.getString("currentFragment") == "Issues") {
            IssueListItemModel model = ((InternalActivity) getActivity()).issuesList.get(args.getInt("currentItem"));
            setData(model);
        }

        if (args.getString("currentFragment") == "SearchResult"){
            IssueListItemModel model = ((InternalActivity) getActivity()).searchResultList.get(args.getInt("currentItem"));
            setData(model);
        }

        Button voteButton = (Button) getActivity().findViewById(R.id.detailsButtonVote);
        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView report = (TextView) getActivity().findViewById(R.id.detailsButtonReport);
                report.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Vote successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(IssueListItemModel model) {
        title.setText(model.getTitle());
        author.setText(model.getAuthor());
        description.setText(model.getDescription());

        final Picasso picasso = PicassoBuilder.getInstance(this.getContext());

        picasso.load(model.getImage()).fit().into(image);

        final ArrayList<String> imagesUrls = model.getImagesUrls();

        image.setOnClickListener(new View.OnClickListener() {
            int index = 1;

            @Override
            public void onClick(View v) {
                if (index >= imagesUrls.size()) {
                    index = 0;
                }
                picasso.load(imagesUrls.get(index)).fit().into(image);
                index++;
            }
        });
    }
}
