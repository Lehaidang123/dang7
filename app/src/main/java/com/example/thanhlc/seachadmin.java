package com.example.thanhlc;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class seachadmin extends RecyclerView.Adapter<seachadmin.ViewHolder> {
    @NonNull
    private Context mcontext;
    ArrayList<Helperclass> list;
    ArrayList<Helperclass> filterList;

    TiengViet xuLyChuoiTiengViet = new TiengViet();
    public seachadmin(Context mcontext, ArrayList<Helperclass> list) {
        this.mcontext = mcontext;
        this.filterList = list;
        this.list = list;
    }

    public seachadmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemacc, parent, false);
        return new seachadmin.ViewHolder(view);
    }

    public Filter getFilter() {
        return filter;
    }

    @Override
    public void onBindViewHolder(@NonNull seachadmin.ViewHolder holder, int position) {

        holder.txtTSP.setText(filterList.get(position).getUsernamae());
        holder.t.setText(filterList.get(position).sdt);
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mcontext);
                alertDialogBuilder.setMessage("Bán có muốn xóa tài khoản này!");
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // xóa sp đang nhấn giữ
                        DatabaseReference Mdata;
                        Mdata = FirebaseDatabase.getInstance().getReference().child("account").child(list.get(position).getUsernamae());
                        Mdata.removeValue();
                       Toast.makeText(mcontext, "Xóa Thành Công", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //không làm gì
                    }
                });
                alertDialogBuilder.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvHSP;
        TextView txtTSP,t;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvHSP = itemView.findViewById(R.id.imvHSP);
            txtTSP = itemView.findViewById(R.id.accc);
            t= itemView.findViewById(R.id.acc);
            relativeLayout = itemView.findViewById(R.id.relative_item_searc);
        }
    }
    private Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            /// khởi tạo biến result
            FilterResults filterResults= new FilterResults();
            String keySearch = constraint.toString();
            /// khi keysearch co gia tri
            if (keySearch != null && keySearch.toString().length() > 0) {
                /// thì mình khởi tạo list trống để add dữ liệu sao khi check vào
                ArrayList<Helperclass> filteredList = new ArrayList<>();
                String pattrn= keySearch.toLowerCase().trim();
                for(Helperclass item : list){
                    /// check dử liệu để add
                    if (xuLyChuoiTiengViet.ConvertString(item.getSdt().toLowerCase())
                            .contains(xuLyChuoiTiengViet.ConvertString(pattrn))) {
                        filteredList.add(item);
                    }
                }
                /// gán vào giá trị sao khi check xong
                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
            }
            else{
                /// gán lại giá trị all
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
            filterList = (ArrayList<Helperclass>) results.values;
            notifyDataSetChanged();
        }
    };
}
