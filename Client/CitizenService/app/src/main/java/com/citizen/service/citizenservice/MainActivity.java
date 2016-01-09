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

public class MainActivity extends AppCompatActivity {
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

    public void onSignInButtonClick(View view) {
        EditText email = (EditText) findViewById(R.id.signInEmail);
        EditText password = (EditText) findViewById(R.id.signInPassword);

//        if (email.getText().toString().matches("")) {
//            Toast.makeText(getApplicationContext(), "Please input email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (password.getText().toString().matches("")) {
//            Toast.makeText(getApplicationContext(), "Please input password", Toast.LENGTH_SHORT).show();
//            return;
//        }


        Intent intent = new Intent(MainActivity.this, InternalActivity.class);
        startActivity(intent);
    }

    public void onRegisterButtonClick(View view) {
        EditText email = (EditText) findViewById(R.id.registerEmail);
        EditText password = (EditText) findViewById(R.id.registerPassword);
        EditText confirmPassword = (EditText) findViewById(R.id.registerRepeatPassword);

        if (email.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Please input email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Please input password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (confirmPassword.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Please repeat password", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, InternalActivity.class);
        startActivity(intent);
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
