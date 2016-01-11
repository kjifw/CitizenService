package com.citizen.service.citizenservice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

        InitializeDrawer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.internalToolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Citizen Service");
        }
    }

    public void InitializeDrawer(){
        ListView listViewDrawer = (ListView) findViewById(R.id.leftDrawer);
        final ArrayAdapter<String> listAdapterDrawer = new ArrayAdapter<String>(
                this,
                R.layout.drawer_list_view_item,
                getResources().getStringArray(R.array.drawer_items));

        listViewDrawer.setAdapter(listAdapterDrawer);

        listViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewPager.setCurrentItem(position);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
                drawer.closeDrawers();
            }
        });
    }

    public void onSubmitButtonClick(View view) {
        EditText title = (EditText) findViewById(R.id.submitTitle);
        EditText description = (EditText) findViewById(R.id.submitDescription);

//        if (title.getText().toString().matches("")) {
//            Toast.makeText(getApplicationContext(), "Title cannot be empty.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (description.getText().toString().matches("")) {
//            Toast.makeText(getApplicationContext(), "Decription cannot be empty.", Toast.LENGTH_SHORT).show();
//            return;
//        }



        Toast.makeText(getApplicationContext(), "Submit Successful", Toast.LENGTH_SHORT).show();
        viewPager.setCurrentItem(0);
    }

    public void onSearchButtonClick(View view) {
        EditText title = (EditText) findViewById(R.id.searchTitle);
        EditText city = (EditText) findViewById(R.id.searchCity);

//        if (title.getText().toString().matches("")) {
//            Toast.makeText(getApplicationContext(), "Title cannot be empty.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (city.getText().toString().matches("")) {
//            Toast.makeText(getApplicationContext(), "City cannot be empty.", Toast.LENGTH_SHORT).show();
//            return;
//        }

        viewPager.setCurrentItem(3);
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
                case 3:
                    return new SearchResultFragment();
                case 4:
                    return new MyIssuesFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
