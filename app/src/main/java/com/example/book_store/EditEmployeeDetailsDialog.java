package com.example.book_store;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditEmployeeDetailsDialog extends AppCompatActivity {

    EditText etMaNhanVien, etTenNhanVienMoi, etGioiTinhMoi, etSoDienThoaiMoi, etDiaChiMoi, etTenDangNhapMoi;
    Button btnXacNhan, btnHuy;
    Database dbHelper;
    String employeeId;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_employee_details_dialog);

        dbHelper = new Database(this);

        etMaNhanVien = findViewById(R.id.etMaNhanVien);
        etTenNhanVienMoi = findViewById(R.id.etTenNhanVienMoi);
        etGioiTinhMoi = findViewById(R.id.etGioiTinhMoi);
        etSoDienThoaiMoi = findViewById(R.id.etSoDienThoaiMoi);
        etDiaChiMoi = findViewById(R.id.etDiaChiMoi);
        etTenDangNhapMoi = findViewById(R.id.etTenDangNhapMoi);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnHuy = findViewById(R.id.btnHuy);

        username = getIntent().getStringExtra("username");
//        loadEmployeeDetails(username);

        btnXacNhan.setOnClickListener(v -> updateEmployeeDetails());

        btnHuy.setOnClickListener(v -> finish()); // Sử dụng lambda để đóng Dialog nếu người dùng hủy bỏ
    } 

//    private void loadEmployeeDetails(String username) {
//        Cursor cursor = dbHelper.getEmployeeDetails(username);
//        if (cursor.moveToFirst()) {
//            employeeId = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_EMPLOYEE_ID));
//            etTenNhanVienMoi.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_EMPLOYEE_NAME)));
//            etGioiTinhMoi.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_EMPLOYEE_GENDER)));
//            etSoDienThoaiMoi.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_EMPLOYEE_PHONE)));
//            etDiaChiMoi.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_EMPLOYEE_ADDRESS)));
//            etTenDangNhapMoi.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_EMPLOYEE_USERNAME)));
//
//            // Disable editing of employee ID
//            etMaNhanVien.setEnabled(false);
//            etMaNhanVien.setText(employeeId);
//        }
//        cursor.close();
//    }

    private void updateEmployeeDetails() {
        String newTenNhanVien = etTenNhanVienMoi.getText().toString().trim();
        String newGioiTinh = etGioiTinhMoi.getText().toString().trim();
        String newSoDienThoai = etSoDienThoaiMoi.getText().toString().trim();
        String newDiaChi = etDiaChiMoi.getText().toString().trim();
        String newTenDangNhap = etTenDangNhapMoi.getText().toString().trim();

//        dbHelper.updateEmployeeDetails(employeeId, newTenNhanVien, newGioiTinh, newSoDienThoai, newDiaChi, newTenDangNhap);

        // Trả kết quả về cho EmployeeDetailsActivity
        setResult(RESULT_OK);
        finish(); // Đóng Dialog và quay lại màn hình trước đó
    }
}
