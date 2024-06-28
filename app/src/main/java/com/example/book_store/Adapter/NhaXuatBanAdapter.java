package com.example.book_store.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.book_store.R;
import com.example.book_store.Sua_nxbActivity;
import com.example.book_store.model.NhaXuatBan;

import java.util.List;

public class NhaXuatBanAdapter extends BaseAdapter {
    private Context context;
    private List<NhaXuatBan> danhSachNXB;

    public NhaXuatBanAdapter(Context context, List<NhaXuatBan> danhSachNXB) {
        this.context = context;
        this.danhSachNXB = danhSachNXB;
    }

    @Override
    public int getCount() {
        return danhSachNXB.size();
    }

    @Override
    public Object getItem(int position) {
        return danhSachNXB.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nxb, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvMaNXB = convertView.findViewById(R.id.tvMaNXB);
            viewHolder.tvTenNXB = convertView.findViewById(R.id.tvTenNXB);
            viewHolder.tvDiaChi = convertView.findViewById(R.id.tvDiaChi);
            viewHolder.btnSua = convertView.findViewById(R.id.btnSua);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NhaXuatBan nhaXuatBan = danhSachNXB.get(position);
        viewHolder.tvMaNXB.setText(nhaXuatBan.getMaNXB());
        viewHolder.tvTenNXB.setText(nhaXuatBan.getTenNXB());
        viewHolder.tvDiaChi.setText(nhaXuatBan.getDiaChi());

        // Xử lý sự kiện khi click vào nút "Sửa"
        viewHolder.btnSua.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang Sua_nxbActivity
            Intent intent = new Intent(context, Sua_nxbActivity.class);
            intent.putExtra("MaNXB", nhaXuatBan.getMaNXB());
            intent.putExtra("TenNXB", nhaXuatBan.getTenNXB());
            intent.putExtra("DiaChi", nhaXuatBan.getDiaChi());
            context.startActivity(intent);
        });
        return convertView;
    }

    static class ViewHolder {
        TextView tvMaNXB, tvTenNXB, tvDiaChi;
        Button btnSua;
    }
}
