package com.example.book_store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.book_store.Database;
import com.example.book_store.model.Kho;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaoKho {
    private SQLiteDatabase database;
    private Database dbHelper;
    private Context mContext;

    public DaoKho(Context context) {
        dbHelper = new Database(context);
        this.mContext = context;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Kho> getAllKho() {
        List<Kho> khoList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM Kho";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String maKho = cursor.getString(cursor.getColumnIndex("MaKho"));
                    String maSach = cursor.getString(cursor.getColumnIndex("MaSach"));
                    String ngayNhap = cursor.getString(cursor.getColumnIndex("NgayNhap"));
                    int giaNhap = cursor.getInt(cursor.getColumnIndex("GiaNhap"));
                    int soLuongNhap = cursor.getInt(cursor.getColumnIndex("Soluongnhap"));

                    Kho kho = new Kho(maKho, maSach, ngayNhap,giaNhap, soLuongNhap);
                    khoList.add(kho);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return khoList;
    }
    public long insertKho(Kho kho) {
        ContentValues values = new ContentValues();
        values.put("MaKho", kho.getMaKho());
        values.put("MaSach", kho.getMaSach());
        values.put("NgayNhap", kho.getNgayNhap());
        values.put("GiaNhap", kho.getGiaNhap());
        values.put("Soluongnhap", kho.getSoLuongNhap());

        return database.insert("Kho", null, values);
    }

    public List<String> getAllMaSach() {
        List<String> maSachList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String query = "SELECT MaSach FROM Sach";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String maSach = cursor.getString(cursor.getColumnIndex("MaSach"));
                    maSachList.add(maSach);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return maSachList;
    }

    public String getCurrentDate() {
        String currentDate = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            currentDate = sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentDate;
    }

}
