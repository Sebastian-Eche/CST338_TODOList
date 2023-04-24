package com.example.cst338_todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cst338_todolist.DB.AppDataBase;
import com.example.cst338_todolist.DB.TODOListDAO;
import com.example.cst338_todolist.databinding.ActivityLandingPageBinding;

import java.util.List;

public class LandingPage extends AppCompatActivity {

    private static final String PREFERENCE_KEY = "com.example.cst338_todolist.PREFERENCE_KEY";
    TextView welcome;

    Button addTodo;
    Button removeTodo;
    private static final String USER_ID_KEY = "com.example.cst338_todolist.userIdKey";
    TODOListDAO TODOListDAO;

    User user;

    private SharedPreferences preferences = null;

    ActivityLandingPageBinding binding;

    private int userID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);


        TODOListDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().TODOListDAO();

        checkForUser();

        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        welcome = binding.welcomeTitle;
        addTodo = binding.addBTN;
        removeTodo = binding.removeBTN;


    }

    private void loginUser(int userID) {
        user = TODOListDAO.getTodoListLogByUserID(userID);
        invalidateOptionsMenu();
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

        List<User> users = TODOListDAO.getAllUsers();
        if(users.size() <= 0){
            User predefinedUser = new User("seche", "seche123");
            TODOListDAO.insert(predefinedUser);
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}