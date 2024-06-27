package com.example.book_store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.book_store.model.NhaXuatBan;
import com.example.book_store.R;
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

        NhaXuatBan nxb = mListNXB.get(position);
        tvMaNXB.setText(nxb.getMaNXB());
        tvTenNXB.setText(nxb.getTenNXB());
        tvDiaChi.setText(nxb.getDiaChi());

        return view;
    }

    // Phương thức để cập nhật dữ liệu của adapter
    public void updateData(List<NhaXuatBan> newList) {
        mListNXB.clear();
        mListNXB.addAll(newList);
        notifyDataSetChanged();
    }
}
