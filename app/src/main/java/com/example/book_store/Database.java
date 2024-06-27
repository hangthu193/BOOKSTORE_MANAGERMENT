package com.example.book_store;


import android.app.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import com.example.book_store.model.Selected;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "qlSach.db";
    private static final int DB_VERSION = 1;
    private final Context mContext;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public void initializeDatabaseFromAssets() {
        if (!checkDatabaseExists()) {
            try {
                InputStream inputStream = mContext.getAssets().open(DB_NAME);
                String outFileName = getDatabasePath().getPath();
                OutputStream outputStream = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean checkDatabaseExists() {
        File dbFile = mContext.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    // Get the path to the database
    private File getDatabasePath() {
        return mContext.getDatabasePath(DB_NAME);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static SQLiteDatabase initDatabase(Activity activity, String databaseName){
        try{
            String outFileName = activity.getApplicationInfo().dataDir + "/databases/"+databaseName;
            File f  = new File(outFileName);
            if(!f.exists()){
                InputStream e = activity.getAssets().open(databaseName);
                File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                if(!folder.exists()){
                    folder.mkdir();
                }
                FileOutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while((length = e.read(buffer))>0){
                    myOutput.write(buffer,0,length);
                }
                myOutput.flush();
                myOutput.close();
                e.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE,null);
    }

    public boolean checkmanv(SQLiteDatabase db, String manv) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE MaNhanVien = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{manv});

        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count == 1;
        } else {
            cursor.close();
            return false;
        }
    }
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = "NhanVien";
        String usernameColumn = "TenDangNhap";
        String passwordColumn = "MatKhau";

        String[] columns = {usernameColumn};
        String selection = usernameColumn + " = ? AND " + passwordColumn + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean updatePassword(SQLiteDatabase db, String manhanvien, String newPassword) {
        try {
            ContentValues values = new ContentValues();
            values.put("matkhau", newPassword);

            String selection = "MaNhanVien = ?";
            String[] selectionArgs = new String[]{manhanvien};

            long result = db.update("NhanVien", values, selection, selectionArgs);
            return result != -1; // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
    public List<Selected> getAllCategories(SQLiteDatabase db) {
        List<Selected> categories = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT MaTheLoai, TenTheLoai FROM TheLoai";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    categories.add(new Selected(name, id));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return categories;
    }
    public String generatePublisherID() {
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = "NXB"; // Tên bảng
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
        int id = 0;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return String.format(Locale.getDefault(), "NXB%04d", id + 1);
    }

    public boolean addPublisher(String id, String name, String address) {
        if (checkPublisherExist(name)) {
            return false; // Trả về false nếu tên nhà xuất bản đã tồn tại
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = "NXB";
        String columnID = "MaNXB";
        String columnName = "TenNXB";
        String columnAddress = "DiaChi";

        ContentValues contentValues = new ContentValues();
        contentValues.put(columnID, id);
        contentValues.put(columnName, name);
        contentValues.put(columnAddress, address);
        long result = db.insert(tableName, null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean checkPublisherExist(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = "NXB";
        String columnName = "TenNXB";

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + columnName + " = ?", new String[]{name});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    public void updatePublisherDetails(String id, String newName, String newAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = "NXB";
        String columnID = "MaNXB";
        String columnName = "TenNXB";
        String columnAddress = "DiaChi";

        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName, newName);
        contentValues.put(columnAddress, newAddress);
        db.update(tableName, contentValues, columnID + " = ?", new String[]{id});
        db.close();
    }
    public Cursor getEmployeeDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = "NhanVien"; // Tên bảng
        String columnUsername = "TenDangNhap"; // Tên cột tên đăng nhập

        return db.query(tableName, null, columnUsername + " = ?", new String[]{username}, null, null, null);
    }


}
