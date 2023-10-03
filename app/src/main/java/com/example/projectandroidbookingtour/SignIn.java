package com.example.projectandroidbookingtour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectandroidbookingtour.Model.TaiKhoan;
import com.example.projectandroidbookingtour.NewsAdmin.NewsActivity;
import com.example.projectandroidbookingtour.TourUser.ListTour;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {
    TextView signUp;

    Button dangnhap;

    EditText username, password;
    CheckBox rememberPassword;
    TextView forgotPassword;
    TaiKhoan taiKhoan = new TaiKhoan(SignIn.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        dangnhap = (Button) findViewById(R.id.dangnhap);
        signUp = (TextView) findViewById(R.id.signUp);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

//        TaiKhoan taiKhoan1 = new TaiKhoan(1, "Bui Van Thuan", BitmapFactory.decodeResource(this.getResources(), R.drawable.kaka), "thuanbui", "123qwe", "admin", "0123456789", "abc@gmail.com" );
//        taiKhoan.insert(taiKhoan1);
//
//        taiKhoan1 = new TaiKhoan(1, "User 1", BitmapFactory.decodeResource(this.getResources(), R.drawable.kaka), "user1", "123qwe", "user", "0123456789", "user1@gmail.com" );
//        taiKhoan.insert(taiKhoan1);

//        list = taiKhoan.getData();
//        for (TaiKhoan account: list
//             ) {
//            Toast.makeText(SignIn.this, account.toString(), Toast.LENGTH_SHORT).show();
//        }

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = username.getText().toString();
                String Password = password.getText().toString();
                TaiKhoan taiKhoan1 = new TaiKhoan(SignIn.this);
                if(taiKhoan1.checkAccount(Username, Password)) {
                    String role = taiKhoan1.getRole(Username);
                    if(role.equals("admin")) {
                        Toast.makeText(SignIn.this, "Hello Admin", Toast.LENGTH_LONG).show();
                        Intent homeAdmin = new Intent(SignIn.this, NewsActivity.class);
//                        ngăn chặn quay trở lại Activity đăng nhập
                        homeAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeAdmin);
                        finish();
                    } else {
                        Toast.makeText(SignIn.this, "Hello User", Toast.LENGTH_LONG).show();
                        Intent homeUser = new Intent(SignIn.this, ListTour.class);
                        homeUser.putExtra("username", Username);
//                        ngăn chặn quay trở lại Activity đăng nhập
                        homeUser.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeUser);
                        finish();
                    }
                } else {
                    Toast.makeText(SignIn.this, "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(SignIn.this, SignUp.class);
                startActivity(signUp);
            }
        });
    }
}