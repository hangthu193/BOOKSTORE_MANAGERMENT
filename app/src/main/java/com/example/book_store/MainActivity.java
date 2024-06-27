package com.example.book_store;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book_store.DAO.DaoTacGia;

import java.util.ArrayList;

import com.example.book_store.book.Book;
import com.example.book_store.category.Category;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD = 1;
    private ActivityResultLauncher<Intent> addActivityResultLauncher;

    DaoTacGia daoTacGia;
    ListView lv;
    ArrayList<TacGia> mylist;
    MyAdapter myadapter;

    Button btnthem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String DATABASE_NAME="qlSach.db";
        SQLiteDatabase database;
        Button btn_forgotpass;
        Button btn_sach;
        Button btn_category;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DAO for database operations
        daoTacGia = new DaoTacGia(this);

        // Initialize ListView and adapter
        lv = findViewById(R.id.lvkho);
        mylist = new ArrayList<>();
        myadapter = new MyAdapter(MainActivity.this, mylist);
        lv.setAdapter(myadapter);

        // Button to open AddActivity
        btnthem = findViewById(R.id.btnthemkho);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                addActivityResultLauncher.launch(intent);
            }
        });

        // Load data from database and update ListView
        loadData();

        // Register ActivityResultLauncher for handling AddActivity result
        addActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Reload data if AddActivity returns RESULT_OK
                        loadData();
                    }
                }
        );

        // ListView item click listener to handle updates (if needed)
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TacGia selectedTacGia = mylist.get(position);
                openUpdateActivity(selectedTacGia.getMaTG());
            }
        });
//        btn_forgotpass = findViewById(R.id.btn_Forgotpass);
//        btn_forgotpass.setOnClickListener(v -> {
//            Intent myIntent = new Intent(MainActivity.this,Forgot_password.class);
//            startActivity(myIntent);
//
//        });
//        btn_sach = findViewById(R.id.btn_sach);
//        btn_sach.setOnClickListener(v -> {
//            Intent myIntent = new Intent(MainActivity.this, Book.class);
//            startActivity(myIntent);
//        });
//        btn_category = findViewById(R.id.btn_category);
//        btn_category.setOnClickListener(v -> {
//            Intent myIntent = new Intent(MainActivity.this, Category.class);
//            startActivity(myIntent);
//        });
    }

    private void loadData() {
        mylist.clear();
        mylist.addAll(daoTacGia.getAllTacGia());
        myadapter.notifyDataSetChanged();
    }

    private void openUpdateActivity(String MaTG) {
        Intent intent = new Intent(MainActivity.this, updateAtivity.class);
        intent.putExtra("MaTG", MaTG);
        startActivity(intent);
    }
}
