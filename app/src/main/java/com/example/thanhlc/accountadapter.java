package com.example.thanhlc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class accountadapter extends BaseAdapter implements Filterable {

    Context mcontext;
    ArrayList<Helperclass> listHinhAnh;
    private LayoutInflater inflater;
    public accountadapter(Context mcontext, ArrayList<Helperclass> listHinhAnh) {
        this.mcontext = mcontext;
        this.listHinhAnh = listHinhAnh;
    }

    @Override
    public int getCount() {
        return listHinhAnh.size();
    }

    @Override
    public Object getItem(int position) {
        return listHinhAnh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Helperclass helperclass = (Helperclass) getItem(position);
        if (inflater == null) {
            inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.itemacc, null);
        }
        TextView txt1 = convertView.findViewById(R.id.accc);
        txt1.setText(helperclass.getUsernamae());

        return convertView;
    }



    @Override
    public Filter getFilter() {
        return null;
    }
}
