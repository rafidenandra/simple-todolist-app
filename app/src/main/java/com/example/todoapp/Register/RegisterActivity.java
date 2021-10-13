package com.example.todoapp.Register;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.todoapp.Login.LoginActivity;
import com.example.todoapp.R;
import com.example.todoapp.Utils.UserDBHelper;


public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail, etPass;
    private UserDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new UserDBHelper(this);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void registerOnClick(View view){
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();

        if (email.isEmpty() && pass.isEmpty()) {
            displayToast("Username/password field empty");
        } else {
            db.addUser(email, pass);
            displayToast("User registered");
            finish();
        }
    }

    public void backToLoginOnClick(View view) {
        finish();
    }
}