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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class registeredFragment extends Fragment {

    EditText etName, etUserEmail, etUserPass, etConfirmPass;
    Button btnRegister;

    private FirebaseAuth auth;


    public registeredFragment() {
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
        return inflater.inflate(R.layout.fragment_registered, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.etName);
        etUserEmail = view.findViewById(R.id.etUserEmail);
        etUserPass = view.findViewById(R.id.etUserPass);
        etConfirmPass = view.findViewById(R.id.etConfirmPass);
        btnRegister = view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkEmptyField()) {
                    if (etUserPass.getText().toString().length() < 6) {
                        etUserPass.setError("Invalid Password or Password should be atleast greater thn 6 characters");
                        etUserPass.requestFocus();
                    } else {
                        if (!etUserPass.getText().toString().equals(etConfirmPass.getText().toString())) {
                            etConfirmPass.setError("Password Missmatch");
                            etConfirmPass.requestFocus();
                        } else {

                            String name = etName.getText().toString();
                            String email = etUserEmail.getText().toString();
                            String pass = etUserPass.getText().toString();

                            createUserAccount(name, email,pass);

                        }
                    }
                }

            }
        });
    }


    public boolean checkEmptyField() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Name Cannot be blank !!!");
            etName.requestFocus();
            return true;
        } else if (TextUtils.isEmpty(etUserEmail.getText().toString())) {
            etUserEmail.setError("Email Cannot be blank !!!");
            etUserEmail.requestFocus();
            return true;
        } else if (TextUtils.isEmpty(etUserPass.getText().toString())) {
            etUserPass.setError("Password Cannot be blank !!!");
            etUserPass.requestFocus();
            return true;
        } else if (TextUtils.isEmpty(etConfirmPass.getText().toString())) {
            etConfirmPass.setError("Confirm Password Cannot be blank !!!");
            etConfirmPass.requestFocus();
            return true;
        }
        return false;
    }

    public void createUserAccount(final String name, final String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map<String, Object> usermap = new HashMap<>();
                    usermap.put("Name", name);
                    usermap.put("Email", email);

                    db.collection("users").document(user.getUid()).set(usermap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Register Successfully",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                } else {
                    System.out.println("From Register: " + task.getException());
                    Toast.makeText(getActivity().getApplicationContext(), task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        FirebaseAuth.getInstance().signOut();
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragment);
        navController.navigate(R.id.loginFragment);

    }

}
