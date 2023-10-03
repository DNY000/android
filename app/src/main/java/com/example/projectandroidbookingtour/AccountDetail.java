package com.example.projectandroidbookingtour;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projectandroidbookingtour.CartUser.Cart;
import com.example.projectandroidbookingtour.TourUser.ListTour;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountDetail extends AppCompatActivity {

    BottomNavigationView bnv;
    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        Intent intent = getIntent();
        String username = intent.getStringExtra("name");
        Toast.makeText(AccountDetail.this, username, Toast.LENGTH_SHORT).show();

        bnv = findViewById(R.id.bottom_nav);
        bnv.setSelectedItemId(R.id.action_me);
        bnv.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.action_home) {
                startActivity(new Intent(getApplicationContext(), ListTour.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.action_cart) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }else if (item.getItemId() == R.id.action_me) {
                return true;
            }
            return false;
        });
    }
}