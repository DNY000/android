package com.example.projectandroidbookingtour.NewsAdmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectandroidbookingtour.CustomerAdmin.CustomerActivity;
import com.example.projectandroidbookingtour.R;
import com.example.projectandroidbookingtour.TourAdmin.TourActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewsActivity extends AppCompatActivity {

    BottomNavigationView bnv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        bnv = findViewById(R.id.bottom_nav);
        bnv.setSelectedItemId(R.id.action_home);
        bnv.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.action_home) {
                return true;
            } else if (item.getItemId() == R.id.action_tour) {
                startActivity(new Intent(getApplicationContext(), TourActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.action_me) {
                startActivity(new Intent(getApplicationContext(), CustomerActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }
}