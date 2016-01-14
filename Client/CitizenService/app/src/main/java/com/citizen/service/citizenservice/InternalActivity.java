package com.citizen.service.citizenservice;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.citizen.service.citizenservice.storage.CitiesDbHandler;

import java.util.ArrayList;
import java.util.List;

public class InternalActivity extends AppCompatActivity implements ActivityManager{
    ViewPager viewPager;
    CitiesDbHandler dbHandler;
    LinearLayout imagesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        viewPager = (ViewPager) findViewById(R.id.internalViewPager);

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new IssuesFragment());
        fragments.add(new SearchFragment());
        fragments.add(new SubmitFragment());
        fragments.add(new MyIssuesFragment());
        fragments.add(new SearchResultFragment());
        fragments.add(new MyIssuesItemDetailsFragment());
        fragments.add(new ItemDetailsFragment());

        InternalPagerAdapter adapter = new InternalPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.internalToolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Citizen Service");
        }

        InitializeDrawer();

        dbHandler = new CitiesDbHandler(this, null);
    }

    public void takePhoto(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    public void onSelectPictureButtonClick(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    Uri selectedImageUri = data.getData();

                    ImageView item = new ImageView(getApplicationContext());
                    item.setImageURI(selectedImageUri);

                    imagesView = (LinearLayout) findViewById(R.id.submitImages);
                    imagesView.addView(item);
                    break;
                case 1:

                    // TODO: save photo to gallery and tell user picture is taken successfully
                    break;
            }
        }
    }

    public void InitializeDrawer(){

        ListView listViewDrawer = (ListView) findViewById(R.id.leftDrawer);
        final ArrayAdapter<String> listAdapterDrawer = new ArrayAdapter<String>(
                this,
                R.layout.drawer_list_view_item,
                getResources().getStringArray(R.array.drawer_items));

        listViewDrawer.setAdapter(listAdapterDrawer);

        final Context myContext = this;

        listViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 4) {
                    //takePhoto(view);
                    Intent intent = new Intent(myContext, CameraActivity.class);
                    startActivity(intent);
                } else {
                    viewPager.setCurrentItem(position);
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
                    drawer.closeDrawers();
                }
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.internalToolbar);
        setSupportActionBar(mToolbar);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, mToolbar,
                R.string.nav_bar_open, R.string.nav_bar_close)
        {
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
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


        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        // TODO: load information from database and uncomment next row after done
        dbHandler.addCity(city.getText().toString());

        // viewPager.setCurrentItem(3);
    }

    @Override
    public void setDetailsInformation(int itemId) {
        // TODO: load information for the selected item from ListView into details page
    }

    public class InternalPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public InternalPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }
}



