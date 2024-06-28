package com.example.book_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.book_store.Database;
import com.example.book_store.model.NhaXuatBan;

import java.util.ArrayList;
import java.util.List;

public class DaoNhaXuatBan {
    private SQLiteDatabase db;

    public DaoNhaXuatBan(Context context) {
        Database dbHelper = new Database(context);
        db = dbHelper.getWritableDatabase();
    }

    // Lấy danh sách nhà xuất bản
    public List<NhaXuatBan> layDanhSachNhaXuatBan(SQLiteDatabase db) {
        List<NhaXuatBan> danhSach = new ArrayList<>();
        Cursor cursor = db.query("NXB", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String maNXB = cursor.getString(cursor.getColumnIndexOrThrow("MaNXB"));
                String tenNXB = cursor.getString(cursor.getColumnIndexOrThrow("TenNXB"));
                String diaChi = cursor.getString(cursor.getColumnIndexOrThrow("DiaChi"));
                danhSach.add(new NhaXuatBan(maNXB, tenNXB, diaChi));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return danhSach;
    }

    // Thêm mới nhà xuất bản
    public boolean themNhaXuatBan(String maNXB, String tenNXB, String diaChi) {
        ContentValues values = new ContentValues();
        values.put("MaNXB", maNXB);
        values.put("TenNXB", tenNXB);
        values.put("DiaChi", diaChi);
        long result = db.insert("NXB", null, values);
        return result != -1;
    }

    // Cập nhật thông tin nhà xuất bản
   // public boolean capNhatNhaXuatBan(String maNXB, String tenNXB, String diaChi) {
    //    ContentValues values = new ContentValues();
    //    values.put("TenNXB", tenNXB);
     //   values.put("DiaChi", diaChi);

    //    int rowsAffected = db.update("NXB", values, "MaNXB = ?", new String[]{maNXB});
      //  return rowsAffected > 0;
 //   }
}
