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
import com.example.cst338_todolist.databinding.ActivityCreateAnAccountBinding;
import com.example.cst338_todolist.databinding.ActivityLoginActvityBinding;

import java.util.List;

public class CreateAnAccount extends AppCompatActivity {

    private static final String PREFERENCE_KEY = "com.example.cst338_todolist.PREFERENCE_KEY";
    private SharedPreferences preferences = null;
    private static final String USER_ID_KEY = "com.example.cst338_todolist.userIdKey";
    private int userID = -1;
    TextView title;
    EditText username;
    EditText password;
    EditText retyped;
    Button submit;

    String passwordText;
    String retypedText;
    String usernameText;

    TODOListDAO TodoListDAO;

    ActivityCreateAnAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_an_account);

        TodoListDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().TODOListDAO();
        binding = ActivityCreateAnAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title = binding.signupTitle;
        username = binding.usernameCAA;
        password = binding.passwordCAA;
        retyped = binding.retypePassword;
        submit = binding.mainSubmitButton;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validatePassword()){
                    addUser();
                }
            }
        });


    }


    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, CreateAnAccount.class);
        return intent;
    }

    private boolean validatePassword() {
        passwordText = password.getText().toString();
        retypedText = retyped.getText().toString();
        usernameText = username.getText().toString();
        if (passwordText.equals(retypedText) && passwordCharacters()) {
            return true;
        } else if(!passwordText.equals(retypedText)){
            Toast.makeText(getApplicationContext(), "PASSWORDS DO NOT MATCH", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return false;
        }
    }

    private boolean passwordCharacters(){
        if(passwordText.length() >= 6){
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "PASSWORD MUST BE 6 CHARACTERS LONG", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void addUser() {
        if (validatePassword()) {
            User newUser;
            if (passwordText.equals("s3cr3t")) {
                newUser = new User(usernameText, passwordText, "yes");
                Toast.makeText(getApplicationContext(), "ADMIN ADDED", Toast.LENGTH_SHORT).show();
            } else {
                newUser = new User(usernameText, passwordText, "no");
                Toast.makeText(getApplicationContext(), "USER ADDED", Toast.LENGTH_SHORT).show();
            }
            TodoListDAO.insert(newUser);
            Intent intent = LoginActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        }
        Intent intent = LoginActivity.intentFactory(getApplicationContext());
        startActivity(intent);
//        Toast.makeText(getApplicationContext(), "PASSWORDS DO NOT MATCH", Toast.LENGTH_SHORT).show();
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

        List<User> users = TodoListDAO.getAllUsers();
        if (users.size() <= 0) {
            User predefinedUser = new User("seche", "seche123", "no");
            TodoListDAO.insert(predefinedUser);
        }
    }
}