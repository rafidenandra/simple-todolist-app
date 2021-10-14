package com.example.todoapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;


public class Session {
    public SharedPreferences prefs;
    public SharedPreferences.Editor editor;
    Context context;

    public Session(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin) {
        editor.putBoolean("loggedInmode", logggedin);
        editor.commit();
    }

    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);
    }
}
