package com.example.book_store;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book_store.book.Book;
import com.example.book_store.category.Category;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String DATABASE_NAME="qlSach.db";
        SQLiteDatabase database;
        Button btn_forgotpass;
        Button btn_sach;
        Button btn_category;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        database = DataBase.initDatabase(this, DATABASE_NAME);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_forgotpass = findViewById(R.id.btn_Forgotpass);
        btn_forgotpass.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this,Forgot_password.class);
            startActivity(myIntent);

        });
        btn_sach = findViewById(R.id.btn_sach);
        btn_sach.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, Book.class);
            startActivity(myIntent);
        });
        btn_category = findViewById(R.id.btn_category);
        btn_category.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, Category.class);
            startActivity(myIntent);
        });
    }

}