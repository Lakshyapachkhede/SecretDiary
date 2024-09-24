package com.pachkhede.secretdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences(LockSetupActivity.preferencesName, MODE_PRIVATE);
        EditText passwordInput = findViewById(R.id.code_lock_password);
        findViewById(R.id.unlock_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPassword = passwordInput.getText().toString();

                if (userPassword.isBlank()) {
                    passwordInput.setError("Please enter password");

                    return;
                }

                String password = sharedPreferences.getString(LockSetupActivity.prefPassword, "demo");

                if (userPassword.equals(password)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("PasswordError", password + " " + userPassword);
                    passwordInput.setError("Please enter correct password");
                }
            }
        });


        findViewById(R.id.forgot_password_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginPasswordReset.class);
                startActivity(intent);

            }
        });



    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}