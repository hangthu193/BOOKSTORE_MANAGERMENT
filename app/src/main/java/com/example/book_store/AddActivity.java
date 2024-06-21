package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_store.dao.DaoTacGia;

public class AddActivity extends AppCompatActivity {
    DaoTacGia daoTacGia;
    Button btnxn, btnhuy;
    EditText editID, edittenTG, editgt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Khởi tạo DAO
        daoTacGia = new DaoTacGia(this);

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
        btnxn = findViewById(R.id.btnxn);
        btnhuy = findViewById(R.id.btnhuy);
        editID = findViewById(R.id.editID);
        edittenTG = findViewById(R.id.edittenTG);
        editgt = findViewById(R.id.editgt);
    }

    private void insert() {
        String MaTG = editID.getText().toString();
        String ten = edittenTG.getText().toString();
        String gt = editgt.getText().toString();

        if (MaTG.isEmpty() || ten.isEmpty() || gt.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        // Tạo đối tượng TacGia
        TacGia tacGia = new TacGia(MaTG, ten, gt);

        // Thực hiện thêm mới vào CSDL bằng DAO
        boolean isSuccess = daoTacGia.insertTacGia(tacGia);

        if (isSuccess) {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        // Đóng activity AddActivity
        finish();
    }

    private void cancel() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
