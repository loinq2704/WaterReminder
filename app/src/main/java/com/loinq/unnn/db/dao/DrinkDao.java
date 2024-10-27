package com.loinq.unnn.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.loinq.unnn.db.entity.Drink;
import com.loinq.unnn.db.helper.DateConverter;

import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface DrinkDao {

    @Insert
    void insert(Drink... drink);

    @Update
    void update(Drink... drink);

    @Delete
    void delete(Drink... drink);

    @Query("SELECT * FROM drinks order by date desc")
    LiveData<List<Drink>> getAll();


}
