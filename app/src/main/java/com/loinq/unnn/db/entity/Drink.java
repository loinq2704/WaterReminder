package com.loinq.unnn.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.loinq.unnn.db.helper.DateConverter;

import java.util.Date;

@Entity(tableName = "drinks")
@TypeConverters({DateConverter.class})
public class Drink {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int intake;

    private Date date;

    public Drink(int intake, Date date) {
        this.intake = intake;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntake() {
        return intake;
    }

    public void setIntake(int intake) {
        this.intake = intake;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
