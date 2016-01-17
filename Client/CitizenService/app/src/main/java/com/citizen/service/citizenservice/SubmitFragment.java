package com.citizen.service.citizenservice;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import com.citizen.service.citizenservice.geolocation.UpdateCurrentCity;
import com.citizen.service.citizenservice.http.PublishIssue;
import com.citizen.service.citizenservice.http.PublishIssueData;
import com.citizen.service.citizenservice.navigation.NavigationService;
import com.citizen.service.citizenservice.storage.TokensDbHandler;


public class SubmitFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_submit, container, false);

        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            public boolean onDoubleTap(MotionEvent e) {

                LocationManager locationManager = (LocationManager)getContext().getSystemService(getContext().LOCATION_SERVICE);

                try {
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } catch(SecurityException ex) {
                    ex.printStackTrace();
                }

                final LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        new UpdateCurrentCity(getContext(), view).execute(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                    }

                    public void onProviderDisabled(String arg0) { }

                    public void onProviderEnabled(String arg0) { }

                    public void onStatusChanged(String arg0, int arg1, Bundle arg2) { }
                };

                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
                } catch(SecurityException ex) {
                    ex.printStackTrace();
                }
                return true;
            }
        });

        EditText tv = (EditText) view.findViewById(R.id.submit_city);
        tv.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        Button button = (Button) view.findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cityInput = (EditText)view.findViewById(R.id.submit_city);
                EditText titleInput = (EditText)view.findViewById(R.id.submit_title);
                EditText descriptionInput = (EditText) view.findViewById(R.id.submit_description);
                Switch isAnonymousInput = (Switch) view.findViewById(R.id.submit_is_anonymous);

                String city = cityInput.getText().toString();
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                boolean isAnonymous = isAnonymousInput.isChecked();

                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.submit_images);
                int imagesForUploadCount = linearLayout.getChildCount();

                ArrayList<String> imagesPaths = new ArrayList<String>();

                for(int i = 0; i < imagesForUploadCount; i++) {
                    ImageView imageForUpload = (ImageView) linearLayout.getChildAt(i);
                    String imagePath = (String)imageForUpload.getTag(R.integer.photo_path);
                    imagesPaths.add(imagePath);
                }

                PublishIssueData publishIssueData = new PublishIssueData(city, title, description, isAnonymous, imagesPaths);

                TokensDbHandler tokensDbHandler = new TokensDbHandler(getContext(), null);
                String url = getResources().getString(R.string.server_url);
                PublishIssue publishIssue = new PublishIssue(getContext(), url, tokensDbHandler.getToken("login"));
                publishIssue.execute(publishIssueData);

                Toast.makeText(getContext(), "Issue submited", Toast.LENGTH_SHORT).show();

                NavigationService navigationService = (NavigationService) getActivity();
                navigationService.goToIssuesFragment();
            }
        });

        return  view;
    }
}
