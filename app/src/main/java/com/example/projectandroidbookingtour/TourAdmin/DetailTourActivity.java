package com.example.projectandroidbookingtour.TourAdmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.projectandroidbookingtour.Model.Tour;
import com.example.projectandroidbookingtour.R;

import java.util.ArrayList;

public class DetailTourActivity extends AppCompatActivity {

    private static final String TABLE_NAME = "tbl_tour";
    ImageButton btnEdit, btnDelete;
    ImageView imgTour;
    TextView txtTenTour, txtMoTaTour, txtThoiGian, txtGiaTour;
    int vitri;
    int delete;
    ArrayList<Tour>arrayList;

    private void confirmDelete (View view) {
        AlertDialog.Builder alertConfirm = new AlertDialog.Builder(this);
        alertConfirm.setTitle("Cảnh báo");
        alertConfirm.setMessage("Bạn thực sự muốn xóa?");
        alertConfirm.setCancelable(false);
        alertConfirm.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Tour tour = new Tour(DetailTourActivity.this);
                arrayList = tour.getAll("SELECT * FROM " + TABLE_NAME);
                delete = tour.delete(arrayList.get(vitri));
                onBackPressed();
            }
        });

        alertConfirm.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = alertConfirm.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(delete > 0) {
            Intent intent = new Intent(DetailTourActivity.this, TourActivity.class);
//             cờ FLAG_ACTIVITY_CLEAR_TASK sẽ xóa tất cả các activity trong hàng chờ, bao gồm cả activity hiện tại, và cờ FLAG_ACTIVITY_NEW_TASK sẽ tạo một task mới để chứa activity mới (TourActivity).
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);
        anhxa();
        Intent intent = getIntent();
        vitri = intent.getIntExtra("vitri", -1);
        Tour tour = new Tour(DetailTourActivity.this);
        arrayList = tour.getAll("SELECT * from " + TABLE_NAME);
        imgTour.setImageBitmap(arrayList.get(vitri).getImg());
        txtTenTour.setText(arrayList.get(vitri).getTentour());
        txtMoTaTour.setText(arrayList.get(vitri).getMota());
        txtThoiGian.setText(arrayList.get(vitri).getSongay()+"");
        txtGiaTour.setText(arrayList.get(vitri).getGia().toString());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete(view);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailTourActivity.this, EditTourActivity.class);
                i.putExtra("vitri", vitri);
                startActivity(i);
            }
        });
    }

    private void anhxa () {
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        imgTour = findViewById(R.id.imgTour);
        txtTenTour = findViewById(R.id.txtTenTour);
        txtMoTaTour = findViewById(R.id.txtMoTaTour);
        txtThoiGian = findViewById(R.id.txtThoiGian);
        txtGiaTour = findViewById(R.id.txtGiaTour);
    }
}