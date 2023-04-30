package com.example.cst338_todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338_todolist.DB.AppDataBase;


@Entity(tableName = AppDataBase.LOGIN_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private Integer userId;
    private String username;
    private String password;
    private String admin;


    public User(String username, String password, String admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

