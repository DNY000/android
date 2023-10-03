package com.example.projectandroidbookingtour.Model;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoan extends Application implements Serializable {
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "tbl_taikhoan";
    private static final String NAME_COLUMN = "name";
    private static final String IMAGE_COLUMN = "img";
    private static final String USERNAME_COLUMN = "username";
    private static final String PASSWORD_COLUMN = "password";
    private static final String ROLE_COLUMN = "role";
    private static final String PHONE_COLUMN = "phone";
    private static final String EMAIL_COLUMN = "email";
    private static final String ID_COLUMN = "id";

    protected int id;
    protected String name;
    protected Bitmap img;
    protected String username;
    protected String password;
    protected String role;
    protected String phone;
    protected String email;
    private Context context;
    MyDatabase myDatabase;

    public TaiKhoan(Context context) {
        this.context = context;
        myDatabase = new MyDatabase(context);
    }

    public TaiKhoan(int id, String name, Bitmap img, String username, String password, String role, String phone, String email) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.username = username;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img=" + img +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public long insert(TaiKhoan taiKhoan) {
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, taiKhoan.getName());
        cv.put(IMAGE_COLUMN, taiKhoan.getImg().toString());
        cv.put(USERNAME_COLUMN, taiKhoan.getUsername());
        cv.put(PASSWORD_COLUMN, taiKhoan.getPassword());
        cv.put(ROLE_COLUMN, taiKhoan.getRole());
        cv.put(PHONE_COLUMN, taiKhoan.getPhone());
        cv.put(EMAIL_COLUMN, taiKhoan.getEmail());
        return sqLiteDatabase.insert(TABLE_NAME, null, cv);
    }

    public List<TaiKhoan> getData() {
        List<TaiKhoan> listAccounts = new ArrayList<>();
        Cursor cs = myDatabase.selectData("SELECT * FROM " + TABLE_NAME);
        while(cs.moveToNext()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            byte[] decodedString = Base64.decode(cs.getString(2), Base64.URL_SAFE );
            Bitmap img = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            String username = cs.getString(3);
            String password = cs.getString(4);
            String role = cs.getString(5);
            String phone = cs.getString(6);
            String email = cs.getString(7);
            TaiKhoan taiKhoan = new TaiKhoan(id,name, img, username, password, role, phone, email);
            listAccounts.add(taiKhoan);
        }
        return listAccounts;
    }

    public boolean checkAccount(String username, String password) {
        SQLiteDatabase db = myDatabase.getReadableDatabase();

        String[] columns = {ID_COLUMN};
        String selection = USERNAME_COLUMN + " = ?" + " AND " + PASSWORD_COLUMN + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public String getRole (String username) {
        SQLiteDatabase db = myDatabase.getReadableDatabase();

        String[] columns = {ROLE_COLUMN};
        String selection = USERNAME_COLUMN + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        String role = "";

        if(cursor.moveToFirst()) {
            role = cursor.getString(0);
        }

        return role;
    }
}
