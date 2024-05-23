package com.example.book_store;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddActivity extends AppCompatActivity {
    String DATABASE_NAME="qlSach.db";
    Button btnxn, btnhuy;
    EditText editID, edittenTG, editgt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addControls();
        addEvents();

    }
    private void addEvents() {
        btnxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }
    private void addControls() {
        btnxn = (Button) findViewById(R.id.btnxn);
        btnhuy = (Button) findViewById(R.id.btnhuy);
        editID = (EditText) findViewById(R.id.editID);
        edittenTG = (EditText) findViewById(R.id.edittenTG);
        editgt = (EditText) findViewById(R.id.editgt);


    }
    private void insert(){
        String MaTG = editID.getText().toString();
        String ten = edittenTG.getText().toString();
        String gt = editgt.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaTG",MaTG);
        contentValues.put("TenTG",ten);
        contentValues.put("GioiTinh",gt);
        SQLiteDatabase database = Database.initDatabase(this,"qlSach.db");
        database.insert("TacGia",null,contentValues);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}