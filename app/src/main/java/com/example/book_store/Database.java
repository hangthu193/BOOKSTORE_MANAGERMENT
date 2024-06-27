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

}
