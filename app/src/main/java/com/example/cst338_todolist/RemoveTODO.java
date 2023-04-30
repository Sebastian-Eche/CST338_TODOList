package com.example.cst338_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cst338_todolist.DB.TODOListDAO;
import com.example.cst338_todolist.databinding.ActivityRemoveTodoBinding;

public class RemoveTODO extends AppCompatActivity {

    EditText removeByTitle;
    TextView removeTODOTitle;
    Button removeTODOBTN;

    ActivityRemoveTodoBinding binding;

    String removeByTitleText;

    TODOListDAO TODOListDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_todo);

        removeByTitle = binding.removeTitle;
        removeTODOTitle = binding.removeTodoTitle;
        removeTODOBTN = binding.removeButton;


        removeTODOBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeTodo();
            }
        });

    }

    private void removeTodo(){
        removeByTitleText = removeByTitle.getText().toString();
        TODO todo = TODOListDAO.getTODObyTitle(removeByTitleText);
        TODOListDAO.delete(todo);
        Intent intent = new Intent(getApplicationContext(), LandingPage.class);
        startActivity(intent);
    }

}