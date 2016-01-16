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

import com.citizen.service.citizenservice.http.SearchIssue;
import com.citizen.service.citizenservice.http.SearchIssueData;
import com.citizen.service.citizenservice.storage.CitiesDbHandler;
import com.citizen.service.citizenservice.storage.TokensDbHandler;

import java.util.ArrayList;

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

                TokensDbHandler tokensDbHandler = new TokensDbHandler(getContext(), null);
                String url = getResources().getString(R.string.server_url);
                SearchIssueData searchIssueData = new SearchIssueData(title, city);

                SearchIssue searchIssue = new SearchIssue(getContext(), url, tokensDbHandler.getToken("login"));
                searchIssue.execute(searchIssueData);

                ((InternalActivity) getActivity()).viewPager.setCurrentItem(4);
            }
        });

        return  view;
    }
}