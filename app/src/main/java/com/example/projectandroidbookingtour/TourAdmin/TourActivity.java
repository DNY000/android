package com.example.projectandroidbookingtour.TourAdmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectandroidbookingtour.CustomerAdmin.CustomerActivity;
import com.example.projectandroidbookingtour.Model.Tour;
import com.example.projectandroidbookingtour.NewsAdmin.NewsActivity;
import com.example.projectandroidbookingtour.R;
import com.example.projectandroidbookingtour.SearchTour;
import com.example.projectandroidbookingtour.Adapter.TourAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class TourActivity extends AppCompatActivity implements SearchTour.SearchTourListener {

    private static final String TABLE_NAME = "tbl_tour";
    BottomNavigationView bnv;

    ImageButton btnAdd, btnFind;
    ListView lv;
    
    TourAdapter tourAdapter;
    ArrayList<Tour> arrayList;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        bnv = findViewById(R.id.bottom_nav);
        btnAdd = findViewById(R.id.btnadd);
        btnFind = findViewById(R.id.btnfind);
        lv = findViewById(R.id.lv);

        loadListview("SELECT * FROM " + TABLE_NAME);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TourActivity.this, AddTourActivity.class));
            }
        });
        bnv.setSelectedItemId(R.id.action_tour);
        bnv.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.action_home) {
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.action_tour) {
                return true;
            } else if (item.getItemId() == R.id.action_me) {
                startActivity(new Intent(getApplicationContext(), CustomerActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TourActivity.this, DetailTourActivity.class);
                intent.putExtra("vitri", i);
                startActivity(intent);
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        SearchTour searchTour = new SearchTour();
        searchTour.show(getSupportFragmentManager(), "Search dialog");
    }

    private void loadListview (String sql) {
        Tour v = new Tour(TourActivity.this);
        arrayList = v.getAll(sql);
        tourAdapter = new TourAdapter(this, R.layout.layout_item_tour, arrayList);
        lv.setAdapter(tourAdapter);
    }

    @Override
    public void applyText(String txtSearch) {
        loadListview("Select * from " +TABLE_NAME + " where tentour like '%" +txtSearch + "%'");
    }
}