package com.pachkhede.secretdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.locks.Lock;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences(LockSetupActivity.preferencesName, MODE_PRIVATE);

        Boolean isPasswordEnabled = sharedPreferences.getBoolean(LockSetupActivity.prefIsLockEnabled, false);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;
                if (isPasswordEnabled) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);

                }

                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}