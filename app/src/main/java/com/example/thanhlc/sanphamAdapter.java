package com.example.thanhlc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class sanphamAdapter extends BaseAdapter implements Filterable {
    Context mcontext;
    ArrayList<Hinhanh> listHinhAnh;
    private LayoutInflater inflater;
    public sanphamAdapter(Context mcontext, ArrayList<Hinhanh> listHinhAnh){
        this.mcontext = mcontext;
        this.listHinhAnh = listHinhAnh;
    }
    @Override
    public int getCount() {
        return this.listHinhAnh.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listHinhAnh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hinhanh hinhanh = (Hinhanh) getItem(position);
        if (inflater == null) {
            inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.donghinhanh, null);
        }
        TextView txt1 = convertView.findViewById(R.id.tens);
        txt1.setText(hinhanh.getTenhinh());
        TextView txt2 = convertView.findViewById(R.id.gias);
        txt2.setText(hinhanh.getGia());
     //   TextView txt3 = convertView.findViewById(R.id.nds);
     //   txt3.setText(hinhanh.getNoidung());
        ImageView imageView = convertView.findViewById(R.id.food);
        ImageView yeuthich =convertView.findViewById(R.id.yeuthich);
        String t = yeuthich.getContext().toString();

        yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( yeuthich !=null) {
                    yeuthich.setImageResource(R.drawable.timday);
                }else
                {
                    yeuthich.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }

            }
        });
        String url = listHinhAnh.get(position).getLink();
        Picasso.with(mcontext).load(url).into(imageView);
        return convertView;
    }


    @Override
    public Filter getFilter() {
        return null;
    }
}
