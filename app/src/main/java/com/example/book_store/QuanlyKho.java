package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book_store.adapter.AdapterKho;
import com.example.book_store.dao.DaoKho;
import com.example.book_store.model.Kho;

import java.util.ArrayList;

public class QuanlyKho extends AppCompatActivity {
    private ListView lvKho;
    private Button btnThemKho;
    private DaoKho daoKho;
    private AdapterKho adapterKho;
    private ArrayList<Kho> khoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quanly_kho);
        lvKho = findViewById(R.id.lvkho);
        btnThemKho = findViewById(R.id.btnthemkho);

        daoKho = new DaoKho(this);
        daoKho.open();

        // Lấy dữ liệu từ database và cập nhật vào ListView
        khoList = (ArrayList<Kho>) daoKho.getAllKho();
        adapterKho = new AdapterKho(this, khoList);
        lvKho.setAdapter(adapterKho);
        btnThemKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở activity thêm kho mới, bạn cần tạo activity thêm kho
                Intent intent = new Intent(QuanlyKho.this, KhoActivity.class);
                startActivity(intent);
            }
        });

    }
    protected void onDestroy() {
        super.onDestroy();
        daoKho.close();
    }
}