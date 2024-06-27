package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book_store.DAO.DaoKho;
import com.example.book_store.model.Kho;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KhoActivity extends AppCompatActivity {
    private EditText edtMaKho, edtSoLuongNhap, edtGiaNhap;
    private Spinner spinnerMaSach;
    private Button btnNhap;
    private TextView tvCurrentDate;
    private DaoKho daoKho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kho);
        daoKho = new DaoKho(this);

        edtMaKho = findViewById(R.id.edtMaKho);
        edtSoLuongNhap = findViewById(R.id.edtSoLuongNhap);
        edtGiaNhap= findViewById(R.id.edtGiaNhap);
        spinnerMaSach = findViewById(R.id.spinnerMaSach);
        tvCurrentDate = findViewById(R.id.tvNgayNhap);
        btnNhap = findViewById(R.id.btnNhapKho);
        List<String> maSachList = daoKho.getAllMaSach();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maSachList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaSach.setAdapter(adapter);
        String currentDate = getCurrentDate();
        tvCurrentDate.setText(currentDate);
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs()){
                    String maKho = edtMaKho.getText().toString().trim();
                    String maSach = spinnerMaSach.getSelectedItem().toString();
                    int soLuongNhap = Integer.parseInt(edtSoLuongNhap.getText().toString());
                    int giaNhap = Integer.parseInt(edtGiaNhap.getText().toString());
                    daoKho.open();
                    Kho kho = new Kho(maKho, maSach, currentDate,giaNhap, soLuongNhap);
                    long result = daoKho.insertKho(kho);
                    if (result > 0) {
                        Toast.makeText(KhoActivity.this, "Nhập kho thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(KhoActivity.this, QuanlyKho.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(KhoActivity.this, "Lỗi khi nhập kho!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(KhoActivity.this, QuanlyKho.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
    protected void onDestroy() {
        super.onDestroy();
        daoKho.close();
    }
    private boolean validateInputs() {
        if (edtMaKho.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Mã Kho", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinnerMaSach.getSelectedItem() == null) {
            Toast.makeText(this, "Vui lòng chọn Mã Sách", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtSoLuongNhap.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Số Lượng Nhập", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtGiaNhap.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Giá Nhập", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(edtSoLuongNhap.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số Lượng Nhập phải là số", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(edtGiaNhap.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá Nhập phải là số", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}