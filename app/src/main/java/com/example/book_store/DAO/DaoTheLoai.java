package com.example.book_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.book_store.DataBase;
import com.example.book_store.model.CategoryProperty;

public class DaoTheLoai {
    private SQLiteDatabase database;
    private DataBase dbHelper;
    private Context mContext;

    public DaoTheLoai(Context mContext) {

        this.mContext = mContext;
    }



    public boolean insertCategory(SQLiteDatabase db, String maTheLoai, String tenTheLoai) {
        try {
            ContentValues values = new ContentValues();
            values.put("matheloai", maTheLoai);
            values.put("tentheloai", tenTheLoai);

            long result = db.insert("TheLoai", null, values);
            return result != -1; // Trả về true nếu chèn thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

        public CategoryProperty getCategoryDetail(SQLiteDatabase db, String id) {
        CategoryProperty categoryDetail = null;

        Cursor cursor = null;
        try {
            String query = "SELECT MaTheLoai, TenTheLoai " +
                    "FROM TheLoai " +
                    "WHERE MaTheLoai = ?";
            cursor = db.rawQuery(query, new String[]{id});

            if (cursor.moveToFirst()) {
                String maTheLoai = cursor.getString(0);
                String tenTheLoai = cursor.getString(1);

                categoryDetail = new CategoryProperty(maTheLoai, tenTheLoai);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return categoryDetail;
    }
    public boolean updateCategory(SQLiteDatabase db, String matheloai, String tentheloai) {
        try {
            ContentValues values = new ContentValues();
            values.put("matheloai", matheloai);
            values.put("tentheloai", tentheloai);
            String selection = "matheloai = ?";
            String[] selectionArgs = new String[]{matheloai};

            int result = db.update("TheLoai", values, selection, selectionArgs);
            return result > 0; // Trả về true nếu cập nhật thành công, ngược lại trả về false
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public boolean deleteCategory(SQLiteDatabase db, String categoryId) {
        boolean isDeleted = false;

        // Kiểm tra nếu thể loại đang được tham chiếu ở các bảng khác
        if (isCategoryReferenced(db, categoryId)) {
            // Thể loại đang được tham chiếu, không thể xóa
            return false;
        }

        SQLiteStatement statement = null;
        try {
            String query = "DELETE FROM TheLoai WHERE MaTheLoai = ?";
            statement = db.compileStatement(query);
            statement.bindString(1, categoryId);

            int rowsAffected = statement.executeUpdateDelete();
            if (rowsAffected > 0) {
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        return isDeleted;
    }

    private boolean isCategoryReferenced(SQLiteDatabase db, String categoryId) {
        Cursor cursor = null;
        try {
            // Kiểm tra sự tồn tại của thể loại trong bảng sách
            String query = "SELECT COUNT(*) FROM Sach WHERE MaTheLoai = ?";
            cursor = db.rawQuery(query, new String[]{categoryId});

            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    // Thể loại đang được tham chiếu
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }
}
