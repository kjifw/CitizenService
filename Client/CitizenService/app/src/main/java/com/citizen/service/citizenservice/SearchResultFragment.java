package com.citizen.service.citizenservice;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.citizen.service.citizenservice.contracts.ISearchResult;
import com.citizen.service.citizenservice.models.IssueListItemModel;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends ListFragment {
    ActivityManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(((ISearchResult) getActivity()).getSearchResultAdapter());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), "item clicked" + position, Toast.LENGTH_SHORT).show();

        ((InternalActivity) getActivity()).setDetailsInformation(position, 6);

        ViewPager pager = ((InternalActivity) getActivity()).viewPager;
        pager.setCurrentItem(6);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        manager = (InternalActivity)getActivity();
    }
}