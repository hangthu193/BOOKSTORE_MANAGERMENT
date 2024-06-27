package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Sua_nxbActivity extends AppCompatActivity {

    EditText etMaNXB, etTenNXB, etDiaChi;
    Button btnHuy, btnXacNhan;
    Database dbHelper;
    String maNXBToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_nxb);

        etMaNXB = findViewById(R.id.etMaNXB);
        etTenNXB = findViewById(R.id.etTenNXB);
        etDiaChi = findViewById(R.id.etDiaChi);
        btnHuy = findViewById(R.id.btnHuy);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        Intent intent = getIntent();
        if (intent != null) {
            maNXBToUpdate = intent.getStringExtra("MaNXB");
            String tenNXB = intent.getStringExtra("TenNXB");
            String diaChi = intent.getStringExtra("DiaChi");

            etMaNXB.setText(maNXBToUpdate);
            etTenNXB.setText(tenNXB);
            etDiaChi.setText(diaChi);
        }

        dbHelper = new Database(this);

        btnHuy.setOnClickListener(v -> finish());
        btnXacNhan.setOnClickListener(v -> capnhatNXB());
    }

    private void capnhatNXB() {
        String tenNXB = etTenNXB.getText().toString().trim();
        String diaChiNXB = etDiaChi.getText().toString().trim();

        if (TextUtils.isEmpty(tenNXB)) {
            etTenNXB.setError("Vui lòng nhập tên nhà xuất bản");
            etTenNXB.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(diaChiNXB)) {
            etDiaChi.setError("Vui lòng nhập địa chỉ nhà xuất bản");
            etDiaChi.requestFocus();
            return;
        }

        dbHelper.updatePublisherDetails(maNXBToUpdate, tenNXB, diaChiNXB);

        Toast.makeText(this, "Cập nhật nhà xuất bản thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, QuanLy_NxbActivity.class);
        startActivity(intent);
        finish();
    }
}
