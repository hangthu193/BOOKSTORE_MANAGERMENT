package com.example.book_store;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

public class updateAtivity extends AppCompatActivity {
    String DATABASE_NAME="qlSach.db";
    Button btnxn, btnhuy;
    EditText editID, edittenTG, editgt;

    String MaTG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ativity);
        addControls();
        initUI();
        addEvents();
    }

    private void addEvents() {
        btnxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private void initUI() {
        Intent intent = getIntent();
        MaTG = intent.getStringExtra("MaTG");
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        Cursor c = database.rawQuery("SELECT * FROM TacGia where MaTG = ?", new String[]{MaTG});
        c.moveToFirst();
        String maTG = c.getString(0);
        String tenTG = c.getString(1);
        String gt = c.getString(2);
        editID.setText(maTG);
        edittenTG.setText(tenTG);
        editgt.setText(gt);
    }

    private void addControls() {
        btnxn = (Button) findViewById(R.id.btnxn);
        btnhuy = (Button) findViewById(R.id.btnhuy);
        editID = (EditText) findViewById(R.id.editID);
        edittenTG = (EditText) findViewById(R.id.edittenTG);
        editgt = (EditText) findViewById(R.id.editgt);


    }
    private void update(){
        String ten = edittenTG.getText().toString();
        String gt = editgt.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenTG",ten);
        contentValues.put("GioiTinh",gt);
        SQLiteDatabase database = Database.initDatabase(this,"qlSach.db");
        database.update("TacGia", contentValues,"MaTG = ?",new String[]{MaTG});
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}