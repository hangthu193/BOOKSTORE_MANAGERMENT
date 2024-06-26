package com.example.book_store;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.example.book_store.category.Category;
import com.example.book_store.model.BookDetailProperty;
import com.example.book_store.model.BookProperty;
import com.example.book_store.book.Selected;
import com.example.book_store.model.CategoryProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
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


//    public boolean insertBook(SQLiteDatabase db, String maSach, String tenSach, String tacgia, String theloai, String nxb, String ngayxb, Integer giaban, Integer soluong,byte[] anh) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put("masach", maSach);
//            values.put("tensach", tenSach);
//            values.put("matg", tacgia);
//            values.put("matheloai", theloai);
//            values.put("manxb", nxb);
//            values.put("ngayxb", ngayxb);
//            values.put("giaban", giaban);
//            values.put("soluong", soluong);
//            values.put("anh",anh);
//
//            long result = db.insert("Sach", null, values);
//            return result != -1; // Trả về true nếu chèn thành công
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false; // Trả về false nếu có lỗi xảy ra
//        }
//    }
//
//    public boolean updateBook(SQLiteDatabase db, String tenSach, String maSach, String tacgia, String theloai, String nxb, String ngayxb, Integer giaban, Integer soluong,byte[] anh){
//        try {
//            ContentValues values = new ContentValues();
//            values.put("masach", maSach);
//            values.put("tensach", tenSach);
//            values.put("matg", tacgia);
//            values.put("matheloai", theloai);
//            values.put("manxb", nxb);
//            values.put("ngayxb", ngayxb);
//            values.put("giaban", giaban);
//            values.put("soluong", soluong);
//            values.put("anh",anh);
//            String selection = "masach = ?"; // Sử dụng "masach" thay vì "matg"
//            String[] selectionArgs = new String[]{maSach};
//
//            long result = db.update("Sach", values, selection, selectionArgs);
//            return result != -1; // Trả về true nếu chèn thành công
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false; // Trả về false nếu có lỗi xảy ra
//        }
//    }
//
//    public boolean deleteBook(SQLiteDatabase db, String bookId) {
//        boolean isDeleted = false;
//
//        SQLiteStatement statement = null;
//        try {
//            String query = "DELETE FROM Sach WHERE MaSach = ?";
//            statement = db.compileStatement(query);
//            statement.bindString(1, bookId);
//
//            int rowsAffected = statement.executeUpdateDelete();
//            if (rowsAffected > 0) {
//                isDeleted = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (statement != null) {
//                statement.close();
//            }
//        }
//
//        return isDeleted;
//    }
//
//
//    public BookDetailProperty getBookDetail(SQLiteDatabase db, String id) {
//        BookDetailProperty bookDetail = null;
//
//        Cursor cursor = null;
//        try {
//            String query = "SELECT s.MaSach, s.TenSach,tl.TenTheLoai, t.TenTG, n.TenNXB, s.NgayXB, s.GiaBan, s.SoLuong,s.Anh " +
//                    "FROM Sach s " +
//                    "INNER JOIN TacGia t ON s.MaTG = t.MaTG " +
//                    "INNER JOIN NXB n ON s.MaNXB = n.MaNXB " +
//                    "INNER JOIN TheLoai tl ON s.MaTheLoai = tl.MaTheLoai " +
//                    "WHERE s.MaSach = ?";
//
//            cursor = db.rawQuery(query, new String[]{id});
//
//            // Check if there's a result before processing
//            if (cursor.moveToFirst()) {
//                String maSach = cursor.getString(0);
//                String tenSach = cursor.getString(1);
//                String tenTheLoai = cursor.getString(2);
//                String tenTG = cursor.getString(3);
//                String tenNXB = cursor.getString(4);
//                String ngayXB = cursor.getString(5);
//                Integer giaBan = cursor.getInt(6);
//                Integer soLuong = cursor.getInt(7);
//                byte[] Anh = cursor.getBlob(8);
//
//                // Create BookDetailProperty object only if data exists
//                bookDetail = new BookDetailProperty(maSach, tenSach, giaBan, tenTheLoai, tenNXB, tenTG, ngayXB, soLuong, Anh);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        return bookDetail;
//    }
//    public List<BookProperty> getAllBooks(SQLiteDatabase db) {
//        List<BookProperty> books = new ArrayList<>();
//        int stt=1;
//        Cursor cursor = null;
//        try {
//            String query = "SELECT MaSach, TenSach FROM Sach";
//            cursor = db.rawQuery(query, null);
//            if (cursor.moveToFirst()) {
//                do {
//
//                    String id = cursor.getString(0);
//                    String name = cursor.getString(1);
//                    // Bạn có thể lấy thêm các trường khác từ cơ sở dữ liệu nếu cần
//                    books.add(new BookProperty(Integer.toString(stt), name, id)); // Điều chỉnh để phù hợp với lớp Selected
//                    stt++;
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return books;
//    }
//    public List<Selected> getAllAuthors(SQLiteDatabase db) {
//        List<Selected> authors = new ArrayList<>();
//        Cursor cursor = null;
//        try {
//            String query = "SELECT MaTG, TenTG FROM TacGia";
//            cursor = db.rawQuery(query, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    String id = cursor.getString(0);
//                    String name = cursor.getString(1);
//                    authors.add(new Selected(name, id));
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return authors;
//    }
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
//    public List<Selected> getAllPublishers(SQLiteDatabase db) {
//        List<Selected> publishers = new ArrayList<>();
//        Cursor cursor = null;
//        try {
//            String query = "SELECT MaNXB, TenNXB FROM NXB";
//            cursor = db.rawQuery(query, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    String id = cursor.getString(0);
//                    String name = cursor.getString(1);
//                    publishers.add(new Selected(name, id));
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return publishers;
//    }



//    public boolean insertCategory(SQLiteDatabase db, String maTheLoai, String tenTheLoai) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put("matheloai", maTheLoai);
//            values.put("tentheloai", tenTheLoai);
//
//            long result = db.insert("TheLoai", null, values);
//            return result != -1; // Trả về true nếu chèn thành công
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false; // Trả về false nếu có lỗi xảy ra
//        }
//    }
//
//    public CategoryProperty getCategoryDetail(SQLiteDatabase db, String id) {
//        CategoryProperty categoryDetail = null;
//
//        Cursor cursor = null;
//        try {
//            String query = "SELECT MaTheLoai, TenTheLoai " +
//                    "FROM TheLoai " +
//                    "WHERE MaTheLoai = ?";
//            cursor = db.rawQuery(query, new String[]{id});
//
//            if (cursor.moveToFirst()) {
//                String maTheLoai = cursor.getString(0);
//                String tenTheLoai = cursor.getString(1);
//
//                categoryDetail = new CategoryProperty(maTheLoai, tenTheLoai);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        return categoryDetail;
//    }
//    public boolean updateCategory(SQLiteDatabase db, String matheloai, String tentheloai) {
//        try {
//            ContentValues values = new ContentValues();
//            values.put("matheloai", matheloai);
//            values.put("tentheloai", tentheloai);
//            String selection = "matheloai = ?";
//            String[] selectionArgs = new String[]{matheloai};
//
//            int result = db.update("TheLoai", values, selection, selectionArgs);
//            return result > 0; // Trả về true nếu cập nhật thành công, ngược lại trả về false
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false; // Trả về false nếu có lỗi xảy ra
//        }
//    }
//
//    public boolean deleteCategory(SQLiteDatabase db, String categoryId) {
//        boolean isDeleted = false;
//
//        // Kiểm tra nếu thể loại đang được tham chiếu ở các bảng khác
//        if (isCategoryReferenced(db, categoryId)) {
//            // Thể loại đang được tham chiếu, không thể xóa
//            return false;
//        }
//
//        SQLiteStatement statement = null;
//        try {
//            String query = "DELETE FROM TheLoai WHERE MaTheLoai = ?";
//            statement = db.compileStatement(query);
//            statement.bindString(1, categoryId);
//
//            int rowsAffected = statement.executeUpdateDelete();
//            if (rowsAffected > 0) {
//                isDeleted = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (statement != null) {
//                statement.close();
//            }
//        }
//
//        return isDeleted;
//    }
//
//    private boolean isCategoryReferenced(SQLiteDatabase db, String categoryId) {
//        Cursor cursor = null;
//        try {
//            // Kiểm tra sự tồn tại của thể loại trong bảng sách
//            String query = "SELECT COUNT(*) FROM Sach WHERE MaTheLoai = ?";
//            cursor = db.rawQuery(query, new String[]{categoryId});
//
//            if (cursor.moveToFirst()) {
//                int count = cursor.getInt(0);
//                if (count > 0) {
//                    // Thể loại đang được tham chiếu
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return false;
//    }
}
