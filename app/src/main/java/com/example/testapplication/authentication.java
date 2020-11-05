package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class authentication extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextInputLayout usernameInput, passwordInput;
    private Button signinButton, createButton;
    private String username, password;
    private static final String TAG = "Authentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        usernameInput = (TextInputLayout) findViewById(R.id.auth_username);
        passwordInput = (TextInputLayout) findViewById(R.id.auth_password);

        signinButton = (Button) findViewById(R.id.auth_signinButton);
        createButton = (Button) findViewById(R.id.auth_signupButton);

        mAuth = FirebaseAuth.getInstance();

        createButton.setOnClickListener(create);
        signinButton.setOnClickListener(signin);
    }

    View.OnClickListener create = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            username = usernameInput.getEditText().getText().toString();
            password = passwordInput.getEditText().getText().toString();
            username = username.trim();
            password = password.trim();
            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(authentication.this, "Please fill up the username or password.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            createAccount(username, password);
        }
    };

    View.OnClickListener signin = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            username = usernameInput.getEditText().getText().toString();
            password = passwordInput.getEditText().getText().toString();
            username = username.trim();
            password = password.trim();
            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(authentication.this, "Please fill up the username or password.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            signIn(username, password);
            if (user != null){
                Intent intent = new Intent(authentication.this, MainActivity.class);
                startActivity(intent);
            }
        }
    };

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(authentication.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(authentication.this, "Password Complexity Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END create_user_with_email]
    }
}
