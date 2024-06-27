package com.example.book_store;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book_store.book.Book;
import com.example.book_store.category.Category;

public class Search extends AppCompatActivity {
    CardView tacgiaCard, khoCard, doanhthuCard,btn_sach,btn_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        tacgiaCard = findViewById(R.id.tacgiaCard);
        tacgiaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, MainActivity.class);
                startActivity(intent);
            }
        });
        khoCard = findViewById(R.id.khoCard);
        khoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, QuanlyKho.class);
                startActivity(intent);
            }
        });
        doanhthuCard = findViewById(R.id.DoanhthuCard);
        doanhthuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, doanhthu.class);
                startActivity(intent);
            }
        });
        //        btn_forgotpass = findViewById(R.id.btn_Forgotpass);
//        btn_forgotpass.setOnClickListener(v -> {
//            Intent myIntent = new Intent(MainActivity.this,Forgot_password.class);
//            startActivity(myIntent);
//
//        });
        btn_sach = findViewById(R.id.clothingCard);
        btn_sach.setOnClickListener(v -> {
            Intent myIntent = new Intent(Search.this, Book.class);
            startActivity(myIntent);
        });
        btn_category = findViewById(R.id.TheLoaiCard);
        btn_category.setOnClickListener(v -> {
            Intent myIntent = new Intent(Search.this, Category.class);
            startActivity(myIntent);
        });
        checkBatteryLevel();

    }
    private void checkBatteryLevel(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level / (float) scale * 100;

            if (batteryPct < 20) {
                // Hiển thị thông báo "Pin yếu!"
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Pin yếu!");
                builder.setMessage("Pin của bạn đang yếu. Vui lòng sạc pin để tiếp tục sử dụng.");
                builder.setIcon(R.drawable.baseline_battery_alert_24);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        }
    }
}