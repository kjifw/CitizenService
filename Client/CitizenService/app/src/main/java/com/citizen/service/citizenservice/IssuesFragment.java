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

import com.citizen.service.citizenservice.models.IssueListItemModel;

import java.util.ArrayList;
import java.util.List;

public class IssuesFragment extends ListFragment {
    ActivityManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issues, container, false);
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<IssueListItemModel> list = ((InternalActivity) getActivity()).issuesList;
        list.clear();

        // For testing purposes - to be replaced
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.drawable.item)
                + '/' + getResources().getResourceTypeName(R.drawable.item)
                + '/' + getResources().getResourceEntryName(R.drawable.item));

        list.add(new IssueListItemModel(imageUri, "title0", "votes0", "description0", "author0"));
        list.add(new IssueListItemModel(imageUri, "title1", "votes1", "description1", "author1"));
        list.add(new IssueListItemModel(imageUri, "title2", "votes2", "description2", "author2"));
        list.add(new IssueListItemModel(imageUri, "title3", "votes3", "description3", "author3"));
        list.add(new IssueListItemModel(imageUri, "title4", "votes4", "description4", "author4"));
        list.add(new IssueListItemModel(imageUri, "title5", "votes5", "description5", "author5"));
        list.add(new IssueListItemModel(imageUri, "title6", "votes6", "description6", "author6"));

        ListAdapter adapter = new ListItemAdapter(getActivity(), list);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), "item clicked" + position, Toast.LENGTH_SHORT).show();
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
