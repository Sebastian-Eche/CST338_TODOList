package com.example.cst338_todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_todolist.DB.AppDataBase;
import com.example.cst338_todolist.DB.TODOListDAO;
import com.example.cst338_todolist.databinding.ActivityLandingPageBinding;

import java.util.List;

public class LandingPage extends AppCompatActivity {

    private static final String PREFERENCE_KEY = "com.example.cst338_todolist.PREFERENCE_KEY";
    TextView welcome;

    Button addTodo;
    Button removeTodo;
    Button removeUser;
    Button logout;
    Button calendar;
    private static final String USER_ID_KEY = "com.example.cst338_todolist.userIdKey";
    TODOListDAO TODOListDAO;

    User user;

    private SharedPreferences preferences = null;

    ActivityLandingPageBinding binding;

    private int userID = -1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);


        TODOListDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().TODOListDAO();

        checkForUser();
//        checkForUserInDatabase();
        addUserToPreference(userID);

//        loginUser(userID);

        user = TODOListDAO.getUserByUserID(userID);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        logout = binding.logoutBTN;
        welcome = binding.welcomeTitle;
        addTodo = binding.addBTN;
        removeUser = binding.removeUserBTN;
        removeTodo = binding.removeBTN;
        calendar = binding.calendar;

        if(user.getAdmin().equals("yes")) {
            welcome.setText("Welcome " + user.getUsername() + "\n" + "ADMIN SCREEN");
            addTodo.setVisibility(View.INVISIBLE);
        }else{
            welcome.setText("Welcome " + user.getUsername() + "\n" + "USER SCREEN ");
            removeTodo.setVisibility(View.INVISIBLE);
            removeUser.setVisibility(View.INVISIBLE);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =  AddTODO.intentFactory(getApplicationContext(), userID);
               startActivity(intent);
            }
        });

        removeTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RemoveTODO.intentFactory(getApplicationContext(), userID);
                startActivity(intent);
            }
        });

        removeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RemoveUser.intentFactory(getApplicationContext(), userID);
                startActivity(intent);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Calendar.intentFactory(getApplicationContext(), userID);
                startActivity(intent);
            }
        });

    }



    private void loginUser(int userID) {
        user = TODOListDAO.getUserByUserID(userID);
        invalidateOptionsMenu();

    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu){
//        if(user != null){
//            MenuItem item = menu.findItem(R.id.logout);
//            item.setTitle(user.getUsername());
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }

    private void checkForUser() {
        userID = getIntent().getIntExtra(USER_ID_KEY, -1);

        if (userID != -1) {
            return;
        }


        if (preferences == null) {
            preferences = this.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        }
            userID = preferences.getInt(USER_ID_KEY, -1);


        if (userID != -1) {
            return;
        }

        List<User> users = TODOListDAO.getAllUsers();
        if (users.size() <= 0) {
            User predefinedUser = new User("seche", "seche123", "no");
            TODOListDAO.insert(predefinedUser);
        }

        Toast.makeText(getApplicationContext(), "USER ID: " + userID, Toast.LENGTH_LONG).show();

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }


    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("LOGOUT?");

        alertBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearUserFromIntent();
                clearUserFromPref();
                userID = -1;
                checkForUser();
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
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
        addUserToPreference(-1);
    }

    private void addUserToPreference(int userID) {
        if(preferences == null){
            preferences = this.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userID);
    }

//    private boolean checkForUserInDatabase(){
//        String usernameText = user.getUsername();
//        user = TODOListDAO.getUserByUsername(usernameText);
//
//        if(user == null){
//            Toast.makeText(this, "no user" + usernameText + " found ", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
//    }


}