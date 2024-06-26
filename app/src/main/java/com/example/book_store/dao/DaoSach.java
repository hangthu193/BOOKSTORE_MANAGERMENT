package com.example.book_store.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.book_store.Database;
import com.example.book_store.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class DaoSach {
    private SQLiteDatabase db;

    public DaoSach(Context context) {
        Database database = new Database(context);
        db = database.getWritableDatabase();
    }
    public List<Sach> getAllSach() {
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Sach", null);
        if (cursor.moveToFirst()) {
            do {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getString(0));
                sach.setTenSach(cursor.getString(1));
                sach.setGiaBan(cursor.getInt(2));
                sach.setTheLoai(cursor.getString(3));
                sach.setTacGia(cursor.getString(4));
                sach.setNxb(cursor.getString(5));
                sach.setSoLuong(cursor.getInt(6));
                list.add(sach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
