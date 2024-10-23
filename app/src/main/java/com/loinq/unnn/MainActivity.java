package com.loinq.unnn;

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

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private InformationFragment informationFragment;
    private SettingsFragment settingsFragment;
    private void bindingView() {
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        if (informationFragment == null)
            informationFragment = new InformationFragment();
        if (settingsFragment == null)
            settingsFragment = new SettingsFragment();
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
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, homeFragment)
                    .commit();
        }
    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if(item.getItemId() == R.id.nav_home)
                        selectedFragment = new HomeFragment();
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
}
