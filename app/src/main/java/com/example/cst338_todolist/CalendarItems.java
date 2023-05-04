package com.example.cst338_todolist;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338_todolist.DB.AppDataBase;

@Entity(tableName = AppDataBase.CALENDAR_ITEMS_TABLE)
public class CalendarItems {


    @PrimaryKey(autoGenerate = true)
    private Integer itemID;

    private Integer calendarItemsSize = 0;

    private Integer calendarItemID;

    public CalendarItems(Integer calendarItemsSize, Integer calendarItemID) {
        this.calendarItemsSize = calendarItemsSize;
        this.calendarItemID = calendarItemID;
    }

    public Integer addingTODO(){
        return calendarItemsSize + 1;
    }


    @Override
    public String toString() {
        return "Calendar Size: " + calendarItemsSize;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public Integer getCalendarItemsSize() {
        return calendarItemsSize;
    }

    public void setCalendarItemsSize(Integer calendarItemsSize) {
        this.calendarItemsSize = calendarItemsSize;
    }

    public Integer getCalendarItemID() {
        return calendarItemID;
    }

    public void setCalendarItemID(Integer calendarItemID) {
        this.calendarItemID = calendarItemID;
    }
}
