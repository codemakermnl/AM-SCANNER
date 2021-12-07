package com.app.qrscheduler.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.qrscheduler.R;
import com.app.qrscheduler.dashboard.DashboardActivity;
import com.app.qrscheduler.signup.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailTxt;

    private EditText passwordTxt;

    private TextView errorMessageTextView;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailTxt = findViewById(R.id.loginTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        errorMessageTextView = findViewById(R.id.errorLoginTextView);

    }

    public void login(View view) {
        String email = emailTxt.getText().toString();
        final String password = passwordTxt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }


        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        // there was an error
                        if (password.length() < 6) {
                            errorMessageTextView.setText("Minimum password is 6 characters!");
                        } else {
                            errorMessageTextView.setText("Invalid Username/Password");
                        }
                    } else {
//                        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();;
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }

    public void signUp(View view) {
        Intent signUp = new Intent(this, SignUpActivity.class);
        startActivity(signUp);
    }
}