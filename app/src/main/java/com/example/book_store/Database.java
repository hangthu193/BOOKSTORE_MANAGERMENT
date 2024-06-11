package com.example.book_store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Locale;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "qlSach.db";
    public static final String TABLE_NAME = "NhanVien";
    public static final String COLUMN_NAME_ID = "MaNhanVien";
    public static final String COLUMN_NAME_NAME = "TenNhanVien";
    public static final String COLUMN_NAME_GENDER = "GioiTinh";
    public static final String COLUMN_NAME_PHONE = "SoDienThoai";
    public static final String COLUMN_NAME_ADDRESS = "DiaChi";
    public static final String COLUMN_NAME_USERNAME = "TenDangNhap";
    public static final String COLUMN_NAME_PASSWORD = "MatKhau";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_ID + " TEXT, " +
                COLUMN_NAME_NAME + " TEXT, " +
                COLUMN_NAME_GENDER + " TEXT, " +
                COLUMN_NAME_PHONE + " TEXT, " +
                COLUMN_NAME_ADDRESS + " TEXT, " +
                COLUMN_NAME_USERNAME + " TEXT, " +
                COLUMN_NAME_PASSWORD + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertEmployee(String id, String name, String gender, String phone, String address, String username, String password) {
        if (isUsernameTaken(username)) {
            return false; // Trả về false nếu tên đăng nhập đã tồn tại
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ID, id);
        contentValues.put(COLUMN_NAME_NAME, name);
        contentValues.put(COLUMN_NAME_GENDER, gender);
        contentValues.put(COLUMN_NAME_PHONE, phone);
        contentValues.put(COLUMN_NAME_ADDRESS, address);
        contentValues.put(COLUMN_NAME_USERNAME, username);
        contentValues.put(COLUMN_NAME_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean isUsernameTaken(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_NAME_USERNAME + " = ?", new String[]{username}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public String generateID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT seq FROM sqlite_sequence WHERE name = ?", new String[]{TABLE_NAME});
        int id = 0;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return String.format(Locale.getDefault(), "NV%04d", id + 1);
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_NAME_USERNAME};
        String selection = COLUMN_NAME_USERNAME + " = ? AND " + COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

}
