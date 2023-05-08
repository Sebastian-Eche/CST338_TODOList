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
import com.example.cst338_todolist.databinding.ActivityRemoveTodoBinding;
import com.example.cst338_todolist.databinding.ActivityRemoveUserBinding;

import java.util.List;

public class RemoveUser extends AppCompatActivity {

    private static final String PREFERENCE_KEY = "com.example.cst338_todolist.PREFERENCE_KEY";
    private SharedPreferences preferences = null;
    private static final String USER_ID_KEY = "com.example.cst338_todolist.userIdKey";
    private int userID = -1;

    TextView removeUserTitle;
    TextView ListofUsers;
    EditText removeUserByUsername;
    Button deleteUser;

    String removeUserByUsernameText;
    List<User> userList;
    ActivityRemoveUserBinding binding;
    TODOListDAO TODOListDAO;

    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_user);

        TODOListDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().TODOListDAO();

        user = TODOListDAO.getUserByUserID(userID);

        binding = ActivityRemoveUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checkForUser();
        addUserToPreference(userID);

        removeUserTitle = binding.removeUserTitle;
        ListofUsers = binding.userList;
        removeUserByUsername = binding.removeUsername;
        deleteUser = binding.removeUserByUsernameBTN;


        ListofUsers.setMovementMethod(new ScrollingMovementMethod());
        showTodoList();

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeUser();
            }
        });


    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, RemoveUser.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    private void removeUser(){
        removeUserByUsernameText = removeUserByUsername.getText().toString();
        User user = TODOListDAO.getUserByUsername(removeUserByUsernameText);
        TODOListDAO.delete(user);
        Toast.makeText(getApplicationContext(), "TODO: " + user.getUsername()  + " SUCCESSFULLY REMOVED", Toast.LENGTH_SHORT);
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

    private void showTodoList(){
        StringBuilder sb = new StringBuilder();
        userList = TODOListDAO.getAllUsers();
        if(!userList.isEmpty()){
            for(int i = 0; i < userList.size(); i++){
                User forUser = userList.get(i);
                sb.append(forUser.toString());
            }
            ListofUsers.setText(sb.toString());
        }
    }
}