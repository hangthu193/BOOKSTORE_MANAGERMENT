package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.book_store.Adapter.NhaXuatBanAdapter;
import com.example.book_store.DAO.DaoNhaXuatBan;
import com.example.book_store.Model.NhaXuatBan;
import java.util.ArrayList;
import java.util.List;

public class QuanLy_NxbActivity extends AppCompatActivity {

    private ListView lvNXB;
    private DaoNhaXuatBan nxbDAO;
    private List<NhaXuatBan> danhSachNXB;
    private NhaXuatBanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nxb);

        lvNXB = findViewById(R.id.lvNXB);
        nxbDAO = new DaoNhaXuatBan(this);
        capNhatListView();

        // Xử lý sự kiện click vào FAB để thêm nhà xuất bản mới
        findViewById(R.id.btnThem).setOnClickListener(v -> {
            Intent intent = new Intent(QuanLy_NxbActivity.this, Them_nxbActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        capNhatListView();
    }

    private void capNhatListView() {
        danhSachNXB = nxbDAO.layDanhSachNhaXuatBan();
        adapter = new NhaXuatBanAdapter(this, danhSachNXB);
        lvNXB.setAdapter(adapter);
    }
}
