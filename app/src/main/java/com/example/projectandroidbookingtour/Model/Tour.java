package com.example.projectandroidbookingtour.Model;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Tour extends Application implements Serializable {
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "tbl_tour";
    private static final String NAME_COLUMN = "tentour";
    private static final String IMG_COLUMN = "img";
    private static final String MOTA_COLUMN = "mota";
    private static final String SONGAY_COLUMN = "songay";
    private static final String GIA_COLUMN = "gia";
    protected int id;
    protected String tentour;
    protected int songay;
    protected Bitmap img;
    protected String mota;
    protected Double gia;
    protected int diadiemxuatphat;

    private Context context;
    MyDatabase myDatabase;
    public Tour(Context context) {
        this.context = context;
        myDatabase = new MyDatabase(context);
    }

    public Tour(int id, String tentour, int songay, Bitmap img, String mota, Double gia, int diadiemxuatphat) {
        this.id = id;
        this.tentour = tentour;
        this.songay = songay;
        this.img = img;
        this.mota = mota;
        this.gia = gia;
        this.diadiemxuatphat = diadiemxuatphat;
    }

    public Tour(int id, String tentour, int songay, Bitmap img, String mota, Double gia) {
        this.id = id;
        this.tentour = tentour;
        this.songay = songay;
        this.img = img;
        this.mota = mota;
        this.gia = gia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTentour() {
        return tentour;
    }

    public void setTentour(String tentour) {
        this.tentour = tentour;
    }

    public int getSongay() {
        return songay;
    }

    public void setSongay(int songay) {
        this.songay = songay;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public int getDiadiemxuatphat() {
        return diadiemxuatphat;
    }

    public void setDiadiemxuatphat(int diadiemxuatphat) {
        this.diadiemxuatphat = diadiemxuatphat;
    }

    public long insert (Tour tour) {
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, tour.getTentour());
        Bitmap bitmap = tour.getImg();
//        Chuyển bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageData = stream.toByteArray();
        cv.put(IMG_COLUMN, imageData);
        cv.put(MOTA_COLUMN, tour.getMota());
        cv.put(SONGAY_COLUMN, tour.getSongay());
        cv.put(GIA_COLUMN, tour.getGia());
        return sqLiteDatabase.insert(TABLE_NAME, null, cv);
    }

    public int delete (Tour tour) {
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        String whereArg = tour.getId()+"";
        int kq = sqLiteDatabase.delete(TABLE_NAME, "id=?", new String[]{whereArg});
        sqLiteDatabase.close();
        return kq;
    }

    public long update (Tour tour, int id) {
        String strID = id+"";
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Bitmap bitmap = tour.getImg();
//        Chuyển bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageData = stream.toByteArray();
        cv.put(NAME_COLUMN, tour.getTentour());
        cv.put(IMG_COLUMN, imageData);
        cv.put(MOTA_COLUMN, tour.getMota());
        cv.put(SONGAY_COLUMN, tour.getSongay());
        cv.put(GIA_COLUMN, tour.getGia());
        return sqLiteDatabase.update(TABLE_NAME, cv, "id=?", new String[]{strID});
    }

    public ArrayList<Tour> getAll (String sql) {
        ArrayList<Tour> tours = new ArrayList<>();
        Cursor cs = myDatabase.selectData(sql);
        while (cs.moveToNext()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            int songay = cs.getInt(2);
            byte[] imgData = cs.getBlob(3);
            Bitmap img = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            String mota = cs.getString(4);
            Double gia = cs.getDouble(5);
            Tour tour = new Tour(id, name, songay, img, mota, gia);
            tours.add(tour);
        }
        return tours;
    }
}
