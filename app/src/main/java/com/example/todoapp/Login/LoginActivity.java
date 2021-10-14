package com.example.todoapp.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.Register.RegisterActivity;
import com.example.todoapp.Utils.Session;
import com.example.todoapp.Utils.UserDBHelper;


public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputPassword;
    private UserDBHelper userDB;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDB = new UserDBHelper(this);
        session = new Session(this);
        inputEmail = (EditText) findViewById(R.id.etEmail);
        inputPassword = (EditText) findViewById(R.id.etPass);

        if (session.loggedin()) {
            moveToMain();
        }
    }

    public void moveToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void loginOnClick(View view) {
        String email = inputEmail.getText().toString();
        String pass = inputPassword.getText().toString();
        int userId = userDB.getIdUser(email, pass);

        if (userDB.checkUser(email, pass)) {
            session.setLoggedin(true);
            session.editor.putInt("user", userId);
            session.editor.apply();

            moveToMain();
        } else {
            displayToast("Wrong email/password");
        }
    }

    public void registerOnClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}