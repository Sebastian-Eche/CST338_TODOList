package com.example.cst338_todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_todolist.DB.AppDataBase;
import com.example.cst338_todolist.DB.TODOListDAO;
import com.example.cst338_todolist.databinding.ActivityAddTodoBinding;
import com.example.cst338_todolist.databinding.ActivityCreateAnAccountBinding;

import java.util.List;

public class AddTODO extends AppCompatActivity {

    private static final String PREFERENCE_KEY = "com.example.cst338_todolist.PREFERENCE_KEY";
    private SharedPreferences preferences = null;
    private static final String USER_ID_KEY = "com.example.cst338_todolist.userIdKey";
    private int userID = -1;

    Button addTODO;
    EditText addTitle;
    EditText addDescription;
    EditText addDate;
    EditText addTime;
    TextView addTODOTitle;

    String addTitleText;
    String addDescriptionText;
    String addDateText;
    String addTimeText;

    TODOListDAO TODOListDAO;

    User user;

    TODO todo;

    ActivityAddTodoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        TODOListDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().TODOListDAO();
        binding = ActivityAddTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkForUser();
        addUserToPreference(userID);

        user = TODOListDAO.getUserByUserID(userID);

        addTODO = binding.mainAddButton;
        addTitle = binding.todoTitle;
        addDescription = binding.todoDescription;
        addDate = binding.todoDate;
        addTime = binding.todoTime;
        addTODOTitle = binding.addTodo;


        addTODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTODO();
            }
        });

    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, AddTODO.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    private void addTODO(){
        addTimeText = addTime.getText().toString();
        addDescriptionText = addDescription.getText().toString();
        addTitleText = addTitle.getText().toString();
        addDateText = addDate.getText().toString();
        TODO newTodo;
        newTodo = new TODO(addTitleText, addDescriptionText, addDateText, addTimeText);
        TODOListDAO.insert(newTodo);
        Toast.makeText(getApplicationContext(), addTitleText + " ADDED TO TODO LIST", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "USERID: " + userID, Toast.LENGTH_LONG).show();
        Intent intent = LandingPage.intentFactory(getApplicationContext(), userID);
        startActivity(intent);
    }

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
    }

    private void addUserToPreference(int userID) {
        if(preferences == null){
            preferences = this.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userID);
    }

}