package com.example.projectandroidbookingtour.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "qlbookingtour";
    private static final int VERSION = 1;

    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "Create table if not exists tbl_taikhoan(id integer primary key autoincrement," +
                "name text,img blob, username text, password text, role text, phone text, email text)";
        db.execSQL(createTable);

        createTable = "Create table if not exists tbl_tour(id integer primary key autoincrement," +
                "tentour text, songay integer, img blob, mota text, gia DOUBLE, diadiemxuatphat text)";
        db.execSQL(createTable);

        createTable = "CREATE TABLE IF NOT EXISTS tbl_hoadon (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    sokhachnguoilon INTEGER,\n" +
                "    sokhachtreem INTEGER,\n" +
                "    gia DOUBLE,\n" +
                "    ngaykhoihanh TEXT,\n" +
                "    idkhachhang INTEGER,\n" +
                "    idtour INTEGER,\n" +
                "    FOREIGN KEY (idkhachhang) REFERENCES tbl_taikhoan (id),\n" +
                "    FOREIGN KEY (idtour) REFERENCES tbl_tour (id)\n" +
                ");";
        db.execSQL(createTable);

        createTable = "CREATE TABLE IF NOT EXISTS tbl_binhluan (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    noidung text, " +
                "    ngaydang text, "+
                "    idtaikhoan INTEGER,\n" +
                "    idtour INTEGER,\n" +
                "    FOREIGN KEY (idtaikhoan) REFERENCES tbl_taikhoan (id),\n" +
                "    FOREIGN KEY (idtour) REFERENCES tbl_tour (id)\n" +
                ");";
        db.execSQL(createTable);

        createTable = "CREATE TABLE IF NOT EXISTS tbl_tintuc (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    tieude text, " +
                "    img blob, " +
                "    noidung text, "+
                "    ngaydang text, "+
                "    idtour INTEGER,\n" +
                "    FOREIGN KEY (idtour) REFERENCES tbl_tour (id)\n" +
                ");";
        db.execSQL(createTable);

        createTable = "CREATE TABLE IF NOT EXISTS tbl_giohang (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    idtour INTEGER,\n" +
                "    FOREIGN KEY (idtour) REFERENCES tbl_tour (id)\n" +
                ");";
        db.execSQL(createTable);
    }

    public Cursor selectData (String sql) {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cs = database.rawQuery(sql, null);
        return cs;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
