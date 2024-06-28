package com.example.book_store;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KhoUpdate extends AppCompatActivity {
    private EditText edtMaKho, edtSoLuongNhap, edtGiaNhap;
    private TextView cldDate;
    private Spinner spinnerMaSach;
    private Button btnUpdateKho;
    private DaoKho daoKho;
    private Kho kho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kho_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtMaKho = findViewById(R.id.edtMaKho);
        spinnerMaSach = findViewById(R.id.spinnerMaSach);
        edtSoLuongNhap = findViewById(R.id.edtSoLuongNhap);
        edtGiaNhap = findViewById(R.id.edtGiaNhap);
        btnUpdateKho = findViewById(R.id.btnUpdateKho);
        cldDate = findViewById(R.id.tvNgayNhap);
        cldDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                int year;
                int month;
                int day;
                final Calendar calendar = Calendar.getInstance();
                try {
                    Date date = sdf.parse(kho.getNgayNhap());
                    calendar.setTime(date);
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                DatePickerDialog date = new DatePickerDialog(KhoUpdate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        Date selectedDate = calendar.getTime();

                        // Định dạng ngày đã chọn
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = sdf.format(selectedDate);
                        cldDate.setText(formattedDate);
                    }
                },year,month,day);
                date.show();
            }
        });

        daoKho = new DaoKho(this);
        daoKho.open();
        kho = (Kho) getIntent().getSerializableExtra("Kho");

        // Populate fields with kho data
        populateFields();

        // Populate spinner with MaSach values from database
        populateMaSachSpinner();
        btnUpdateKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKho();
            }
        });
    }
    private void populateFields() {
        if (kho != null) {
            edtMaKho.setText(kho.getMaKho());
            // Assume spinner is populated after this method
            cldDate.setText(kho.getNgayNhap());
            edtSoLuongNhap.setText(String.valueOf(kho.getSoLuongNhap()));
            edtGiaNhap.setText(String.valueOf(kho.getGiaNhap()));
        }
    }

    private void populateMaSachSpinner() {
        List<String> maSachList = daoKho.getAllMaSach();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maSachList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaSach.setAdapter(adapter);

        // Set the spinner to the correct value
        if (kho != null) {
            int position = adapter.getPosition(kho.getMaSach());
            spinnerMaSach.setSelection(position);
        }
    }

    private void updateKho() {
        // Get values from inputs
        String maKho = edtMaKho.getText().toString().trim();
        String maSach = spinnerMaSach.getSelectedItem().toString();
        String ngayNhap = cldDate.getText().toString(); // Assume current date is used as NgayNhap
        int soLuongNhap;
        int giaNhap;

        try {
            soLuongNhap = Integer.parseInt(edtSoLuongNhap.getText().toString().trim());
            giaNhap = Integer.parseInt(edtGiaNhap.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập đúng định dạng cho Số lượng nhập và Giá nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update Kho object
        kho.setMaKho(maKho);
        kho.setMaSach(maSach);
        kho.setNgayNhap(ngayNhap);
        kho.setSoLuongNhap(soLuongNhap);
        kho.setGiaNhap(giaNhap);

        // Update in database
        long result = daoKho.updateKho(kho);
        if (result > 0) {
            Toast.makeText(this, "Cập nhật kho thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, QuanlyKho.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Cập nhật kho thất bại", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, QuanlyKho.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        daoKho.close();
    }
}