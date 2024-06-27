package com.example.book_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.book_store.Database;
import com.example.book_store.Model.NhaXuatBan;

import java.util.ArrayList;
import java.util.List;

public class DaoNhaXuatBan {
    private SQLiteDatabase db;

    public DaoNhaXuatBan(Context context) {
        Database dbHelper = new Database(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<NhaXuatBan> layDanhSachNhaXuatBan() {
        List<NhaXuatBan> danhSachNXB = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(Database.TABLE_PUBLISHER, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int maNXBIndex = cursor.getColumnIndex(Database.COLUMN_PUBLISHER_ID);
                    int tenNXBIndex = cursor.getColumnIndex(Database.COLUMN_PUBLISHER_NAME);
                    int diaChiIndex = cursor.getColumnIndex(Database.COLUMN_PUBLISHER_ADDRESS);

                    if (maNXBIndex != -1 && tenNXBIndex != -1 && diaChiIndex != -1) {
                        String maNXB = cursor.getString(maNXBIndex);
                        String tenNXB = cursor.getString(tenNXBIndex);
                        String diaChi = cursor.getString(diaChiIndex);
                        NhaXuatBan nxb = new NhaXuatBan(maNXB, tenNXB, diaChi);
                        danhSachNXB.add(nxb);
                    }
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

    public boolean themNhaXuatBan(String maNXB, String tenNXB, String diaChi) {
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_PUBLISHER_ID, maNXB);
        values.put(Database.COLUMN_PUBLISHER_NAME, tenNXB);
        values.put(Database.COLUMN_PUBLISHER_ADDRESS, diaChi);

        long result = db.insert(Database.TABLE_PUBLISHER, null, values);
        return result != -1;
    }

    public boolean capNhatNhaXuatBan(String maNXB, String tenNXB, String diaChi) {
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_PUBLISHER_NAME, tenNXB);
        values.put(Database.COLUMN_PUBLISHER_ADDRESS, diaChi);

        int rowsAffected = db.update(Database.TABLE_PUBLISHER, values, Database.COLUMN_PUBLISHER_ID + " = ?", new String[]{maNXB});
        return rowsAffected > 0;
    }
}
