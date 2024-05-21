package com.example.book_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<TacGia> mylist;

    public MyAdapter(Context context, ArrayList<TacGia> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_items,null);
        TextView txtid = (TextView) row.findViewById(R.id.txtid);
        TextView txtten = (TextView) row.findViewById(R.id.txtten);
        TextView txtgt = (TextView) row.findViewById(R.id.txtgt);
        Button btnsua = (Button) row.findViewById(R.id.btnsua);
        Button btnxoa = (Button) row.findViewById(R.id.btnxoa);

        TacGia tacgia = mylist.get(position);
        txtid.setText("Mã TG: "+tacgia.MaTG);
        txtten.setText("Tên TG: "+tacgia.TenTG);
        txtgt.setText("Giới tính: "+tacgia.GioiTinh);
        return row;
    }
}
