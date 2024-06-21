package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_store.dao.DaoTacGia;

public class updateAtivity extends AppCompatActivity {
    Button btnxn, btnhuy;
    EditText editID, edittenTG, editgt;
    DaoTacGia daoTacGia;
    String MaTG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ativity);

        // Khởi tạo DAO
        daoTacGia = new DaoTacGia(this);

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

        // Lấy thông tin Tác giả từ CSDL bằng DAO và hiển thị lên giao diện
        TacGia tacGia = daoTacGia.getTacGia(MaTG);
        if (tacGia != null) {
            editID.setText(tacGia.getMaTG());
            edittenTG.setText(tacGia.getTenTG());
            editgt.setText(tacGia.getGioiTinh());
        }
    }

    private void addControls() {
        btnxn = findViewById(R.id.btnxn);
        btnhuy = findViewById(R.id.btnhuy);
        editID = findViewById(R.id.editID);
        edittenTG = findViewById(R.id.edittenTG);
        editgt = findViewById(R.id.editgt);
    }

    private void update() {
        String ten = edittenTG.getText().toString();
        String gt = editgt.getText().toString();

        // Tạo đối tượng TacGia với thông tin đã cập nhật
        TacGia tacGia = new TacGia(MaTG, ten, gt);

        // Thực hiện cập nhật vào CSDL bằng DAO
        boolean isUpdated = daoTacGia.updateTacGia(tacGia);

        if (isUpdated) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        // Đóng activity updateAtivity
        finish();
    }

    private void cancel() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
