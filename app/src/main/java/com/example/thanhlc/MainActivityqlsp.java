package com.example.thanhlc;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivityqlsp extends AppCompatActivity {

    GridView gridView;
    DatabaseReference Mdata;
    sanphamAdapter adapter;
    ArrayList<Hinhanh> listHinhAnh = new ArrayList<>();
    public  static  String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityqlsp);
        gridView=findViewById(R.id.grid_qlsp);
        DatafromFirebase();
        adapter = new sanphamAdapter(this,listHinhAnh);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivityqlsp.this, capnhatsanpham.class);
                intent.putExtra("ten",listHinhAnh.get(position).getTenhinh());
                intent.putExtra("gia",listHinhAnh.get(position).getGia());
                intent.putExtra("noidung",listHinhAnh.get(position).getNoidung());
                intent.putExtra("hinh",listHinhAnh.get(position).getLink());
                intent.putExtra("sdt",listHinhAnh.get(position).getSdt());
                intent.putExtra("tinhtrang",listHinhAnh.get(position).getTinhtrang());
                intent.putExtra("id",listHinhAnh.get(position).getId());

                startActivity(intent);
            }
        });

    }
    private void DatafromFirebase(){
        listHinhAnh = new ArrayList<>();
        Mdata= FirebaseDatabase.getInstance().getReference().child("sanpham");
        Mdata.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    //  Log.d("abc", "onDataChange: vao day");
                    String key = ds.getKey();
                    String ten = ds.child("tenhinh").getValue(String.class);
                    String gia = ds.child("gia").getValue(String.class);
                    String hinh = ds.child("link").getValue(String.class);
                    String noidung = ds.child("noidung").getValue(String.class);
                    String sdt = ds.child("sdt").getValue(String.class);

                    AtomicBoolean isSP = new AtomicBoolean();
                        if(MainActivity.sdt.equals(sdt)) {
                            Hinhanh ha = new Hinhanh("","","", key, ten, gia, noidung, hinh,MainActivity.sdt);
                            listHinhAnh.add(ha);
                        }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}