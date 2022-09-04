package com.jp.smartgram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    DoctorFragment doctorFragment = new DoctorFragment();
    FintechFragment fintechFragment = new FintechFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        bottomNavigationView.setSelectedItemId(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.f1fragment,homeFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.f1fragment, homeFragment).commit();
                return true;

            case R.id.doctor:
                getSupportFragmentManager().beginTransaction().replace(R.id.f1fragment, doctorFragment).commit();
                return true;

            case R.id.fintech:
                getSupportFragmentManager().beginTransaction().replace(R.id.f1fragment, fintechFragment).commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.f1fragment, profileFragment).commit();
                return true;
        }
        return false;
    }

}