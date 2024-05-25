package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Forgot_password extends AppCompatActivity {

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
        btn_updatepass = findViewById(R.id.btn_Forgotpass);
        btn_updatepass.setOnClickListener(v -> {
            Intent myIntent = new Intent(Forgot_password.this,newpass.class);
            startActivity(myIntent);

        });
        btn_quaylai = findViewById(R.id.btn_quaylai);
        btn_quaylai.setOnClickListener(v -> {
            Intent myIntent = new Intent(Forgot_password.this, MainActivity.class);
            startActivity(myIntent);
        });
    }
}