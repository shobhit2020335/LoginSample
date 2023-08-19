package com.example.loginsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.loginsample.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Signup extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseAuth auth;
    TextInputEditText mail, password, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginbtnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mail = (TextInputEditText) findViewById(R.id.inputemailsignup);
        name = (TextInputEditText) findViewById(R.id.inputnamesignup);
        password = (TextInputEditText) findViewById(R.id.inputpasswordsignup);


        auth = FirebaseAuth.getInstance();

        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Required");
                    name.requestFocus();
                } else if (mail.getText().toString().isEmpty()) {
                    mail.setError("Required");
                    mail.requestFocus();
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Required");
                    password.requestFocus();
                } else {


                    String email = Objects.requireNonNull(mail.getText()).toString();
                    String pass = Objects.requireNonNull(password.getText()).toString();
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(Signup.this, "REGISTERD SUCCESSFULLY. PLEASE LOGIN.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Signup.this, MainActivity.class);

                                String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                intent.putExtra("uid",id);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Signup.this, "ERROR:" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}