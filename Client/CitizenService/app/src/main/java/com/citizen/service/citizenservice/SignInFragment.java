package com.citizen.service.citizenservice;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.citizen.service.citizenservice.http.HttpClient;
import com.citizen.service.citizenservice.navigation.NavigationService;

public class SignInFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        Button button = (Button) view.findViewById(R.id.signInButton);

        button.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        EditText email = (EditText) view.findViewById(R.id.signInEmail);
                        EditText password = (EditText) view.findViewById(R.id.signInPassword);

                        String emailValue = email.getText().toString();
                        String passwordValue = password.getText().toString();

                        if (emailValue.matches("")) {
                            Toast.makeText(view.getContext(), "Please input email", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (passwordValue.matches("")) {
                            Toast.makeText(view.getContext(), "Please input password", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        HttpClient httpClient = new HttpClient(getContext(), getResources().getString(R.string.server_url));
                        httpClient.Login(emailValue, passwordValue, (NavigationService)getActivity());
                    }
                }
        );

        return  view;
    }
}
