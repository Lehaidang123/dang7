package com.example.thanhlc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Recyclerview_Search extends RecyclerView.Adapter<Recyclerview_Search.ViewHolder> {
    @NonNull
    private Context mcontext;
    ArrayList<Hinhanh> list;
    ArrayList<Hinhanh> filterList;

    TiengViet xuLyChuoiTiengViet = new TiengViet();
    public Recyclerview_Search(Context mcontext, ArrayList<Hinhanh> list) {
        this.mcontext = mcontext;
        this.filterList = list;
        this.list = list;
    }

    public Recyclerview_Search.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new Recyclerview_Search.ViewHolder(view);
    }

    public Filter getFilter() {
        return filter;
    }

    @Override
    public void onBindViewHolder(@NonNull Recyclerview_Search.ViewHolder holder, int position) {
        String url = filterList.get(position).getLink();
        Picasso.with(mcontext).load(url).into(holder.imvHSP);
        holder.txtTSP.setText(filterList.get(position).getTenhinh());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext,MainActivitychitiet.class);
                intent.putExtra("ten",filterList.get(position).getTenhinh());
                intent.putExtra("hinh",filterList.get(position).getLink());
                intent.putExtra("gia",filterList.get(position).getGia());
                intent.putExtra("noidung",filterList.get(position).getNoidung());
                intent.putExtra("sdt",filterList.get(position).getSdt());
                intent.putExtra("tinhtrang",filterList.get(position).getTinhtrang());
                intent.putExtra("khuvuc",filterList.get(position).getKhuvuc());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvHSP;
        TextView txtTSP;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvHSP = itemView.findViewById(R.id.imvHSP);
            txtTSP = itemView.findViewById(R.id.txtTSP);
            relativeLayout = itemView.findViewById(R.id.relative_item_search);
        }
    }
    private Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            /// kh???i t???o bi???n result
            FilterResults filterResults= new FilterResults();
            String keySearch = constraint.toString();
            /// khi keysearch co gia tri
            if (keySearch != null && keySearch.toString().length() > 0) {
                /// th?? m??nh kh???i t???o list tr???ng ????? add d??? li???u sao khi check v??o
                ArrayList<Hinhanh> filteredList = new ArrayList<>();
                String pattrn= keySearch.toLowerCase().trim();
                for(Hinhanh item : list){
                    /// check d??? li???u ????? add
                    if (xuLyChuoiTiengViet.ConvertString(item.getTenhinh().toLowerCase())
                            .contains(xuLyChuoiTiengViet.ConvertString(pattrn))) {
                        filteredList.add(item);
                    }
                }
                /// g??n v??o gi?? tr??? sao khi check xong
                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
            }
            else{
                /// g??n l???i gi?? tr??? all
                filterResults.values = list;
                filterResults.count = list.size();
//                synchronized (list) {
//                    filterResults.values = list;
//                    filterResults.count = list.size();
//                }
            }
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterList = (ArrayList<Hinhanh>) results.values;
            notifyDataSetChanged();
        }
    };
}
