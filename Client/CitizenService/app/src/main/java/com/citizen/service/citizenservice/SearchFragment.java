package com.citizen.service.citizenservice;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.citizen.service.citizenservice.contracts.ISearchIssue;
import com.citizen.service.citizenservice.contracts.ISearchResult;
import com.citizen.service.citizenservice.http.HttpClient;
import com.citizen.service.citizenservice.http.SearchIssueAsync;
import com.citizen.service.citizenservice.http.SearchIssueData;
import com.citizen.service.citizenservice.models.IssueListItemModel;
import com.citizen.service.citizenservice.storage.CitiesDbHandler;
import com.citizen.service.citizenservice.storage.TokensDbHandler;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        AutoCompleteTextView autoTextView = (AutoCompleteTextView) view.findViewById(R.id.searchCity);

        ArrayList<String> cities;

        CitiesDbHandler dbHandler = ((InternalActivity) getActivity()).dbHandler;
        cities = dbHandler.getCities();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.search_cities_layout,
                cities);

        autoTextView.setAdapter(adapter);

        Button search = (Button) view.findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

                EditText cityInput = (EditText) view.findViewById(R.id.searchCity);
                EditText titleInput = (EditText) view.findViewById(R.id.searchTitle);

                String city = cityInput.getText().toString();
                String title = titleInput.getText().toString();

                ((InternalActivity) getActivity()).dbHandler.addCity(cityInput.getText().toString());

                String url = getResources().getString(R.string.server_url);
                SearchIssueData searchIssueData = new SearchIssueData(title, city);

                ListItemAdapter adapter = ((ISearchResult) getActivity()).getSearchResultAdapter();
                HttpClient httpClient = new HttpClient(getContext(), url);
                httpClient.LoadSearchIssues((ISearchResult) getActivity(), searchIssueData, adapter);

                ((InternalActivity) getActivity()).viewPager.setCurrentItem(4);
            }
        });

        return  view;
    }
}