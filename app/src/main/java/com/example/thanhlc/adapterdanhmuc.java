package com.example.thanhlc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterdanhmuc extends BaseAdapter {
    Context context;

    public adapterdanhmuc(Context context, int layout, ArrayList<Danhmuc> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }

    private int layout;
    private ArrayList<Danhmuc>arraylist;

    @Override

    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return this.arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        TextView textView= convertView.findViewById(R.id.testdanhmuc);
      textView.setText(arraylist.get(position).Ten);
        ImageView imageView = convertView.findViewById(R.id.ddanhmuc);
        String url = arraylist.get(position).getHinh();
        Picasso.with(context).load(url).into(imageView);
        return convertView;
    }
}
