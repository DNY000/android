package com.example.projectandroidbookingtour.CustomerAdmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectandroidbookingtour.NewsAdmin.NewsActivity;
import com.example.projectandroidbookingtour.R;
import com.example.projectandroidbookingtour.TourAdmin.TourActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        bnv = findViewById(R.id.bottom_nav);
        bnv.setSelectedItemId(R.id.action_me);
        bnv.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.action_home) {
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.action_tour) {
                startActivity(new Intent(getApplicationContext(), TourActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.action_me) {
                return true;
            }
            return false;
        });
    }
}