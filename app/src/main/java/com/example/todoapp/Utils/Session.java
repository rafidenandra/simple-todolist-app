package com.example.todoapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;


public class Session {
    public SharedPreferences prefs;
    public SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin) {
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);
    }

//    public void setEmail(String email) {
//        editor.putString("email", email);
//        editor.commit();
//    }
//
//    public String getEmail() {
//        return prefs.getString("email", "");
//    }
}
