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
import android.widget.Toast;

import com.example.cst338_todolist.DB.AppDataBase;
import com.example.cst338_todolist.DB.TODOListDAO;
import com.example.cst338_todolist.databinding.ActivityLoginActvityBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.cst338_todolist.userIdKey";

    Button submitBTN;
    Button signUP;
    EditText enterPass;
    EditText enterUser;
    private TODOListDAO TODOListDAO;
    String usernameText;
    String passwordText;



    User user;
    List<User> mTODOListLogList;

    private SharedPreferences preferences = null;

    private int userID = -1;

    ActivityLoginActvityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);

        binding = ActivityLoginActvityBinding.inflate(getLayoutInflater());

        TODOListDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().TODOListDAO();



        setContentView(binding.getRoot());

        submitBTN = binding.mainSubmitButton;
//        signUP = binding.SignUpButton;
        enterPass = binding.password;
        enterUser = binding.username;

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameText = enterUser.getText().toString();
                passwordText = enterPass.getText().toString();
                if(checkForUserInDatabase()) {
                    if (!validatePassword()) {
                        Toast.makeText(LoginActivity.this, "INVALID PASSWORD", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = LandingPage.intentFactory(getApplicationContext(), user.getUserId());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    private boolean checkForUserInDatabase(){
        user = TODOListDAO.getUserByUsername(usernameText);

        if(user == null){
            Toast.makeText(this, "no user" + usernameText + " found ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePassword(){
        return user.getPassword().equals(passwordText);
    }


    private void checkForUser() {
        userID = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(userID != -1){
            return;
        }


        if(preferences == null){
            preferences = this.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE);
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





}