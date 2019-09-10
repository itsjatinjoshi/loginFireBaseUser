package com.example.myapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class loginFragment extends Fragment implements View.OnClickListener {

    EditText etMail, etPass;
    Button btnLogin;
    TextView tvRegistered;

    private FirebaseAuth auth;
    FirebaseUser user;


    public loginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

        etMail = view.findViewById(R.id.etMail);
        etPass = view.findViewById(R.id.etPass);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvRegistered = view.findViewById(R.id.tvRegistered);

        btnLogin.setOnClickListener(this);
        tvRegistered.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btnLogin) {
            if (TextUtils.isEmpty(etMail.getText().toString())) {
                etMail.setError("Email cannot be Blank !");
                etMail.requestFocus();
            } else if (TextUtils.isEmpty(etPass.getText().toString())) {
                etPass.setError("Please enter password");
                etPass.requestFocus();
            } else {
                if (etPass.getText().toString().length() < 6) {
                    etPass.setError("Password should not be less then 6 letters");
                    etPass.requestFocus();

                } else {
                    String email = etMail.getText().toString();
                    String pass = etPass.getText().toString();

                    loginUser(email, pass);
                }
            }

        } else if (id == R.id.tvRegistered) {

            NavController navController = Navigation.findNavController(getActivity(), R.id.fragment);
            navController.navigate(R.id.registeredFragment);

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        user=  auth.getCurrentUser();
        if(user != null){
            updateUI(user);
            Toast.makeText(getContext().getApplicationContext(), "User Already logged in",
                    Toast.LENGTH_LONG).show();

        }
        else {

        }

    }

    public void loginUser(String userEmail, String userPassword) {

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            user = auth.getCurrentUser();
                            Toast.makeText(getActivity().getApplicationContext(), "Login Successfully",
                                    Toast.LENGTH_LONG).show();
                            updateUI(user);

                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Login Failed",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    public void updateUI(FirebaseUser user)
    {

        NavController navController = Navigation.findNavController(getActivity(), R.id.fragment);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        navController.navigate(R.id.homeFragment, bundle);

    }

}
