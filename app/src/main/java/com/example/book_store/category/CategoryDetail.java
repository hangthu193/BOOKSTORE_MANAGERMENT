package com.example.book_store.category;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book_store.DataBase;
import com.example.book_store.R;
import com.example.book_store.book.BookDetailProperty;

public class CategoryDetail extends AppCompatActivity {
    private DataBase dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_detail);

        String categoryId = getIntent().getStringExtra("CATEGORY_ID");

        dbHelper = new DataBase();
        db = dbHelper.initDatabase(this, "qlSach.db");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        CategoryProperty category = dbHelper.getCategoryDetail(db,categoryId);
        TextView matheloai = findViewById(R.id.cdt_matheloai);
        TextView tentheloai = findViewById(R.id.cdt_tentheloai);

//        matheloai.setText("Mã sách: " + category.getMaTheloai());
        tentheloai.setText("Tên sách: " + category.getTenTheloai());
    }
}