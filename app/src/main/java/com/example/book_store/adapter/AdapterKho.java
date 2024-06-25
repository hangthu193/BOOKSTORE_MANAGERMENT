package com.example.book_store.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.book_store.R;
import com.example.book_store.TacGia;
import com.example.book_store.dao.DaoKho;
import com.example.book_store.dao.DaoTacGia;
import com.example.book_store.model.Kho;

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

        Kho kho = mylist.get(position);
        txtmakho.setText("Ma kho: " + kho.getMaKho());
        txtmasach.setText("Ma sach: " + kho.getMaSach());
        txtngaynhap.setText("Ngay nhap: " + kho.getNgayNhap());
        txtsoluong.setText("So luong nhap: " + kho.getSoLuongNhap());
        txtgianhap.setText("Gia nhap: " + kho.getGiaNhap());
        return row;

    }
}
