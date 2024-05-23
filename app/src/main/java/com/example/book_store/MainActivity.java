package com.example.book_store;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String DATABASE_NAME="qlSach.db";
    SQLiteDatabase database;
    //Khai báo ListView
    ListView lv;
    ArrayList<TacGia> mylist;
    MyAdapter myadapter;

    Button btnthem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnthem =(Button) findViewById(R.id.btnthem);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        lv = (ListView) findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new MyAdapter(MainActivity.this, mylist);
        lv.setAdapter(myadapter);

        database = Database.initDatabase(this, DATABASE_NAME);

// Truy vấn CSDL và cập nhật hiển thị lên Listview
        Cursor c = database.query("TacGia",null,null,null,null,null,null);
        mylist.clear();
        for(int i = 0; i<c.getCount();i++){
            c.moveToPosition(i);
            String id = c.getString(0);
            String ten = c.getString(1);
            String gioitinh = c.getString(2);
            mylist.add(new TacGia(id, ten, gioitinh));

        }

        myadapter.notifyDataSetChanged();
    }

}