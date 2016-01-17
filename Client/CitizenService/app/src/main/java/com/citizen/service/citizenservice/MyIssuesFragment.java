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

import com.citizen.service.citizenservice.contracts.IMyIssue;
import com.citizen.service.citizenservice.http.HttpClient;
import com.citizen.service.citizenservice.models.IssueListItemModel;

import java.util.List;

public class MyIssuesFragment extends ListFragment {
    ActivityManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_issues, container, false);
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<IssueListItemModel> list = ((InternalActivity) getActivity()).myIssuesList;
        list.clear();
        ListAdapter adapter = new ListItemAdapter(getActivity(), list);
        setListAdapter(adapter);

        setListAdapter(adapter);
        HttpClient httpClient = new HttpClient(getContext(), getResources().getString(R.string.server_url));
        httpClient.LoadMyIssues((IMyIssue) getActivity());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), "item clicked " + position, Toast.LENGTH_SHORT).show();

        ((InternalActivity) getActivity()).setDetailsInformation(position, 5);

        ViewPager pager = ((InternalActivity) getActivity()).viewPager;
        pager.setCurrentItem(5);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        manager = (InternalActivity)getActivity();
    }
}