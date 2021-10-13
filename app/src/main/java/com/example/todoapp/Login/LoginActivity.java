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
    private EditText etEmail, etPass;
    private UserDBHelper db;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new UserDBHelper(this);
        session = new Session(this);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);

        if (session.loggedin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    public void loginOnClick(View view) {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        int user_id = db.getIdUser(email, pass);

        if (db.checkUser(email, pass)) {
//        if (user_id) {
            session.setLoggedin(true);
            session.editor.putInt("user", user_id);
            session.editor.apply();
//            session.setEmail(email);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong email/password",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void registerOnClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}