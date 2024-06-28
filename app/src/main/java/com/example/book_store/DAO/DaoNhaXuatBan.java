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
    public List<NhaXuatBan> layDanhSachNhaXuatBan() {
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
    public boolean themNhaXuatBan(String id, String name, String address) {
        if (checkPublisherExist(name)) {
            return false; // Trả về false nếu tên nhà xuất bản đã tồn tại
        }

        ContentValues values = new ContentValues();
        values.put("MaNXB", id);
        values.put("TenNXB", name);
        values.put("DiaChi", address);
        long result = db.insert("NXB", null, values);
        return result != -1;
    }

    // Kiểm tra sự tồn tại của nhà xuất bản
    private boolean checkPublisherExist(String name) {
        String[] columns = {"TenNXB"};
        String selection = "TenNXB = ?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query("NXB", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

}
