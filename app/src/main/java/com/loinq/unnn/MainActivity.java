package com.loinq.unnn;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.loinq.unnn.fragment.HomeFragment;
import com.loinq.unnn.fragment.InformationFragment;
import com.loinq.unnn.fragment.SettingsFragment;
import com.loinq.unnn.receiver.Receiver;
import com.loinq.unnn.util.Constant;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment;
    private InformationFragment informationFragment;
    private SettingsFragment settingsFragment;

    private SharedPreferences sharedPreferences;

    private int weight = 0;

    private void bindingView() {
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        if (homeFragment == null)
            homeFragment = new HomeFragment(weight);
        if (informationFragment == null)
            informationFragment = new InformationFragment();
        if (settingsFragment == null)
            settingsFragment = new SettingsFragment();

        sharedPreferences = getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        weight = sharedPreferences.getInt(Constant.WEIGHT, 0);
    }

    private void bindingAcion(){
        bottomNavigationView.setOnItemSelectedListener(navListener);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof HomeFragment) {
            homeFragment = (HomeFragment) fragment;
        }
        else if (fragment instanceof InformationFragment) {
            informationFragment = (InformationFragment) fragment;
        }
        else if (fragment instanceof SettingsFragment) {
            settingsFragment = (SettingsFragment) fragment;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAcion();
        scheduleDailyReset();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, weight == 0 ? settingsFragment : homeFragment)
                    .commit();
        }
    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if(item.getItemId() == R.id.nav_home)
                        selectedFragment = new HomeFragment(weight);
                    else if(item.getItemId() == R.id.nav_infor)
                        selectedFragment = new InformationFragment();
                    else if(item.getItemId() == R.id.nav_settings)
                        selectedFragment = new SettingsFragment();

                    // Load the selected fragment
                    if (selectedFragment != null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainerView, selectedFragment)
                                .commit();
                    }


                    return true;
                }
            };

    private void scheduleDailyReset() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);   // Set the time to midnight
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}
