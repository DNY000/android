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


public class AddTourActivity extends AppCompatActivity {

    private static final int CODE_CAM = 123;
    private static final int IMGCODE = 124;
    private static final int GALLERY_REQUEST_CODE = 125;
    EditText txtName, txtDesc,txtTime, txtPrice;
    ImageButton imgCamera, imgFolder;
    ImageView imgview;
    Button btnAddTour, btnCancel;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        anhxa();

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cameraPermission = ContextCompat.checkSelfPermission(AddTourActivity.this, Manifest.permission.CAMERA);
                if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
                    // Ứng dụng đã có quyền truy cập camera, mở camera
                    openCamera();
                } else {
                    // Ứng dụng chưa có quyền truy cập camera, xin cấp quyền
                    confirmPermission();
                }
            }
        });

        imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
//          Mở thư viện ảnh và chỉ định loại tệp mà intent sẽ tìm kiếm trong thư viện
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddTourActivity.this, TourActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnAddTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                int songay = Integer.parseInt(txtTime.getText().toString());
                Bitmap img = GetImage(imgview);
                String mota = txtDesc.getText().toString();
                Double gia = Double.parseDouble(txtPrice.getText().toString());
                Tour tour = new Tour(AddTourActivity.this);
                Long insert = tour.insert(new Tour(1, name, songay, img, mota, gia));
                if(insert != -1) {
                    Toast.makeText(AddTourActivity.this, "Thêm tour thành công", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(AddTourActivity.this, "Thêm tour thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddTourActivity.this, TourActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void anhxa() {
        txtName = findViewById(R.id.txtName);
        txtDesc = findViewById(R.id.txtDesc);
        txtTime = findViewById(R.id.txtTime);
        txtPrice = findViewById(R.id.txtPrice);
        imgCamera = findViewById(R.id.imgCamera);
        imgFolder = findViewById(R.id.imgFolder);
        imgview = findViewById(R.id.imgview);
        btnAddTour = findViewById(R.id.btnAddTour);
        btnCancel = findViewById(R.id.btnCancel);
    }

    public Bitmap GetImage(ImageView h){
//        Tạo đối tượng BitmapDrawable bằng cách cách chuyển đổi drawable của ImgView thành BitmapDrawable
        BitmapDrawable bitmapDrawable = (BitmapDrawable) h.getDrawable();
//        sử dụng phương thức getBitmap() để trích xuất đối tượng Bitmap từ BitmapDrawable
        Bitmap bitmap = bitmapDrawable.getBitmap();

        return bitmap;
    }

    private void confirmPermission() {
        String[] permission = new String[] {Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, permission, CODE_CAM);
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

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMGCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(IMGCODE == requestCode && resultCode == RESULT_OK && data !=null) {
            bitmap= (Bitmap) data.getExtras().get("data");
            imgview.setImageBitmap(bitmap);
        }

        if(GALLERY_REQUEST_CODE == requestCode && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgview.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}