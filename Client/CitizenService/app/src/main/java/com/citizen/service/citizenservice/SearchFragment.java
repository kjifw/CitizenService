package com.citizen.service.citizenservice;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.citizen.service.citizenservice.storage.CitiesDbHandler;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        AutoCompleteTextView autoTextView = (AutoCompleteTextView) view.findViewById(R.id.searchCity);

        ArrayList<String> cities;

        CitiesDbHandler dbHandler = ((InternalActivity) getActivity()).dbHandler;
        cities = dbHandler.getCities();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.search_cities_layout,
                cities);

        autoTextView.setAdapter(adapter);

        return  view;
    }
}