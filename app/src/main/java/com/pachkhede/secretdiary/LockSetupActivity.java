package com.pachkhede.secretdiary;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LockSetupActivity extends AppCompatActivity {
    static String preferencesName = "SecretDiary";
    static String prefIsLockEnabled = "isLockEnabled";
    static String prefPassword = "password";
    static String prefQuestion = "question";
    static String prefAnswer = "answer";
    Toolbar toolbar;
    String[] questions = {
            "What is your secret recovery word?",
            "Where was the destination of your most memorable school field trip?",
            "What is the name of a college you applied to but did not attend?",
            "What was your driving instructor's first name?",
            "What was the name of the first school you remember attending?",
            "What was the name of your first stuffed toy?",
            "What is your favorite animal?",
            "What was your maths teacher's surname in your first grade?"
    };

    SharedPreferences sharedPreferences;
    EditText passwordEdt, answerEdt;
    TextView questionEdt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_setup);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Code Lock");
        toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.white));

        passwordEdt = findViewById(R.id.code_lock_password);
        questionEdt = findViewById(R.id.code_lock_questions);
        answerEdt = findViewById(R.id.code_lock_question_answer);


        sharedPreferences = getSharedPreferences(preferencesName, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(prefIsLockEnabled, false)) {
            passwordEdt.setText(sharedPreferences.getString(prefPassword, "demo"));
            questionEdt.setText(sharedPreferences.getString(prefQuestion, "demo"));
            answerEdt.setText(sharedPreferences.getString(prefAnswer, "demo"));
            findViewById(R.id.disable_lock_btn).setVisibility(View.VISIBLE);
        }



        findViewById(R.id.code_lock_questions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestionsDialog();
            }
        });

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEdt.getText().toString();
                String question = questionEdt.getText().toString();
                String answer = answerEdt.getText().toString();
                if (password.isEmpty()) {
                    passwordEdt.setError("Please enter a password");
                    return;
                }
                if (question.isEmpty()) {
                    questionEdt.setError("Please Choose a question");
                    return;
                }
                if (answer.isEmpty()) {
                    answerEdt.setError("Please enter a answer");
                    return;
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();


                editor.putBoolean(prefIsLockEnabled, true);
                editor.putString(prefPassword, password);
                editor.putString(prefQuestion, question);
                editor.putString(prefAnswer, answer);

                editor.apply();

                findViewById(R.id.disable_lock_btn).setVisibility(View.VISIBLE);

            }
        });

        findViewById(R.id.disable_lock_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LockSetupActivity.this)
                        .setTitle("Deactivate codelock?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(LockSetupActivity.prefIsLockEnabled, false);
                            editor.apply();
                            Toast.makeText(LockSetupActivity.this, "Code Lock Disabled", Toast.LENGTH_SHORT).show();

                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .create().show();

                findViewById(R.id.disable_lock_btn).setVisibility(View.GONE);

            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showQuestionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LockSetupActivity.this);
        builder.setTitle("Select Recovery Question");
        builder.setItems(questions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedQuestion = questions[which];
                TextView questions = findViewById(R.id.code_lock_questions);
                questions.setText(selectedQuestion);
            }
        });

        builder.create().show();

    }

}