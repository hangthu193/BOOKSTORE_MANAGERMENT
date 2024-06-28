package com.example.book_store.password;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book_store.Database;
import com.example.book_store.MainActivity;
import com.example.book_store.R;
import com.example.book_store.Search;

public class newpass extends AppCompatActivity {

    EditText newpass, repnewpass;
    Button btn_updatepass;
    private Database dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton btn_quaylai1;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_newpass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new Database(this);
        db = dbHelper.initDatabase(this, "qlSach.db");

        btn_quaylai1 = findViewById(R.id.btn_quaylai1);
        btn_quaylai1.setOnClickListener(v -> {
            Intent myIntent = new Intent(newpass.this, Forgot_password.class);
            startActivity(myIntent);
        });

        newpass = findViewById(R.id.newpass);
        repnewpass = findViewById(R.id.renewpass);
        btn_updatepass = findViewById(R.id.btn_updatepass);

        btn_updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newpass.getText().toString();
                String reNewPassword = repnewpass.getText().toString();
                String manhanvien = getIntent().getStringExtra("manhanvien");

                if (newPassword.isEmpty() || reNewPassword.isEmpty()) {
                    Toast.makeText(newpass.this, "Vui lòng nhập mật khẩu mới và xác nhận lại", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(reNewPassword)) {
                    Toast.makeText(newpass.this, "Mật khẩu mới và xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isUpdated = dbHelper.updatePassword(db, manhanvien, newPassword);
                if (isUpdated) {
                    Toast.makeText(newpass.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(newpass.this, Search.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(newpass.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
