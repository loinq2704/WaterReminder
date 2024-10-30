package com.loinq.unnn.db.repository;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.loinq.unnn.db.MyRoomDatabase;
import com.loinq.unnn.db.dao.DrinkDao;
import com.loinq.unnn.db.entity.Drink;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public void addSampleData() {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            // Tạo dữ liệu mẫu
            Drink drink1 = new Drink(250, new Date());
            Drink drink2 = new Drink(500, new Date());
            Drink drink3 = new Drink(750, new Date());

            // Thêm dữ liệu vào database
            mDrinkDao.insert(drink1, drink2, drink3);
        });
    }

    public void exportToCSV(Context context, List<Drink> drinks) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            File csvFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "drinks.csv");
            try (FileWriter writer = new FileWriter(csvFile)) {
                writer.append("ID,Intake,Date\n");
                for (Drink drink : drinks) {
                    writer.append(String.valueOf(drink.getId()))
                            .append(',')
                            .append(String.valueOf(drink.getIntake()))
                            .append(',')
                            .append(new SimpleDateFormat("yyyy-MM-dd").format(drink.getDate()))
                            .append('\n');
                }
                Log.d("exportToCSV", "Data exported successfully to: " + csvFile.getAbsolutePath());
            } catch (IOException e) {
                Log.e("exportToCSV", "Failed to export data", e);
            }
        });
    }


}
