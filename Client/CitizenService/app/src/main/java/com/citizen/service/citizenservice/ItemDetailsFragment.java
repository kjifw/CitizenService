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
import com.citizen.service.citizenservice.http.HttpClient;
import com.citizen.service.citizenservice.models.IssueListItemModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemDetailsFragment extends Fragment {
    private TextView title;
    private TextView author;
    private TextView description;
    private TextView upVotes;
    private ImageView image;
    private TextView city;

    private int id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        TextView report = (TextView) view.findViewById(R.id.detailsButtonReport);
        report.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                HttpClient httpClient = new HttpClient(getContext(), getResources().getString(R.string.server_url));
                httpClient.ReportIncorrectIssue(id);
                Button voteButton = (Button) getActivity().findViewById(R.id.detailsButtonVote);
                voteButton.setVisibility(View.INVISIBLE);
                TextView report = (TextView) getActivity().findViewById(R.id.detailsButtonReport);
                report.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Reported as incorrect", Toast.LENGTH_SHORT).show();

                return false;
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
        city = (TextView) getActivity().findViewById(R.id.detailsCity);
        upVotes = (TextView) getActivity().findViewById(R.id.detailsVotesCount);

        IssueListItemModel model = null;
        if (args.getString("currentFragment") == "Issues") {
            model = ((InternalActivity) getActivity()).issuesList.get(args.getInt("currentItem"));
            setData(model);
        }

        if (args.getString("currentFragment") == "SearchResult"){
            model = ((InternalActivity) getActivity()).searchResultList.get(args.getInt("currentItem"));
            setData(model);
        }

        if (args.getString("currentFragment") == "MyIssues"){
            model = ((InternalActivity) getActivity()).myIssuesList.get(args.getInt("currentItem"));
            setData(model);
        }

        final int issueId = model.getId();
        id = issueId;
        final View detailsVotesCount = view.findViewById(R.id.detailsVotesCount);

        Button voteButton = (Button) getActivity().findViewById(R.id.detailsButtonVote);
        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverUrl = getResources().getString(R.string.server_url);
                HttpClient httpClient = new HttpClient(getContext(), serverUrl);
                httpClient.UpVote(detailsVotesCount, issueId);

                TextView vote = (TextView) getActivity().findViewById(R.id.detailsButtonVote);
                vote.setVisibility(View.INVISIBLE);
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
        city.setText(model.getCity());
        upVotes.setText("Votes count: " + model.getVotesCount());

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
