package com.example.projectandroidbookingtour.TourAdmin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.projectandroidbookingtour.Model.Tour;
import com.example.projectandroidbookingtour.R;

import java.util.ArrayList;

public class EditTourActivity extends AppCompatActivity {

    private static final int CODE_CAM = 123;
    private static final int IMGCODE = 124;
    private static final int GALLERY_REQUEST_CODE = 236;
    EditText txtEditName, txtEditDesc,txtEditTime, txtEditPrice;
    ImageButton imgEditCamera, imgEditFolder;
    ImageView imgEditview;
    Button btnSave;
    Bitmap bitmap;
    int vitri;
    ArrayList<Tour>arrayList;
    private static final String TABLE_NAME = "tbl_tour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tour);
        anhxa();
        Intent i = getIntent();
        vitri = i.getIntExtra("vitri", -1);
        Tour tour = new Tour(EditTourActivity.this);
        arrayList = tour.getAll("SELECT * from " + TABLE_NAME);
        txtEditName.setText(arrayList.get(vitri).getTentour());
        txtEditDesc.setText(arrayList.get(vitri).getMota());
        txtEditPrice.setText(arrayList.get(vitri).getGia().toString());
        txtEditTime.setText(arrayList.get(vitri).getSongay()+"");
        imgEditview.setImageBitmap(arrayList.get(vitri).getImg());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtEditName.getText().toString();
                String desc = txtEditDesc.getText().toString();
                int soNgay = Integer.parseInt(txtEditTime.getText().toString());
                Double gia = Double.valueOf(txtEditPrice.getText().toString());
                Bitmap bitmap = GetImage(imgEditview);
                Tour t = new Tour(EditTourActivity.this);
                Long update = t.update(new Tour(1, name, soNgay, bitmap, desc, gia), arrayList.get(vitri).getId());
                if(update != -1) {
                    Toast.makeText(EditTourActivity.this, "Sửa thông tin tour thành công", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(EditTourActivity.this, "Sửa thông tin tour thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgEditCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cameraPermission = ContextCompat.checkSelfPermission(EditTourActivity.this, Manifest.permission.CAMERA);
                if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
                    // Ứng dụng đã có quyền truy cập camera, mở camera
                    openCamera();
                } else {
                    // Ứng dụng chưa có quyền truy cập camera, xin cấp quyền
                    confirmPermission();
                }
            }
        });

        imgEditFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
//              Mở thư viện ảnh và chỉ định loại tệp mà intent sẽ tìm kiếm trong thư viện
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditTourActivity.this, DetailTourActivity.class);

        startActivity(intent);
        finish();
    }

    private void confirmPermission() {
        String[] permission = new String[] {Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, permission, CODE_CAM);
    }
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMGCODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CODE_CAM) {
            if(grantResults.length > 0) {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "User don't permission", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "User don't permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(IMGCODE == requestCode && resultCode == RESULT_OK && data !=null) {
            bitmap= (Bitmap) data.getExtras().get("data");
            imgEditview.setImageBitmap(bitmap);
        }

        if(GALLERY_REQUEST_CODE == requestCode && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgEditview.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap GetImage(ImageView h){
//        Tạo đối tượng BitmapDrawable bằng cách cách chuyển đổi drawable của ImgView thành BitmapDrawable
        BitmapDrawable bitmapDrawable = (BitmapDrawable) h.getDrawable();
//        sử dụng phương thức getBitmap() để trích xuất đối tượng Bitmap từ BitmapDrawable
        Bitmap bitmap = bitmapDrawable.getBitmap();

        return bitmap;
    }

    private void anhxa() {
        txtEditName = findViewById(R.id.txtEditName);
        txtEditDesc = findViewById(R.id.txtEditDesc);
        txtEditTime = findViewById(R.id.txtEditTime);
        txtEditPrice = findViewById(R.id.txtEditPrice);
        imgEditCamera = findViewById(R.id.imgEditCamera);
        imgEditFolder = findViewById(R.id.imgEditFolder);
        imgEditview = findViewById(R.id.imgEditview);
        btnSave = findViewById(R.id.btnSave);
    }
}