package com.example.projectandroidbookingtour.CartUser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.projectandroidbookingtour.AccountDetail;
import com.example.projectandroidbookingtour.R;
import com.example.projectandroidbookingtour.TourUser.ListTour;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Cart extends AppCompatActivity {

    BottomNavigationView bnv;
    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        bnv = findViewById(R.id.bottom_nav);
        bnv.setSelectedItemId(R.id.action_cart);
        bnv.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.action_home) {
                startActivity(new Intent(getApplicationContext(), ListTour.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.action_cart) {
                return true;
            }else if (item.getItemId() == R.id.action_me) {
                startActivity(new Intent(getApplicationContext(), AccountDetail.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }
}