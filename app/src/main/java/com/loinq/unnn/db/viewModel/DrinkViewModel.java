package com.loinq.unnn.db.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.loinq.unnn.db.entity.Drink;
import com.loinq.unnn.db.repository.DrinkRepository;

import java.util.List;

public class DrinkViewModel extends AndroidViewModel {
    
    private DrinkRepository mRepository;

    private final LiveData<List<Drink>> mAllDrinks;

    public DrinkViewModel (Application application) {
        super(application);
        mRepository = new DrinkRepository(application);
        mAllDrinks = mRepository.getAll();
    }

    LiveData<List<Drink>> getAllDrinks() { return mAllDrinks; }

    public void insert(Drink word) { mRepository.insert(word); }

    public void exportToCSV(Context context) {
        mRepository.exportToCSV(context);
    }
    public void addSampleData() {
        mRepository.addSampleData();
    }
}
