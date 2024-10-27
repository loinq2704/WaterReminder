package com.loinq.unnn.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.loinq.unnn.db.MyRoomDatabase;
import com.loinq.unnn.db.dao.DrinkDao;
import com.loinq.unnn.db.entity.Drink;

import java.util.List;

public class DrinkRepository {

    private DrinkDao mDrinkDao;

    public DrinkRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mDrinkDao = db.drinkDao();
    }

    public LiveData<List<Drink>> getAll() {
        return mDrinkDao.getAll();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Drink drink) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDrinkDao.insert(drink);
        });
    }
}
