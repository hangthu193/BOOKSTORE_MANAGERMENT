package com.example.book_store.category;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book_store.DAO.DaoTheLoai;

import com.example.book_store.Database;
import com.example.book_store.MainActivity;
import com.example.book_store.R;
import com.example.book_store.book.Book;
import com.example.book_store.book.Bookdetail;
import com.example.book_store.model.BookDetailProperty;
import com.example.book_store.model.CategoryProperty;

public class CategoryDetail extends AppCompatActivity {
    private Database dbHelper;
    private SQLiteDatabase db;
    private  DaoTheLoai daoTheLoai;
    Button btn_update;
    Button btn_delete;
    ImageButton imgbtn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_detail);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String categoryId = getIntent().getStringExtra("CATEGORY_ID");

        dbHelper = new Database(this);
        db = dbHelper.initDatabase(this, "qlSach.db");
        daoTheLoai= new DaoTheLoai(this);
        CategoryProperty category = daoTheLoai.getCategoryDetail(db, categoryId);
        TextView matheloai = findViewById(R.id.cdt_matheloai);
        TextView tentheloai = findViewById(R.id.cdt_tentheloai);

        matheloai.setText("Mã thể loại: " + category.getMaTheloai());
        tentheloai.setText("Tên thể loại: " + category.getTenTheloai());


        imgbtn_back = findViewById(R.id.imgbtn_back);
        imgbtn_back.setOnClickListener(v -> {
            Intent myIntent = new Intent(CategoryDetail.this, Category.class);
            startActivity(myIntent);
        });


        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateCategory(Gravity.CENTER, daoTheLoai.getCategoryDetail(db, categoryId));
            }
        });
//        btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             boolean check=  daoTheLoai.deleteCategory(db, categoryId);
//             if (check){
//                 Toast.makeText(getApplicationContext(), "Xoá thể loại thành công!", Toast.LENGTH_SHORT).show();
//                 Intent myIntent = new Intent(CategoryDetail.this, Category.class);
//                 startActivity(myIntent);
//             }
//             else {
//                 Toast.makeText(getApplicationContext(), "Không thể xoá thể loại do đã tồn tại sách!", Toast.LENGTH_SHORT).show();
//                 Intent myIntent = new Intent(CategoryDetail.this, CategoryDetail.class);
//                 myIntent.putExtra("CATEGORY_ID", categoryId);
//                 startActivity(myIntent);
//             }
//            }
//        });

        Button btn_delete= findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteCategory(Gravity.CENTER, daoTheLoai.getCategoryDetail(db,categoryId));
            }
        });
    }

    private void openDeleteCategory(int gravity, CategoryProperty categoryProperty){
        String categoryId = getIntent().getStringExtra("CATEGORY_ID");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_deletebook);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }

        ImageButton imgbtn_back= dialog.findViewById(R.id.imgbtn_back);
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btn_xacnhan= dialog.findViewById(R.id.btn_xacnhan);
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkDel= daoTheLoai.deleteCategory(db,categoryId);
                if(checkDel){
                    Toast.makeText(CategoryDetail.this, "Xoá sách thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CategoryDetail.this, Category.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CategoryDetail.this, "Xoá sách không thành công!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }
    private boolean validateCategoryInputs(EditText tentheloai) {

        String tenTheLoaiValue = tentheloai.getText().toString().trim();



        if (tenTheLoaiValue.isEmpty()) {
            tentheloai.setError("Tên thể loại không được để trống");
            tentheloai.requestFocus();
            return false;
        }

        return true;
    }

    private void openUpdateCategory(int gravity, CategoryProperty categoryProperty) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_updatecategory);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
       TextView maTL= dialog.findViewById(R.id.matheloai);
        EditText tenTL=dialog.findViewById(R.id.tentheloai);
        maTL.setText(categoryProperty.getMaTheloai());
        tenTL.setText(categoryProperty.getTenTheloai());
        imgbtn_back= dialog.findViewById(R.id.imgbtn_back);
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_update= dialog.findViewById(R.id.btn_capnhattheloai);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateCategoryInputs(tenTL)) {
                dbHelper = new Database(CategoryDetail.this);
                boolean updateCategory = daoTheLoai.updateCategory(db, maTL.getText().toString(), tenTL.getText().toString());
                if (updateCategory) {

                    Toast.makeText(getApplicationContext(), "Cập nhật thể loại thành công!", Toast.LENGTH_SHORT).show();
                    try {
                        Intent myIntent = new Intent(CategoryDetail.this, CategoryDetail.class);
                        myIntent.putExtra("CATEGORY_ID", maTL.getText().toString());
                        startActivity(myIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(CategoryDetail.this, "Cập nhật thể loại không thành công!", Toast.LENGTH_SHORT).show();
                }}
            }
        });
        dialog.show();
    }
}