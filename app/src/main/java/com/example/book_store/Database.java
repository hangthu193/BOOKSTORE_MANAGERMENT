package com.example.book_store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Locale;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "qlSach.db";

    // Bảng NHANVIEN
    public static final String TABLE_EMPLOYEE = "NhanVien";
    public static final String COLUMN_EMPLOYEE_ID = "MaNhanVien";
    public static final String COLUMN_EMPLOYEE_NAME = "TenNhanVien";
    public static final String COLUMN_EMPLOYEE_GENDER = "GioiTinh";
    public static final String COLUMN_EMPLOYEE_PHONE = "SoDienThoai";
    public static final String COLUMN_EMPLOYEE_ADDRESS = "DiaChi";
    public static final String COLUMN_EMPLOYEE_USERNAME = "TenDangNhap";
    public static final String COLUMN_EMPLOYEE_PASSWORD = "MatKhau";

    // Bảng NHAXUATBAN
    public static final String TABLE_PUBLISHER = "NhaXuatBan";
    public static final String COLUMN_PUBLISHER_ID = "MaNXB";
    public static final String COLUMN_PUBLISHER_NAME = "TenNXB";
    public static final String COLUMN_PUBLISHER_ADDRESS = "DiaChiNXB";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Nhân viên khi cơ sở dữ liệu được tạo
        String CREATE_EMPLOYEE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEE + " (" +
                COLUMN_EMPLOYEE_ID + " TEXT PRIMARY KEY, " +
                COLUMN_EMPLOYEE_NAME + " TEXT, " +
                COLUMN_EMPLOYEE_GENDER + " TEXT, " +
                COLUMN_EMPLOYEE_PHONE + " TEXT, " +
                COLUMN_EMPLOYEE_ADDRESS + " TEXT, " +
                COLUMN_EMPLOYEE_USERNAME + " TEXT, " +
                COLUMN_EMPLOYEE_PASSWORD + " TEXT)";
        db.execSQL(CREATE_EMPLOYEE_TABLE);

        // Tạo bảng Nhà xuất bản
        String CREATE_PUBLISHER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PUBLISHER + " (" +
                COLUMN_PUBLISHER_ID + " TEXT PRIMARY KEY, " +
                COLUMN_PUBLISHER_NAME + " TEXT, " +
                COLUMN_PUBLISHER_ADDRESS + " TEXT)";
        db.execSQL(CREATE_PUBLISHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa và tạo lại bảng nếu có bản nâng cấp
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUBLISHER);
        onCreate(db);
    }

    // Phương thức để thêm một người dùng mới (nhân viên) vào cơ sở dữ liệu
    public boolean addUser(String id, String name, String gender, String phone, String address, String username, String password) {
        if (checkUsernameExist(username)) {
            return false; // Trả về false nếu tên đăng nhập đã tồn tại
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMPLOYEE_ID, id);
        contentValues.put(COLUMN_EMPLOYEE_NAME, name);
        contentValues.put(COLUMN_EMPLOYEE_GENDER, gender);
        contentValues.put(COLUMN_EMPLOYEE_PHONE, phone);
        contentValues.put(COLUMN_EMPLOYEE_ADDRESS, address);
        contentValues.put(COLUMN_EMPLOYEE_USERNAME, username);
        contentValues.put(COLUMN_EMPLOYEE_PASSWORD, password);
        long result = db.insert(TABLE_EMPLOYEE, null, contentValues);
        return result != -1;
    }

    // Phương thức để kiểm tra xem một tên đăng nhập đã tồn tại trong bảng nhân viên chưa
    public boolean checkUsernameExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EMPLOYEE, null, COLUMN_EMPLOYEE_USERNAME + " = ?", new String[]{username}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Phương thức để tạo mã nhân viên mới dựa trên trạng thái hiện tại của bảng nhân viên
    public String generateID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + TABLE_EMPLOYEE + ")", null);
        int id = 0;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return String.format(Locale.getDefault(), "NV%04d", id + 1);
    }

    // Phương thức để kiểm tra xem một đăng nhập với tên đăng nhập và mật khẩu cung cấp có hợp lệ không
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_EMPLOYEE_USERNAME};
        String selection = COLUMN_EMPLOYEE_USERNAME + " = ? AND " + COLUMN_EMPLOYEE_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_EMPLOYEE, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Phương thức để lấy chi tiết của một nhân viên dựa trên tên đăng nhập
    public Cursor getEmployeeDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_EMPLOYEE, null, COLUMN_EMPLOYEE_USERNAME + " = ?", new String[]{username}, null, null, null);
    }

    // Phương thức để cập nhật chi tiết của một nhân viên trong bảng nhân viên
    public void updateEmployeeDetails(String id, String newName, String newGender, String newPhone, String newAddress, String newUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMPLOYEE_NAME, newName);
        contentValues.put(COLUMN_EMPLOYEE_GENDER, newGender);
        contentValues.put(COLUMN_EMPLOYEE_PHONE, newPhone);
        contentValues.put(COLUMN_EMPLOYEE_ADDRESS, newAddress);
        contentValues.put(COLUMN_EMPLOYEE_USERNAME, newUsername);
        db.update(TABLE_EMPLOYEE, contentValues, COLUMN_EMPLOYEE_ID + " = ?", new String[]{id});
    }

    // Phương thức để tạo mã nhà xuất bản mới dựa trên trạng thái hiện tại của bảng nhà xuất bản
    public String generatePublisherID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + TABLE_PUBLISHER + ")", null);
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
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PUBLISHER_ID, id);
        contentValues.put(COLUMN_PUBLISHER_NAME, name);
        contentValues.put(COLUMN_PUBLISHER_ADDRESS, address);
        long result = db.insert(TABLE_PUBLISHER, null, contentValues);
        db.close(); // Đóng kết nối sau khi thực hiện xong
        return result != -1;
    }

    public boolean checkPublisherExist(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PUBLISHER + " WHERE " + COLUMN_PUBLISHER_NAME + " = ?", new String[]{name});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Phương thức để cập nhật thông tin của một nhà xuất bản trong bảng nhà xuất bản
    public void updatePublisherDetails(String id, String newName, String newAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PUBLISHER_NAME, newName);
        contentValues.put(COLUMN_PUBLISHER_ADDRESS, newAddress);
        db.update(TABLE_PUBLISHER, contentValues, COLUMN_PUBLISHER_ID + " = ?", new String[]{id});
        db.close();
    }

}
