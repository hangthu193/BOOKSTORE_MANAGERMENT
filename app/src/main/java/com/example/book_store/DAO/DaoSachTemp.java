package com.example.book_store.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.book_store.DataBase;
import com.example.book_store.model.Selected;
import com.example.book_store.model.BookDetailProperty;
import com.example.book_store.model.BookProperty;

import java.util.ArrayList;
import java.util.List;

public class DaoSach {
    private SQLiteDatabase database;
    private DataBase dbHelper;
    private Context mContext;

    public DaoSach(Context mContext) {
        this.mContext = mContext;
    }


    public boolean insertBook(SQLiteDatabase db, String maSach, String tenSach, String tacgia, String theloai, String nxb, String ngayxb, Integer giaban, Integer soluong,byte[] anh) {

        try {
            ContentValues values = new ContentValues();
            values.put("masach", maSach);
            values.put("tensach", tenSach);
            values.put("matg", tacgia);
            values.put("matheloai", theloai);
            values.put("manxb", nxb);
            values.put("ngayxb", ngayxb);
            values.put("giaban", giaban);
            values.put("soluong", soluong);
            values.put("anh",anh);

            long result = db.insert("Sach", null, values);
            return result != -1; // Trả về true nếu chèn thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public boolean updateBook(SQLiteDatabase db, String tenSach, String maSach, String tacgia, String theloai, String nxb, String ngayxb, Integer giaban, Integer soluong,byte[] anh){
        try {
            ContentValues values = new ContentValues();
            values.put("masach", maSach);
            values.put("tensach", tenSach);
            values.put("matg", tacgia);
            values.put("matheloai", theloai);
            values.put("manxb", nxb);
            values.put("ngayxb", ngayxb);
            values.put("giaban", giaban);
            values.put("soluong", soluong);
            values.put("anh",anh);
            String selection = "masach = ?"; // Sử dụng "masach" thay vì "matg"
            String[] selectionArgs = new String[]{maSach};

            long result = db.update("Sach", values, selection, selectionArgs);
            return result != -1; // Trả về true nếu chèn thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public boolean deleteBook(SQLiteDatabase db, String bookId) {
        boolean isDeleted = false;

        SQLiteStatement statement = null;
        try {
            String query = "DELETE FROM Sach WHERE MaSach = ?";
            statement = db.compileStatement(query);
            statement.bindString(1, bookId);

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


    public BookDetailProperty getBookDetail(SQLiteDatabase db, String id) {
        BookDetailProperty bookDetail = null;

        Cursor cursor = null;
        try {
            String query = "SELECT s.MaSach, s.TenSach,tl.TenTheLoai, t.TenTG, n.TenNXB, s.NgayXB, s.GiaBan, s.SoLuong,s.Anh " +
                    "FROM Sach s " +
                    "INNER JOIN TacGia t ON s.MaTG = t.MaTG " +
                    "INNER JOIN NXB n ON s.MaNXB = n.MaNXB " +
                    "INNER JOIN TheLoai tl ON s.MaTheLoai = tl.MaTheLoai " +
                    "WHERE s.MaSach = ?";

            cursor = db.rawQuery(query, new String[]{id});

            // Check if there's a result before processing
            if (cursor.moveToFirst()) {
                String maSach = cursor.getString(0);
                String tenSach = cursor.getString(1);
                String tenTheLoai = cursor.getString(2);
                String tenTG = cursor.getString(3);
                String tenNXB = cursor.getString(4);
                String ngayXB = cursor.getString(5);
                Integer giaBan = cursor.getInt(6);
                Integer soLuong = cursor.getInt(7);
                byte[] Anh = cursor.getBlob(8);

                // Create BookDetailProperty object only if data exists
                bookDetail = new BookDetailProperty(maSach, tenSach, giaBan, tenTheLoai, tenNXB, tenTG, ngayXB, soLuong, Anh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return bookDetail;
    }
    public List<BookProperty> getAllBooks(SQLiteDatabase db) {
        List<BookProperty> books = new ArrayList<>();
        int stt=1;
        Cursor cursor = null;
        try {
            String query = "SELECT MaSach, TenSach FROM Sach";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {

                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    // Bạn có thể lấy thêm các trường khác từ cơ sở dữ liệu nếu cần
                    books.add(new BookProperty(Integer.toString(stt), name, id)); // Điều chỉnh để phù hợp với lớp Selected
                    stt++;
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return books;
    }
    public List<Selected> getAllAuthors(SQLiteDatabase db) {
        List<Selected> authors = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT MaTG, TenTG FROM TacGia";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    authors.add(new Selected(name, id));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return authors;
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
    public List<Selected> getAllPublishers(SQLiteDatabase db) {
        List<Selected> publishers = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT MaNXB, TenNXB FROM NXB";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    publishers.add(new Selected(name, id));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return publishers;
    }
}
