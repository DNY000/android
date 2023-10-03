package com.example.projectandroidbookingtour.TourUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectandroidbookingtour.BookingTour.BookingTour;
import com.example.projectandroidbookingtour.Model.Tour;
import com.example.projectandroidbookingtour.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class DetailTourUserActivity extends AppCompatActivity {

    private static final String TABLE_NAME = "tbl_tour";
    ImageView imgTour;
    TextView txtTenTour, txtMoTaTour, txtThoiGian, txtGiaTour;
    int vitri;
    ArrayList<Tour> arrayList;
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour_user);
        anhxa();
        Intent intent = getIntent();
        vitri = intent.getIntExtra("vitri", -1);
        Tour tour = new Tour(DetailTourUserActivity.this);
        arrayList = tour.getAll("SELECT * from " + TABLE_NAME);
        imgTour.setImageBitmap(arrayList.get(vitri).getImg());
        txtTenTour.setText(arrayList.get(vitri).getTentour());
        txtMoTaTour.setText(arrayList.get(vitri).getMota());
        txtThoiGian.setText(arrayList.get(vitri).getSongay()+"");
        txtGiaTour.setText(arrayList.get(vitri).getGia().toString());

        bnv.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_add_cart) {
//              Code chức năng thêm tour vào giỏ hàng
                return true;
            } else if (item.getItemId() == R.id.action_booking_tour) {
                startActivity(new Intent(getApplicationContext(), BookingTour.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }

    private void anhxa () {
        imgTour = findViewById(R.id.imgTourUser);
        txtTenTour = findViewById(R.id.txtTenTourUser);
        txtMoTaTour = findViewById(R.id.txtMoTaTourUser);
        txtThoiGian = findViewById(R.id.txtThoiGianUser);
        txtGiaTour = findViewById(R.id.txtGiaTourUser);
        bnv = findViewById(R.id.bottom_menu_tour);
    }
}