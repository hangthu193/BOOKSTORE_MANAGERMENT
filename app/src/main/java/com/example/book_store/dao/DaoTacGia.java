package com.example.book_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.book_store.Database;
import com.example.book_store.model.TacGia;

import java.util.ArrayList;

public class DaoTacGia {
    private SQLiteDatabase database;

    public DaoTacGia(Context context) {
        Database db = new Database(context);
        db.initializeDatabaseFromAssets(); // Ensure database is copied from assets if not already
        database = db.getWritableDatabase(); // Get writable database instance
    }

    public ArrayList<TacGia> getAllTacGia() {
        ArrayList<TacGia> list = new ArrayList<>();
        Cursor cursor = database.query("TacGia", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String ten = cursor.getString(1);
            String gioitinh = cursor.getString(2);
            list.add(new TacGia(id, ten, gioitinh));
        }
        cursor.close();
        return list;
    }

    public boolean insertTacGia(TacGia tacGia) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("MaTG", tacGia.getMaTG());
        contentValues.put("TenTG", tacGia.getTenTG());
        contentValues.put("GioiTinh", tacGia.getGioiTinh());
        long result = database.insert("TacGia", null, contentValues);
        database.close();
        return result != -1;
    }

    public boolean updateTacGia(TacGia tacGia) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenTG", tacGia.getTenTG());
        contentValues.put("GioiTinh", tacGia.getGioiTinh());
        int result = database.update("TacGia", contentValues, "MaTG = ?", new String[]{tacGia.getMaTG()});
        return result > 0;
    }

    public boolean deleteTacGia(String MaTG) {
        int result = database.delete("TacGia", "MaTG = ?", new String[]{MaTG});
        return result > 0;
    }

    public TacGia getTacGia(String MaTG) {
        Cursor cursor = database.rawQuery("SELECT * FROM TacGia WHERE MaTG = ?", new String[]{MaTG});
        if (cursor.moveToFirst()) {
            String id = cursor.getString(0);
            String ten = cursor.getString(1);
            String gioitinh = cursor.getString(2);
            cursor.close();
            return new TacGia(id, ten, gioitinh);
        }
        cursor.close();
        return null;
    }
}
