package com.example.todoapp.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import com.example.todoapp.Preferences.Preferences;
import com.example.todoapp.Register.RegisterActivity;


public class LoginActivity extends AppCompatActivity {
    private EditText mViewUser;
    private EditText mViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mViewUser = findViewById(R.id.et_emailSignin);
        mViewPassword = findViewById(R.id.et_passwordSignin);

        mViewPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                loginCheck();
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())){
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }
    }

    private void loginCheck() {
        mViewUser.setError(null);
        mViewPassword.setError(null);
        View focus = null;
        boolean cancel = false;

        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        if (TextUtils.isEmpty(user)) {
            mViewUser.setError("This field is required");
            focus = mViewUser;
            cancel = true;
        } else if(!isValidUser(user)) {
            mViewUser.setError("This Username is not found");
            focus = mViewUser;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mViewPassword.setError("This field is required");
            focus = mViewPassword;
            cancel = true;
        } else if (!isValidPassword(password)) {
            mViewPassword.setError("This password is incorrect");
            focus = mViewPassword;
            cancel = true;
        }

        if (cancel) {
            focus.requestFocus();
        } else {
            masuk();
        }
    }

    private void masuk() {
        Preferences.setLoggedInUser(getBaseContext(),
                Preferences.getRegisteredUser(getBaseContext()));
        Preferences.setLoggedInStatus(getBaseContext(),true);
        startActivity(new Intent(getBaseContext(), MainActivity.class));finish();
    }

    private boolean isValidUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }

    private boolean isValidPassword(String password) {
        return password.equals(Preferences.getRegisteredPass(getBaseContext()));
    }

    public void loginOnClick(View view) {
        loginCheck();
    }

    public void registerOnClick(View view) {
        startActivity(new Intent(getBaseContext(), RegisterActivity.class));
    }
}