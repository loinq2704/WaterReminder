package com.loinq.unnn.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.loinq.unnn.util.Constant;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Reset the variable
        SharedPreferences prefs = context.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constant.TODAY_INTAKE, 0);  // Set variable to zero
        editor.apply();
    }
}
