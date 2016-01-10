package com.citizen.service.citizenservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InternalActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        viewPager = (ViewPager) findViewById(R.id.internalViewPager);
        InternalPagerAdapter adapter = new InternalPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public void onSubmitButtonClick(View view) {
        EditText title = (EditText) findViewById(R.id.submitTitle);
        EditText description = (EditText) findViewById(R.id.submitDescription);

        if (title.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Title cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (description.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Decription cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }



        Toast.makeText(getApplicationContext(), "Submit Successful", Toast.LENGTH_SHORT).show();
    }

    public void onSearchButtonClick(View view) {
        EditText title = (EditText) findViewById(R.id.searchTitle);
        EditText city = (EditText) findViewById(R.id.searchCity);

        if (title.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Title cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (city.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "City cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public class InternalPagerAdapter extends FragmentPagerAdapter {
        public InternalPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new IssuesFragment();
                case 1:
                    return new SearchFragment();
                case 2:
                    return new SubmitFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
