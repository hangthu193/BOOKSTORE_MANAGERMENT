package com.example.book_store;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_store.DAO.DaoTacGia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Activity context;
    ArrayList<TacGia> mylist;
    DaoTacGia daoTacGia;


    public MyAdapter(Activity context, ArrayList<TacGia> mylist) {
        this.context = context;
        this.mylist = mylist;
        daoTacGia = new DaoTacGia(context);
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

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, updateAtivity.class);
                intent.putExtra("MaTG",tacgia.MaTG);
                context.startActivity(intent);
            }
        });
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        boolean isDeleted = daoTacGia.deleteTacGia(tacgia.getMaTG());
                        if (isDeleted) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            mylist.remove(tacgia); // Remove from local list
                            notifyDataSetChanged(); // Update ListView
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return row;
    }

}
