package com.loinq.unnn;


import android.content.Context;
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
import com.loinq.unnn.Notification.NotificationReceiver;
import com.loinq.unnn.fragment.HomeFragment;
import com.loinq.unnn.fragment.InformationFragment;
import com.loinq.unnn.fragment.SettingsFragment;
import com.loinq.unnn.util.Constant;


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

    private void bindingAcion() {
        bottomNavigationView.setOnItemSelectedListener(navListener);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof HomeFragment) {
            homeFragment = (HomeFragment) fragment;
        } else if (fragment instanceof InformationFragment) {
            informationFragment = (InformationFragment) fragment;
        } else if (fragment instanceof SettingsFragment) {
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
     NotificationReceiver.scheduleNextReminder(this);
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

                    if (item.getItemId() == R.id.nav_home)
                        selectedFragment = new HomeFragment(weight);
                    else if (item.getItemId() == R.id.nav_infor)
                        selectedFragment = new InformationFragment();
                    else if (item.getItemId() == R.id.nav_settings)
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
}
//    @SuppressLint("ScheduleExactAlarm")
//    private void scheduleReminder30p() {
//        // Intent để kích hoạt BroadcastReceiver
//        Intent intent = new Intent(this, NotificationReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//
//        // Thời gian thông báo (ví dụ: mỗi ngày vào 9:00 sáng)
////        Calendar calendar = Calendar.getInstance();
////        calendar.set(Calendar.HOUR_OF_DAY, 9);  // 9:00 sáng
////        calendar.set(Calendar.MINUTE, 0);
////        calendar.set(Calendar.SECOND, 0);
//        long triggerTime = System.currentTimeMillis() + 5000;
//        // Sử dụng AlarmManager để lên lịch nhắc nhở
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        if (alarmManager != null) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent); // Đặt alarm sau 5 giây
//        }
//        }
//    }



