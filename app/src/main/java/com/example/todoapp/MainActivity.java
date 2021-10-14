package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.todoapp.Adapter.ToDoAdapter;
import com.example.todoapp.Login.LoginActivity;
import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.Utils.Session;
import com.example.todoapp.Utils.TaskDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private TaskDBHelper myDB;
    private List<ToDoModel> mList;
    private ToDoAdapter adapter;
    private Session session;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new TaskDBHelper(MainActivity.this);
        mList = new ArrayList<>();
        adapter = new ToDoAdapter(myDB, MainActivity.this);
        session = new Session(this);

        if (!session.loggedin()) {
            logout();
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
        userId = session.prefs.getInt("user", 0);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        mList = myDB.getAllTask(userId);
        Collections.reverse(mList);
        adapter.setTasks(mList);

        fab.setOnClickListener((v) -> {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = myDB.getAllTask(userId);
        Collections.reverse(mList);
        adapter.setTasks(mList);
        adapter.notifyDataSetChanged();
    }

    public void logout() {
        session.setLoggedin(false);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    public void logoutOnClick(View view) {
        logout();
    }
}