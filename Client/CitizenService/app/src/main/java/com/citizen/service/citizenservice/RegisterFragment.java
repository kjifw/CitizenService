package com.citizen.service.citizenservice;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.citizen.service.citizenservice.http.HttpClient;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button button = (Button) view.findViewById(R.id.registerButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = (EditText) view.findViewById(R.id.registerEmail);
                EditText password = (EditText) view.findViewById(R.id.registerPassword);
                EditText confirmPassword = (EditText) view.findViewById(R.id.registerRepeatPassword);

                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                String confirmPasswordValue = confirmPassword.getText().toString();

                if (emailValue.matches("")) {
                    Toast.makeText(getContext(), "Please input email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwordValue.toString().matches("")) {
                    Toast.makeText(getContext(), "Please input password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (confirmPasswordValue.matches("")) {
                    Toast.makeText(getContext(), "Please repeat password", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpClient httpClient = new HttpClient(getContext(), getResources().getString(R.string.server_url));
                httpClient.Register(emailValue, passwordValue, confirmPasswordValue, ((MainActivity) getActivity()).viewPager);
            }
        });

        return  view;
    }
}
