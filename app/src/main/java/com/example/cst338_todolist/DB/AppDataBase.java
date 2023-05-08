package com.example.cst338_todolist.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cst338_todolist.CalendarItems;
import com.example.cst338_todolist.TODO;
import com.example.cst338_todolist.User;


@Database(entities = {User.class, TODO.class, CalendarItems.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase {

    public static final String DATABASE_NAME = "TODOList.db";

    public static final String LOGIN_TABLE = "login_table";

    public static final String TODO_TABLE = "todo_table";

    public static final String CALENDAR_ITEMS_TABLE = "calendarItems_table";

    private static volatile AppDataBase instance;

    private static final Object LOCK = new Object();

    public abstract TODOListDAO TODOListDAO();

    public static AppDataBase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}

