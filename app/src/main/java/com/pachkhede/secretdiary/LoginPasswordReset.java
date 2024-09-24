package com.pachkhede.secretdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPasswordReset extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password_reset);

        SharedPreferences sharedPreferences = getSharedPreferences(LockSetupActivity.preferencesName, MODE_PRIVATE);

        String question = sharedPreferences.getString(LockSetupActivity.prefQuestion, "demo");
        String answer = sharedPreferences.getString(LockSetupActivity.prefAnswer, "demo");

        TextView questionEdt = findViewById(R.id.reset_question);
        questionEdt.setText(question);


        EditText passwordInput = findViewById(R.id.code_lock_answer);

        findViewById(R.id.show_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = passwordInput.getText().toString();

                if (userAnswer.isBlank()) {
                    passwordInput.setError("Please enter password");
                    return;
                }



                if (userAnswer.equals(answer)){
                    String password = sharedPreferences.getString(LockSetupActivity.prefPassword, "demo");

                    Toast.makeText(LoginPasswordReset.this, password, Toast.LENGTH_SHORT).show();
                } else {
                    passwordInput.setError("Please enter correct answer");
                }
            }
        });






    }


}