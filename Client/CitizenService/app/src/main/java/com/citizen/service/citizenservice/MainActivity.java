package com.citizen.service.citizenservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.citizen.service.citizenservice.navigation.NavigationService;

public class MainActivity extends AppCompatActivity implements NavigationService {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.mainViewPager);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public void navigateToRegister(View view) {
        viewPager.setCurrentItem(1);
    }

    @Override
    public void goToInternalActivity() {
        Intent intent = new Intent(this, InternalActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToIssuesFragment() {

    }

    public class MainPagerAdapter extends FragmentPagerAdapter {
        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SignInFragment();
                case 1:
                    return new RegisterFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
