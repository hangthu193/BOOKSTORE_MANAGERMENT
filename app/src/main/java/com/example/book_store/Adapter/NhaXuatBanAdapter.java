package com.example.book_store.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.book_store.R;
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_nxb, null);
        }
        TextView tvMaNXB = convertView.findViewById(R.id.tvMaNXB);
        TextView tvTenNXB = convertView.findViewById(R.id.tvTenNXB);
        TextView tvDiaChi = convertView.findViewById(R.id.tvDiaChi);

        NhaXuatBan nhaXuatBan = danhSachNXB.get(position);
        tvMaNXB.setText(nhaXuatBan.getMaNXB());
        tvTenNXB.setText(nhaXuatBan.getTenNXB());
        tvDiaChi.setText(nhaXuatBan.getDiaChi());

        return convertView;
    }
}
