package com.example.cst338_todolist.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.cst338_todolist.User;

import java.util.List;

@Dao
public interface TODOListDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User todoListLogs);

    @Query("SELECT * FROM " + AppDataBase.LOGIN_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.LOGIN_TABLE + " WHERE userId = :userID")
    User getTodoListLogByUserID(int userID);

    @Query("SELECT * FROM " + AppDataBase.LOGIN_TABLE + " WHERE username = :username")
    User getUserByUsername(String username);


}

