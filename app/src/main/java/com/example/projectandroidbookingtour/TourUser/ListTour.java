package com.example.projectandroidbookingtour.TourUser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectandroidbookingtour.AccountDetail;
import com.example.projectandroidbookingtour.CartUser.Cart;
import com.example.projectandroidbookingtour.Model.Tour;
import com.example.projectandroidbookingtour.R;
import com.example.projectandroidbookingtour.SearchTour;
import com.example.projectandroidbookingtour.Adapter.TourAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ListTour extends AppCompatActivity implements SearchTour.SearchTourListener {
    private static final String TABLE_NAME = "tbl_tour";
    TourAdapter tourAdapter;
    ArrayList<Tour> arrayList;
    ListView lv;
    ImageButton btnSearch;

    BottomNavigationView bnv;
    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tour);
        lv = findViewById(R.id.lvtour);
        btnSearch = findViewById(R.id.btnsearchtour);
        Intent i = getIntent();
        String userName = i.getStringExtra("username");
        loadListview("SELECT * FROM " + TABLE_NAME);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListTour.this, DetailTourUserActivity.class);
                intent.putExtra("vitri", i);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        bnv = findViewById(R.id.bottom_nav);
        bnv.setSelectedItemId(R.id.action_home);
        bnv.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.action_home) {
                return true;
            } else if (item.getItemId() == R.id.action_cart) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }else if (item.getItemId() == R.id.action_me) {
                Intent intent = new Intent(getApplicationContext(), AccountDetail.class);
                intent.putExtra("name", userName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });


    }

    private void openDialog() {
        SearchTour searchTour = new SearchTour();
        searchTour.show(getSupportFragmentManager(), "Search dialog");
    }

    private void loadListview (String sql) {
        Tour v = new Tour(ListTour.this);
        arrayList = v.getAll(sql);
        tourAdapter = new TourAdapter(this, R.layout.layout_item_tour, arrayList);
        lv.setAdapter(tourAdapter);
    }

    @Override
    public void applyText(String txtSearch) {
        loadListview("Select * from " +TABLE_NAME + " where tentour like '%" +txtSearch + "%'");
    }
}