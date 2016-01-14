package com.citizen.service.citizenservice;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        // For testing purposes - to be replaced
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.drawable.item)
                + '/' + getResources().getResourceTypeName(R.drawable.item)
                + '/' + getResources().getResourceEntryName(R.drawable.item));

        List<IssueListItemModel> list = new ArrayList<IssueListItemModel>();
        list.add(new IssueListItemModel(imageUri, "stitle0", "votes0"));
        list.add(new IssueListItemModel(imageUri, "stitle1", "votes1"));
        list.add(new IssueListItemModel(imageUri, "stitle2", "votes2"));
        list.add(new IssueListItemModel(imageUri, "stitle3", "votes3"));
        list.add(new IssueListItemModel(imageUri, "stitle4", "votes4"));
        list.add(new IssueListItemModel(imageUri, "stitle5", "votes5"));
        list.add(new IssueListItemModel(imageUri, "stitle6", "votes6"));

        ListAdapter adapter = new ListItemAdapter(getActivity(), list);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), "item clicked" + position, Toast.LENGTH_SHORT).show();
        // transfer data to details page
        // ViewPager pager = ((InternalActivity) getActivity()).viewPager;
        // pager.setCurrentItem(6);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        manager = (InternalActivity)getActivity();
    }
}