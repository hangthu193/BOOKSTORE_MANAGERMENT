package com.example.book_store;

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

public class Forgot_password extends AppCompatActivity {
    EditText manhanvien;
    private DataBase dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btn_updatepass;
        ImageButton btn_quaylai;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DataBase();
        db = dbHelper.initDatabase(this, "qlSach.db");
        manhanvien = findViewById(R.id.manhanvien);
        btn_updatepass = findViewById(R.id.btn_Forgotpass);
        btn_quaylai = findViewById(R.id.btn_quaylai);

        btn_quaylai.setOnClickListener(v -> {
            Intent myIntent = new Intent(Forgot_password.this, MainActivity.class);
            startActivity(myIntent);
        });

        btn_updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String manv = manhanvien.getText().toString();
                if (manv.isEmpty()) {
                    Toast.makeText(Forgot_password.this, "Vui lòng nhập mã nhân viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean checkmanv = dbHelper.checkmanv(db, manv);
                if (checkmanv) {
                    Intent myIntent = new Intent(Forgot_password.this, newpass.class);
                    myIntent.putExtra("manhanvien", manv);
                    startActivity(myIntent);
                } else {
                    Toast.makeText(Forgot_password.this, "Mã nhân viên không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
