package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.book_store.DAO.DaoNhaXuatBan;
import com.example.book_store.model.NhaXuatBan;

import java.util.List;

public class Them_nxbActivity extends AppCompatActivity {

    EditText etMaNXB, etTenNXB, etDiaChi;
    Button btnHuy, btnXacNhan;
    Database dbHelper;
    DaoNhaXuatBan nxbDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nxb);

        etMaNXB = findViewById(R.id.etMaNXB);
        etTenNXB = findViewById(R.id.etTenNXB);
        etDiaChi = findViewById(R.id.etDiaChi);
        btnHuy = findViewById(R.id.btnHuy);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        dbHelper = new Database(this);
        nxbDAO = new DaoNhaXuatBan(this);

        btnHuy.setOnClickListener(v -> finish());
        btnXacNhan.setOnClickListener(v -> themNXB());
    }

    private void themNXB() {
        String maNXB = etMaNXB.getText().toString().trim();
        String tenNXB = etTenNXB.getText().toString().trim();
        String diaChiNXB = etDiaChi.getText().toString().trim();

        if (TextUtils.isEmpty(maNXB)) {
            etMaNXB.setError("Vui lòng nhập mã nhà xuất bản");
            etMaNXB.requestFocus();
            return;
        }
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

        boolean success = nxbDAO.themNhaXuatBan(maNXB, tenNXB, diaChiNXB);

        if (success) {
            Toast.makeText(this, "Thêm nhà xuất bản thành công", Toast.LENGTH_SHORT).show();

            // Update publisher list after adding
            List<NhaXuatBan> danhSachNXB = nxbDAO.layDanhSachNhaXuatBan();

            Intent intent = new Intent(this, QuanLy_NxbActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Thêm nhà xuất bản thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
