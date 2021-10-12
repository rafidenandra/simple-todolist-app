package com.example.todoapp.Register;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;


public class RegisterActivity extends AppCompatActivity {
    private EditText mViewUser;
    private EditText mViewPassword;
    private EditText mViewRepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mViewUser = findViewById(R.id.et_emailSignup);
        mViewPassword = findViewById(R.id.et_passwordSignup);
        mViewRepassword = findViewById(R.id.et_passwordSignup2);

        mViewRepassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                registerCheck();
                return true;
            }
            return false;
        });

        findViewById(R.id.button_signupSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCheck();
            }
        });
    }

    private void registerCheck() {
        mViewUser.setError(null);
        mViewPassword.setError(null);
        mViewRepassword.setError(null);
        View focus = null;
        boolean cancel = false;

        String repassword = mViewRepassword.getText().toString();
        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        if (TextUtils.isEmpty(user)) {
            mViewUser.setError("This field is required");
            focus = mViewUser;
            cancel = true;
        } else if(checkUser(user)) {
            mViewUser.setError("This Username is already exist");
            focus = mViewUser;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mViewPassword.setError("This field is required");
            focus = mViewPassword;
            cancel = true;
        } else if (!checkPassword(password,repassword)) {
            mViewRepassword.setError("This password is incorrect");
            focus = mViewRepassword;
            cancel = true;
        }

        if (cancel) {
            focus.requestFocus();
        } else {
            Preferences.setRegisteredUser(getBaseContext(),user);
            Preferences.setRegisteredPass(getBaseContext(),password);
            finish();
        }
    }

    private boolean checkPassword(String password, String repassword) {
        return password.equals(repassword);
    }

    private boolean checkUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}