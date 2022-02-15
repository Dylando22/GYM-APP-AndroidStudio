package com.dspencer.gogogym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;

import com.dspencer.gogogym.fragments.CalenderFragment;
import com.dspencer.gogogym.fragments.WorkoutsFragment;
import com.dspencer.gogogym.fragments.DefaultFragment;
import com.dspencer.gogogym.fragments.HomeFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DrawerLayout drawerLayout = findViewById(R.id.Drawer);
        MaterialToolbar toolBar = findViewById(R.id.topAppBar);
        NavigationView navigationView = findViewById(R.id.NavagationView);
        Button def = findViewById(R.id.Default);
        Button Start = findViewById(R.id.Starter);
        toolBar.setNavigationOnClickListener(v -> {
            drawerLayout.openDrawer(Gravity.LEFT);
        });

        navigationView.setNavigationItemSelectedListener( menuItem -> {
                    menuItem.setChecked(true);
                    if (menuItem.getItemId() == R.id.menu_calender){
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.HomeFragment, CalenderFragment.class,null)
                                .commit();
                    }
                    else if (menuItem.getItemId() == R.id.menu_create){
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.HomeFragment, WorkoutsFragment.class,null)
                                .commit();
                    }
                    else if (menuItem.getItemId() == R.id.menu_default){
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.HomeFragment, DefaultFragment.class,null)
                                .commit();
                    }
                    else if (menuItem.getItemId() == R.id.menu_home){
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.HomeFragment, HomeFragment.class,null)
                                .commit();
                    }
                    return true;
                }
        );

    }
}