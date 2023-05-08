package com.example.cst338_todolist.DB;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ProviderInfo;
import android.media.ApplicationMediaCapabilities;

import androidx.room.Database;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.cst338_todolist.Calendar;
import com.example.cst338_todolist.CalendarItems;
import com.example.cst338_todolist.TODO;
import com.example.cst338_todolist.User;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.security.Provider;
import java.util.List;
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class TODOListDAOTest extends TestCase {

    TODOListDAO TODOListDAO;

    AppDataBase database;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).allowMainThreadQueries().build();
//        TODOListDAO = Room.databaseBuilder(context, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().TODOListDAO();
        TODOListDAO = database.TODOListDAO();

        super.setUp();
    }

    @After
    public void tearDown() throws IOException {
        database.close();

    }
    @Test
    public void testInsertUser() throws Exception{
        User user = new User("testUser", "testUser123", "no");
        TODOListDAO.insert(user);
//        User getUser = TODOListDAO.getUserByUsername("testUser");
//        assertEquals(getUser.getUsername(), user.getUsername());
//        assertEquals(getUser.getUserId(), );
        List<User> userList = TODOListDAO.getAllUsers();
        assertTrue("Size is greater than 0", userList.size() > 0);
    }

    @Test
    public void testUpdateUser() {
        User user = new User("testUser1", "testUser1234", "no");
        TODOListDAO.insert(user);
        List<User> userList = TODOListDAO.getAllUsers();
        user = new User("testUser2", "testUser12345", "yes");
        TODOListDAO.update(user);
        userList = TODOListDAO.getAllUsers();
//        assertEquals();
        assertNotSame(user, userList.get(0));

    }

    @Test
    public void testDeleteUser() {
        User user = new User("testUser2", "testUser12345", "no");
        assertNotNull(user);
        TODOListDAO.delete(user);
        User getUser = TODOListDAO.getUserByUsername("testUser2");
        assertNull(getUser);
    }
    @Test
    public void testInsertTODO() {
        TODO todo = new TODO("testTODO", "test", "test", "test", 1);
        TODOListDAO.insert(todo);
        List<TODO> todoList = TODOListDAO.getAllTODOs();
        assertTrue("TODOList is greater than 0", todoList.size() > 0);
    }
    @Test
    public void testUpdateTODO() {
        TODO todo = new TODO("testTODO1", "test", "test", "test", 5);
        TODOListDAO.insert(todo);
        List<TODO> todoList = TODOListDAO.getAllTODOs();
        todo = new TODO("testTODO2", "test2", "test2", "test2", 3);
        TODOListDAO.update(todo);
        todoList = TODOListDAO.getAllTODOs();
        assertNotSame(todo, todoList.get(0));


    }

    @Test
    public void testDeleteTODO() {
        TODO todo = new TODO("testTODO2", "test2", "test2", "test2", 3);
        assertNotNull(todo);
        TODOListDAO.insert(todo);
        List<TODO> todoList = TODOListDAO.getAllTODOs();
//        System.out.println(todoList.get(0));
        TODOListDAO.delete(todoList.get(0));
        todoList = TODOListDAO.getAllTODOs();
        assertTrue("todoList size is equal to 0", todoList.size() == 0);

    }

    @Test
    public void testInsertCalendarItems() {
        CalendarItems calendarItems = new CalendarItems(3,1);
        TODOListDAO.insert(calendarItems);
//        List<CalendarItems> getCalendarItems = TODOListDAO.getAllCalendarItems();
//        assertEquals(calendarItems, getCalendarItems.get(0));
        List<CalendarItems> calendarItemsList = TODOListDAO.getAllCalendarItems();
        assertTrue("CalendarItemsList is greater than 0", calendarItemsList.size() > 0);
    }
    @Test
    public void testUpdateCalendarItems() {
        CalendarItems calendarItems = new CalendarItems(9,4);
        TODOListDAO.insert(calendarItems);
        List<CalendarItems> calendarItemsList = TODOListDAO.getAllCalendarItems();
        calendarItems = new CalendarItems(4,9);
        TODOListDAO.update(calendarItems);
        calendarItemsList = TODOListDAO.getAllCalendarItems();
        assertNotSame(calendarItems, calendarItemsList.get(0));

    }
    @Test
    public void testDeleteCalendarItems() {
        CalendarItems calendarItems = new CalendarItems(5,2);
        assertNotNull(calendarItems);
        TODOListDAO.insert(calendarItems);
        List<CalendarItems> calendarItemsList = TODOListDAO.getAllCalendarItems();
        TODOListDAO.delete(calendarItemsList.get(0));
        calendarItemsList = TODOListDAO.getAllCalendarItems();
        assertTrue("calendarItemsList size is equal to 0", calendarItemsList.size() == 0);
    }
}