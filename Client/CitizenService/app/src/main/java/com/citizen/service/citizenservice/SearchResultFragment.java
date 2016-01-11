package com.citizen.service.citizenservice;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment {
    private ListView listOfIssues;
    private ActivityManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        List<String> list = new ArrayList<String>();
        list.add("search result item 1");
        list.add("search result item 2");
        list.add("search result item 3");
        list.add("search result item 4");

        ListAdapter adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.list_view_item, list);
        listOfIssues = (ListView) view.findViewById(R.id.issuesList);

        listOfIssues.setAdapter(adapter);

        listOfIssues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewPager pager = ((InternalActivity) getActivity()).viewPager;
                manager.setDetailsInformation(position);

                pager.setCurrentItem(6);
            }
        });

        return  view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        manager = (InternalActivity)getActivity();
    }
}
