package com.citizen.service.citizenservice.geolocation;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetReverseGeoCoding {
    private String Address1 = "", Address2 = "", City = "", State = "", Country = "", County = "", PIN = "";

    public void getAddress(String latitude, String longitude) {
        Address1 = "";
        Address2 = "";
        City = "";
        State = "";
        Country = "";
        County = "";
        PIN = "";

        try {

            JSONObject jsonObj = parserJson.parse("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyCmADZoR8XpwxZS-xV_WJhVEsmXrTqNTbY&latlng=" + latitude + "," + longitude + "&sensor=true");

            String Status = jsonObj.getString("status");
            if (Status.equalsIgnoreCase("OK")) {
                JSONArray Results = jsonObj.getJSONArray("results");
                JSONObject zero = Results.getJSONObject(0);
                JSONArray address_components = zero.getJSONArray("address_components");

                for (int i = 0; i < address_components.length(); i++) {
                    JSONObject zero2 = address_components.getJSONObject(i);
                    String long_name = zero2.getString("long_name");
                    JSONArray mtypes = zero2.getJSONArray("types");
                    String Type = mtypes.getString(0);

                    if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {
                        if (Type.equalsIgnoreCase("street_number")) {
                            Address1 = long_name + " ";
                        } else if (Type.equalsIgnoreCase("route")) {
                            Address1 = Address1 + long_name;
                        } else if (Type.equalsIgnoreCase("sublocality")) {
                            Address2 = long_name;
                        } else if (Type.equalsIgnoreCase("locality")) {
                            // Address2 = Address2 + long_name + ", ";
                            City = long_name;
                        } else if (Type.equalsIgnoreCase("administrative_area_level_2")) {
                            County = long_name;
                        } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                            State = long_name;
                        } else if (Type.equalsIgnoreCase("country")) {
                            Country = long_name;
                        } else if (Type.equalsIgnoreCase("postal_code")) {
                            PIN = long_name;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getAddress1() {
        return Address1;

    }

    public String getAddress2() {
        return Address2;

    }

    public String getCity() {
        return City;

    }

    public String getState() {
        return State;

    }

    public String getCountry() {
        return Country;

    }

    public String getCounty() {
        return County;

    }

    public String getPIN() {
        return PIN;

    }

    public static class parserJson {

        public static JSONObject parse(String googleApiUrl) {

            JSONObject jObject = null;
            HttpURLConnection urlConnection = null;

            try {

                URL url = new URL(googleApiUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setUseCaches(false);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.connect();

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());
            }

            StringBuilder response = new StringBuilder();

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 512);
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException ex) {

            }

            try {
                jObject = new JSONObject(response.toString());
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }

            return jObject;
        }
    }
}
