package com.example.book_store;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
}
