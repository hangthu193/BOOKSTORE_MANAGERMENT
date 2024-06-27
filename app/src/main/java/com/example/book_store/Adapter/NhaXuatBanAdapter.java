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

    private Context mContext;
    private List<NhaXuatBan> mListNXB;

    public NhaXuatBanAdapter(Context context, List<NhaXuatBan> listNXB) {
        mContext = context;
        mListNXB = listNXB;
    }

    @Override
    public int getCount() {
        return mListNXB.size();
    }

    @Override
    public Object getItem(int position) {
        return mListNXB.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_nxb, null);
        }

        TextView tvMaNXB = view.findViewById(R.id.tvMaNXB);
        TextView tvTenNXB = view.findViewById(R.id.tvTenNXB);
        TextView tvDiaChi = view.findViewById(R.id.tvDiaChi);
        Button btnSua = view.findViewById(R.id.btnSua);

        NhaXuatBan nxb = mListNXB.get(position);
        tvMaNXB.setText(nxb.getMaNXB());
        tvTenNXB.setText(nxb.getTenNXB());
        tvDiaChi.setText(nxb.getDiaChi());

        btnSua.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, Sua_nxbActivity.class);
            intent.putExtra("MaNXB", nxb.getMaNXB());
            intent.putExtra("TenNXB", nxb.getTenNXB());
            intent.putExtra("DiaChi", nxb.getDiaChi());
            mContext.startActivity(intent);
        });

        return view;
    }

    public void updateData(List<NhaXuatBan> newList) {
        mListNXB.clear();
        mListNXB.addAll(newList);
        notifyDataSetChanged();
    }
}
