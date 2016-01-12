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

                        if (email.getText().toString().matches("")) {
                            Toast.makeText(view.getContext(), "Please input email", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (password.getText().toString().matches("")) {
                            Toast.makeText(view.getContext(), "Please input password", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent = new Intent(getActivity(), InternalActivity.class);
                        startActivity(intent);
                    }
                }
        );

        return  view;
    }
}
