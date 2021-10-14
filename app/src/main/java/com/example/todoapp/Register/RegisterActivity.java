package com.example.todoapp.Register;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.todoapp.R;
import com.example.todoapp.Utils.UserDBHelper;


public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputPassword;
    private UserDBHelper userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDB = new UserDBHelper(this);
        inputEmail = (EditText) findViewById(R.id.etEmail);
        inputPassword = (EditText) findViewById(R.id.etPass);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean isEmpty(String email, String pass) {
        return email.isEmpty() && pass.isEmpty();
    }

    public void registerOnClick(View view){
        String email = inputEmail.getText().toString();
        String pass = inputPassword.getText().toString();

        if (isEmpty(email, pass)) {
            displayToast("Username/password field is empty");
        } else {
            userDB.addUser(email, pass);
            displayToast("User registered");
            finish();
        }
    }

    public void backToLoginOnClick(View view) {
        finish();
    }
}