package com.example.book_store.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.book_store.KhoUpdate;
import com.example.book_store.R;
import com.example.book_store.DAO.DaoKho;
import com.example.book_store.model.Kho;
import com.example.book_store.updateAtivity;

import java.util.ArrayList;

public class AdapterKho extends BaseAdapter {
    Activity context;
    ArrayList<Kho> mylist;
    DaoKho daoKho;

    public AdapterKho(Activity context, ArrayList<Kho> mylist) {
        this.context = context;
        this.mylist = mylist;
        daoKho = new DaoKho(context);
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
        View row = inflater.inflate(R.layout.layout_kho,null);
        TextView txtmakho = (TextView) row.findViewById(R.id.txtmakho);
        TextView txtmasach = (TextView) row.findViewById(R.id.txtmasach);
        TextView txtngaynhap = (TextView) row.findViewById(R.id.txtngaynhap);
        TextView txtsoluong = (TextView) row.findViewById(R.id.txtsluong);
        TextView txtgianhap = row.findViewById((R.id.txtgianhap));
        Button btncapnhat = row.findViewById(R.id.btnCapNhatKho);

        Kho kho = mylist.get(position);
        txtmakho.setText("Mã kho: " + kho.getMaKho());
        txtmasach.setText("Mã sách: " + kho.getMaSach());
        txtngaynhap.setText("Ngày nhâp: " + kho.getNgayNhap());
        txtsoluong.setText("Số lượng nhập: " + kho.getSoLuongNhap());
        txtgianhap.setText("Giá nhập: " + kho.getGiaNhap());

        btncapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KhoUpdate.class);
                intent.putExtra("MaKho",kho.getMaKho());
                context.startActivity(intent);
            }
        });
        return row;

    }
}
