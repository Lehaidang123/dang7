package com.example.thanhlc;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class adminquanlysp extends AppCompatActivity {
    DatabaseReference Mdata;
    ArrayList<Hinhanh> manghinh= new ArrayList<>();
    sanphamAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    GridView lvhinhanh;
    ArrayList<Hinhanh> listHinhAnh = new ArrayList<>();
    public  static String  t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminquanlysp);
        lvhinhanh = findViewById(R.id.adminql);
        DatafromFirebase();
        adapter = new sanphamAdapter(this,listHinhAnh);
        lvhinhanh.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lvhinhanh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(adminquanlysp.this);
                alertDialogBuilder.setMessage("Bán có muốn xóa sản phẩm này!");
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // xóa sp đang nhấn giữ
                     Mdata = FirebaseDatabase.getInstance().getReference().child("sanpham").child(listHinhAnh.get(position).getId());
                        Mdata.removeValue();
                        Toast.makeText(adminquanlysp.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();

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
    private void DatafromFirebase() {
        listHinhAnh = new ArrayList<>();
        Mdata = FirebaseDatabase.getInstance().getReference().child("sanpham");
        Mdata.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //  Log.d("abc", "onDataChange: vao day");
                    String key = ds.getKey();
                    String ten = ds.child("tenhinh").getValue(String.class);
                    String gia = ds.child("gia").getValue(String.class);
                    String hinh = ds.child("link").getValue(String.class);
                    String noidung = ds.child("noidung").getValue(String.class);
                    String tinh = ds.child("tinhtrang").getValue(String.class);
                    String sdt = ds.child("sdt").getValue(String.class);
                    String khu = ds.child("khuvuc").getValue(String.class);


                    Hinhanh ha = new Hinhanh(khu, tinh, "", key, ten, gia, noidung, hinh, sdt);
                    listHinhAnh.add(ha);
                    Collections.sort(listHinhAnh, new Comparator<Hinhanh>() {
                        @Override
                        public int compare(Hinhanh o1, Hinhanh o2) {

                            return (o1.getTenhinh().compareTo(o2.getTenhinh()));

                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}