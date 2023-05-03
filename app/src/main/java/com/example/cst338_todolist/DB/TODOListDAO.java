package com.example.cst338_todolist.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.cst338_todolist.TODO;
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
    User getUserByUserID(int userID);

    @Query("SELECT * FROM " + AppDataBase.LOGIN_TABLE + " WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM todo_table JOIN login_table ON login_table.userId = todo_table.todoID WHERE todoID  = :userID ")
    List<TODO> getUserTODOItems(Integer userID);


    @Insert
    void insert(TODO... todos);

    @Update
    void update(TODO... todos);

    @Delete
    void delete(TODO todo);

    @Query("SELECT * FROM " + AppDataBase.TODO_TABLE)
    List<TODO> getAllTODOs();

    @Query("SELECT * FROM " + AppDataBase.TODO_TABLE + " WHERE todoTitle = :todoTitle")
    TODO getTODObyTitle(String todoTitle);

    @Query("SELECT * FROM " + AppDataBase.TODO_TABLE + " WHERE todoDate = :todoDate")
    TODO getTODObyDate(String todoDate);

}

