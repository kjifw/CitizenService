package com.citizen.service.citizenservice;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class IssuesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issues, container, false);

        List<String> list = new ArrayList<String>();
//        list.add("first");
//        list.add("second");
//        list.add("third");
//        list.add("fourth");

        // TODO: create issue class and other classes

        ListAdapter adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.list_view_item, list);
        ListView listOfIssues = (ListView) view.findViewById(R.id.mostVotedIssuesList);

        listOfIssues.setAdapter(adapter);

        return  view;
    }
}
