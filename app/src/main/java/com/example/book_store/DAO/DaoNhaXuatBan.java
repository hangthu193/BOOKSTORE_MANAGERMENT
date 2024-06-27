package com.example.book_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.book_store.Database;
import com.example.book_store.model.NhaXuatBan;
import com.example.book_store.model.NhaXuatBan;

import java.util.ArrayList;
import java.util.List;

public class DaoNhaXuatBan {
    private SQLiteDatabase db;

    public DaoNhaXuatBan(Context context) {
        Database dbHelper = new Database(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<NhaXuatBan> layDanhSachNhaXuatBan(SQLiteDatabase db) {
        List<NhaXuatBan> danhSachNXB = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT MaNXB, TenNXB, DiaChi FROM NXB";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    String maNXB = cursor.getString(0);
                    String tenNXB = cursor.getString(1);
                    String diaChi = cursor.getString(2);
                    NhaXuatBan nxb = new NhaXuatBan(maNXB, tenNXB, diaChi);
                    danhSachNXB.add(nxb);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return danhSachNXB;
    }



//    public boolean themNhaXuatBan(String maNXB, String tenNXB, String diaChi) {
//        ContentValues values = new ContentValues();
//        values.put(Database.COLUMN_PUBLISHER_ID, maNXB);
//        values.put(Database.COLUMN_PUBLISHER_NAME, tenNXB);
//        values.put(Database.COLUMN_PUBLISHER_ADDRESS, diaChi);
//
//        long result = db.insert(Database.TABLE_PUBLISHER, null, values);
//        return result != -1;
//    }

//    public boolean capNhatNhaXuatBan(String maNXB, String tenNXB, String diaChi) {
//        ContentValues values = new ContentValues();
//        values.put(Database.COLUMN_PUBLISHER_NAME, tenNXB);
//        values.put(Database.COLUMN_PUBLISHER_ADDRESS, diaChi);
//
//        int rowsAffected = db.update(Database.TABLE_PUBLISHER, values, Database.COLUMN_PUBLISHER_ID + " = ?", new String[]{maNXB});
//        return rowsAffected > 0;
//    }
}
