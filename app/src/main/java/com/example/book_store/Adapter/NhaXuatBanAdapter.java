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

    private Context Context;
    private List<NhaXuatBan> ListNXB;

    public NhaXuatBanAdapter(Context context, List<NhaXuatBan> listNXB) {
        Context = context;
        ListNXB = listNXB;
    }

    @Override
    public int getCount() {
        return ListNXB.size();
    }

    @Override
    public Object getItem(int position) {
        return ListNXB.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_nxb, null);
        }

        TextView tvMaNXB = view.findViewById(R.id.tvMaNXB);
        TextView tvTenNXB = view.findViewById(R.id.tvTenNXB);
        TextView tvDiaChi = view.findViewById(R.id.tvDiaChi);

        NhaXuatBan nxb = ListNXB.get(position);
        tvMaNXB.setText(nxb.getMaNXB());
        tvTenNXB.setText(nxb.getTenNXB());
        tvDiaChi.setText(nxb.getDiaChi());

        return view;
    }
}
