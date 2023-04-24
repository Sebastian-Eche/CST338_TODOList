package com.example.cst338_todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cst338_todolist.DB.AppDataBase;
import com.example.cst338_todolist.DB.TODOListDAO;
import com.example.cst338_todolist.DB.TODOListDAO_Impl;
import com.example.cst338_todolist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final String USER_ID_KEY = "com.example.cst338_todolist.userIdKey";
    private static final String PREFERENCE_KEY = "com.example.cst338_todolist.PREFERENCE_KEY";
    private SharedPreferences preferences = null;
    Button login;
    Button signup;
    TextView title;

    private TODOListDAO TODOListDAO;
    int userID = -1;
    ActivityMainBinding binding;

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TODOListDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().TODOListDAO();


//        checkForUser();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        login = binding.loginBTN;
        signup = binding.signupBTN;
        title = binding.todoListTitle;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForUser();
//               Intent intent = LoginActivity.intentFactory(getApplicationContext());
//               startActivity(intent);
            }
        });


    }

    private void checkForUser() {
        userID = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(userID != -1){
            return;
        }


        if(preferences == null){
            preferences = this.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        }

        userID = preferences.getInt(USER_ID_KEY, -1);

        if(userID != -1){
            return;
        }

        users = TODOListDAO.getAllUsers();
        if(users.size() <= 0){
            User predefinedUser = new User("seche", "seche123");
            TODOListDAO.insert(predefinedUser);
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("LOGOUT?");

        alertBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearUserFromIntent();
                clearUserFromPref();
            }
        });

        alertBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertBuilder.create().show();
    }

    private void clearUserFromIntent() {
    }

    private void clearUserFromPref() {

    }
}