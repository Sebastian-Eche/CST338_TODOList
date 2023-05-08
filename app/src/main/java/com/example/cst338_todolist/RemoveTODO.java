package com.example.cst338_todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cst338_todolist.DB.AppDataBase;
import com.example.cst338_todolist.DB.TODOListDAO;
import com.example.cst338_todolist.databinding.ActivityLandingPageBinding;
import com.example.cst338_todolist.databinding.ActivityRemoveTodoBinding;

import java.util.ArrayList;
import java.util.List;

public class RemoveTODO extends AppCompatActivity {

    private static final String PREFERENCE_KEY = "com.example.cst338_todolist.PREFERENCE_KEY";
    private SharedPreferences preferences = null;
    private static final String USER_ID_KEY = "com.example.cst338_todolist.userIdKey";
    private int userID = -1;
    EditText removeByTitle;
    TextView todoList;
    TextView removeTODOTitle;
    Button removeTODOBTN;

    ActivityRemoveTodoBinding binding;

    String removeByTitleText;

    List<TODO> TODOList;

    TODOListDAO TODOListDAO;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_todo);

        TODOListDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().TODOListDAO();

        binding = ActivityRemoveTodoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checkForUser();
        addUserToPreference(userID);


        user = TODOListDAO.getUserByUserID(userID);


        removeByTitle = binding.removeTitle;
        removeTODOTitle = binding.removeTodoTitle;
        removeTODOBTN = binding.removeButton;
        todoList = binding.todoList;


        todoList.setMovementMethod(new ScrollingMovementMethod());
        showTodoList();


        removeTODOBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeTodo();
            }
        });

    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, RemoveTODO.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    private void showTodoList(){
        StringBuilder sb = new StringBuilder();
        TODOList = TODOListDAO.getAllTODOs();
        if(!TODOList.isEmpty()){
            for(int i = 0; i < TODOList.size(); i++){
                TODO todo = TODOList.get(i);
                sb.append(todo.toString());
            }
            todoList.setText(sb.toString());
        }
    }

    private void removeTodo(){
        removeByTitleText = removeByTitle.getText().toString();
        TODO todo = TODOListDAO.getTODObyTitle(removeByTitleText);
        TODOListDAO.delete(todo);
        Toast.makeText(getApplicationContext(), "TODO: " + todo.getTodoTitle() + " SUCCESSFULLY REMOVED", Toast.LENGTH_SHORT);
        Intent intent = LandingPage.intentFactory(getApplicationContext(), userID);
        startActivity(intent);
    }

    private void addUserToPreference(int userID) {
        if(preferences == null){
            preferences = this.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userID);
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

}