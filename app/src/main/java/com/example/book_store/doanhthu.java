package com.example.book_store;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_store.dao.DaoKho;
import com.example.book_store.dao.DaoSach;
import com.example.book_store.model.Kho;
import com.example.book_store.model.Sach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class doanhthu extends AppCompatActivity {
    TextView tvDoanhThu, tvTienLai, tvSoLuongTon, tvStartDate, tvEndDate, tvResult;
    DaoKho daoKho;
    DaoSach daoSach;
    Calendar startDate, endDate;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanhthu);

        tvDoanhThu = findViewById(R.id.tvDoanhThu);
        tvTienLai = findViewById(R.id.tvTienLai);
        tvSoLuongTon = findViewById(R.id.tvSoLuongTon);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvResult = findViewById(R.id.tvResult);

        daoKho = new DaoKho(this);
        daoSach = new DaoSach(this);

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        // Định dạng ngày tháng
        sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        loadStatistics();
    }

    private void loadStatistics() {
        List<Sach> sachList = daoSach.getAllSach();
        double totalRevenue = 0;
        double totalProfit = 0;
        int totalStock = 0;

        for (Sach sach : sachList) {
            totalRevenue += sach.getGiaBan() * sach.getSoLuong();
            totalStock += sach.getSoLuong();
        }

        // Assuming cost price is 70% of the selling price for profit calculation
        totalProfit = totalRevenue * 0.7;

        tvDoanhThu.setText("Doanh thu: " + totalRevenue);
        tvTienLai.setText("Tiền lãi: " + totalProfit);
        tvSoLuongTon.setText("Số lượng tồn: " + totalStock);
    }

    public void showStartDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
            startDate.set(year, month, dayOfMonth);
            updateLabel(tvStartDate, startDate);
        }, startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void showEndDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
            endDate.set(year, month, dayOfMonth);
            updateLabel(tvEndDate, endDate);
        }, endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void updateLabel(TextView textView, Calendar calendar) {
        textView.setText(sdf.format(calendar.getTime()));
    }

    public void calculateQuantity(View view) {
        int quantity = getQuantityBetweenDates(startDate, endDate);
        tvResult.setText("Kết quả: " + quantity);
    }

    private int getQuantityBetweenDates(Calendar startDate, Calendar endDate) {
        List<Kho> khoList = daoKho.getAllKho();
        int quantity = 0;

        for (Kho kho : khoList) {
            try {
                Date importDate = sdf.parse(kho.getNgayNhap());
                if (!importDate.before(startDate.getTime()) && !importDate.after(endDate.getTime())) {
                    quantity += kho.getSoLuongNhap();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return quantity;
    }
}
