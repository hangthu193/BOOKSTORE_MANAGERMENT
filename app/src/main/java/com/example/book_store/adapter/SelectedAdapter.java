package com.example.book_store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book_store.R;
import com.example.book_store.model.Selected;

import java.util.List;

public class SelectedAdapter extends ArrayAdapter<Selected>{
    public SelectedAdapter(@NonNull Context context, int resource, @NonNull List<Selected> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext() ).inflate(R.layout.item_selected,parent,false);
        TextView tvSelected = convertView.findViewById(R.id.tv_selected);
        Selected selected= this.getItem(position);
        if(selected != null){
            tvSelected.setText(selected.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        TextView tvCategory = convertView.findViewById(R.id.tv_category);
        Selected selected= this.getItem(position);
        if(selected != null){
            tvCategory.setText(selected.getName());
        }
        return convertView;
    }
}
