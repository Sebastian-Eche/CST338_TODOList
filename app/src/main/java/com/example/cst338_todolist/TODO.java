package com.example.cst338_todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338_todolist.DB.AppDataBase;

@Entity(tableName = AppDataBase.TODO_TABLE)
public class TODO {
    @PrimaryKey(autoGenerate = true)
    private Integer itemId;
    private String todoTitle;
    private String todoDescription;
    private String todoDate;
    private String todoTime;

    private Integer todoID;

    public TODO(String todoTitle, String todoDescription, String todoDate, String todoTime, Integer todoID) {
        this.todoTitle = todoTitle;
        this.todoDescription = todoDescription;
        this.todoDate = todoDate;
        this.todoTime = todoTime;
        this.todoID = todoID;
    }

    public Integer getTodoID() {
        return todoID;
    }

    public void setTodoID(Integer todoID) {
        this.todoID = todoID;
    }

    @Override
    public String toString() {
        return "TODO: " + todoTitle + "\n";
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getTodoDescription() {
        return todoDescription;
    }

    public void setTodoDescription(String todoDescription) {
        this.todoDescription = todoDescription;
    }

    public String getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(String todoDate) {
        this.todoDate = todoDate;
    }

    public String getTodoTime() {
        return todoTime;
    }

    public void setTodoTime(String todoTime) {
        this.todoTime = todoTime;
    }
}
