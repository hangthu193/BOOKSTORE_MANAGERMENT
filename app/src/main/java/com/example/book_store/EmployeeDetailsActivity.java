package com.example.book_store;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


public class EmployeeDetailsActivity extends AppCompatActivity {

    TextView tvMaNhanVien, tvTenNhanVien, tvGioiTinh, tvSoDienThoai, tvDiaChi, tvTenDangNhap;
    Button btnSuaThongTinCaNhan, btnDangXuat;
    Database dbHelper;
    String username;
    private ActivityResultLauncher<Intent> editEmployeeLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        dbHelper = new Database(this);
        SharedPreferences sharedPreferences = getSharedPreferences("SessionPref", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);

        tvMaNhanVien = findViewById(R.id.tvMaNhanVien);
        tvTenNhanVien = findViewById(R.id.tvTenNhanVien);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        tvSoDienThoai = findViewById(R.id.tvSoDienThoai);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvTenDangNhap = findViewById(R.id.tvTenDangNhap);
        btnSuaThongTinCaNhan = findViewById(R.id.btnSuaThongTinCaNhan);
        btnDangXuat = findViewById(R.id.btnDangXuat);


        loadEmployeeDetails(username);

        // Initialize Activity Result Launcher
        editEmployeeLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Reload lại thông tin nhân viên sau khi chỉnh sửa thành công
                        loadEmployeeDetails(username);
                    }
                });

        btnSuaThongTinCaNhan.setOnClickListener(v -> {
            // Chuyển sang EditEmployeeDetailsDialog để sửa thông tin cá nhân
            Intent intent = new Intent(EmployeeDetailsActivity.this, EditEmployeeDetailsDialog.class);
            intent.putExtra("username", username);
            editEmployeeLauncher.launch(intent); // Sử dụng launcher để khởi động activity
        });

        btnDangXuat.setOnClickListener(v -> {
            // Hiển thị dialog đăng xuất khi nhấn nút Đăng Xuất
            showLogoutDialog();
        });
    }

    private void showLogoutDialog() {
        // Tạo và hiển thị dialog đăng xuất
        LogoutDialogFragment dialog = new LogoutDialogFragment();
        dialog.show(getSupportFragmentManager(), "logout_dialog");
    }

    private void loadEmployeeDetails(String username) {
        Cursor cursor = dbHelper.getEmployeeDetails(username);
        if (cursor.moveToFirst()) {
            tvMaNhanVien.setText(cursor.getString(0));
            tvTenNhanVien.setText(cursor.getString(1));
            tvGioiTinh.setText(cursor.getString(2));
            tvSoDienThoai.setText(cursor.getString(3));
            tvDiaChi.setText(cursor.getString(4));
            tvTenDangNhap.setText(cursor.getString(5));
        }
        cursor.close();
    }
}
