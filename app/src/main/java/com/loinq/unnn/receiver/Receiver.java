package com.loinq.unnn.receiver;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.loinq.unnn.db.entity.Drink;
import com.loinq.unnn.db.viewModel.DrinkViewModel;
import com.loinq.unnn.util.Constant;

import java.util.Date;

public class Receiver extends BroadcastReceiver {

    private DrinkViewModel mDrinkViewModel;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Save the current value of the variable
        mDrinkViewModel = new DrinkViewModel((Application)context.getApplicationContext());
        mDrinkViewModel.insert(new Drink(prefs.getInt(Constant.TODAY_INTAKE, 0), new Date()));

        // Reset the variable
        editor.putInt(Constant.TODAY_INTAKE, 0);  // Set variable to zero
        editor.apply();
    }
}
