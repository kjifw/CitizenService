package com.citizen.service.citizenservice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import com.citizen.service.citizenservice.contracts.IMyIssue;
import com.citizen.service.citizenservice.contracts.ISearchResult;
import com.citizen.service.citizenservice.contracts.ITopVotedResult;
import com.citizen.service.citizenservice.models.IssueListItemModel;
import com.citizen.service.citizenservice.navigation.NavigationService;
import com.citizen.service.citizenservice.storage.CitiesDbHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class InternalActivity extends AppCompatActivity implements ActivityManager,
        NavigationService, ISearchResult, ITopVotedResult, IMyIssue {
    ViewPager viewPager;
    CitiesDbHandler dbHandler;
    LinearLayout imagesView;
    List<IssueListItemModel> issuesList;
    List<IssueListItemModel> myIssuesList;
    List<IssueListItemModel> searchResultList;
    InternalPagerAdapter adapter;
    Bundle bundle;

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

        bundle = new Bundle();
        adapter = new InternalPagerAdapter(getSupportFragmentManager(), fragments, bundle);
        viewPager.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.internalToolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.app_name);
        }

        InitializeDrawer();

        dbHandler = new CitiesDbHandler(this, null);

        issuesList = new ArrayList<>();
        myIssuesList = new ArrayList<>();
        searchResultList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.internal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signOutButton) {
            Intent logout = new Intent(InternalActivity.this, MainActivity.class);
            InternalActivity.this.startActivity(logout);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final static int LOAD_PHOTO = 42;

    public void onSelectPictureButtonClick(View view){

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(pickIntent, "Select Image");

        startActivityForResult(chooserIntent, LOAD_PHOTO);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == LOAD_PHOTO) {
                    Uri selectedImageUri = data.getData();
                    ImageView item = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10, 0, 10, 0);
                    item.setLayoutParams(lp);
                    item.setImageURI(selectedImageUri);
                    item.setTag(R.integer.photo_path, getRealPathFromURI(selectedImageUri));
                    imagesView = (LinearLayout) findViewById(R.id.submit_images);
                    imagesView.addView(item);
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

    @Override
    public void setDetailsInformation(int itemId, int fragmentId) {
        String key = "currentFragment";
        if (fragmentId == 4) {
            this.bundle.putString(key, "Issues");
        }

        if (fragmentId == 5) {
            this.bundle.putString(key, "MyIssues");
        }

        if (fragmentId == 6) {
            this.bundle.putString(key, "SearchResult");
        }

        this.bundle.putInt("currentItem", itemId);
    }

    private boolean collectionContainsObject(List<IssueListItemModel> collection, IssueListItemModel obj) {
        for(int i = 0; i < collection.size(); i++) {
            IssueListItemModel model = collection.get(i);
            if((model.getAuthor().equals(obj.getAuthor())) && (model.getTitle().equals(obj.getTitle()))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setMyIssueData(List<IssueListItemModel> models) {

        if(myIssuesList.size() == 0) {
            myIssuesList.clear();
            myIssuesList.addAll(models);
            return;
        }

        for(int i = 0; i < models.size(); i++) {
            IssueListItemModel model = models.get(i);
            if(collectionContainsObject(myIssuesList, model) == false) {
                myIssuesList.add(model);
            }
        }
    }

    @Override
    public void setSearchResultData(List<IssueListItemModel> models) {

        if(searchResultList.size() == 0) {
            searchResultList.clear();
            searchResultList.addAll(models);
            return;
        }

        for(int i = 0; i < models.size(); i++) {
            IssueListItemModel model = models.get(i);
            if(collectionContainsObject(searchResultList, model) == false) {
                searchResultList.add(model);
            }
        }
    }

    ListItemAdapter searchResultAdapter = null;

    @Override
    public ListItemAdapter getSearchResultAdapter() {

        if(searchResultAdapter == null) {
            List<IssueListItemModel> list = searchResultList;
            searchResultAdapter = new ListItemAdapter(this, list);
        }

        return searchResultAdapter;
    }

    @Override
    public void setTopVotedResultData(List<IssueListItemModel> models) {

        if(issuesList.size() == 0) {
            issuesList.clear();
            issuesList.addAll(models);
            return;
        }

        for(int i = 0; i < models.size(); i++) {
            IssueListItemModel model = models.get(i);
            if(collectionContainsObject(issuesList, model) == false) {
                issuesList.add(model);
            }
        }
    }

    public class InternalPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private Bundle bundle;

        public InternalPagerAdapter(FragmentManager fm, List<Fragment> fragments, Bundle bundle) {
            super(fm);
            this.fragments = fragments;
            this.bundle = bundle;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment item =  this.fragments.get(position);
            item.setArguments(bundle);
            return item;
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void goToInternalActivity() {

    }

    @Override
    public void goToIssuesFragment() {
        viewPager.setCurrentItem(0);
    }
}



