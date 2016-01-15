package com.citizen.service.citizenservice.geolocation;


import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import com.citizen.service.citizenservice.R;

public class UpdateCurrentCity extends AsyncTask<String, Void, String> {

    private Context context;
    private View view;

    public UpdateCurrentCity(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    protected String doInBackground(String... params) {

        GetReverseGeoCoding getReverseGeoCoding = new GetReverseGeoCoding();
        getReverseGeoCoding.getAddress(params[0], params[1]);
        return getReverseGeoCoding.getCity();
    }

    @Override
    protected void onPostExecute(String result) {
        EditText cityEditText = (EditText)this.view.findViewById(R.id.submit_city);
        cityEditText.setText(result);
    }
}
