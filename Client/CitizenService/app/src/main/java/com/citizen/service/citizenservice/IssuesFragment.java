package com.citizen.service.citizenservice;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.citizen.service.citizenservice.contracts.ITopVotedResult;
import com.citizen.service.citizenservice.http.HttpClient;
import com.citizen.service.citizenservice.models.IssueListItemModel;

import java.util.List;

public class IssuesFragment extends ListFragment {
    ActivityManager manager;

    private final static int NUMBER_OF_ISSUES_TO_LOAD = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issues, container, false);
        return  view;
    }

    private boolean areIssuesLoaded = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(areIssuesLoaded == false) {
            List<IssueListItemModel> list = ((InternalActivity) getActivity()).issuesList;
            list.clear();
            ListAdapter adapter = new ListItemAdapter(getActivity(), list);
            setListAdapter(adapter);

            areIssuesLoaded = true;
            HttpClient httpClient = new HttpClient(getContext(), getResources().getString(R.string.server_url));
            httpClient.LoadTopVotedIssues((ITopVotedResult) getActivity(), NUMBER_OF_ISSUES_TO_LOAD);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(getActivity(), "item clicked " + position, Toast.LENGTH_SHORT).show();

        ((InternalActivity) getActivity()).setDetailsInformation(position, 4);
        ViewPager pager = ((InternalActivity) getActivity()).viewPager;
        pager.setCurrentItem(6);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        manager = (ActivityManager) context;
    }
}
