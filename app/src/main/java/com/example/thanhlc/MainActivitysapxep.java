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
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivitysapxep extends AppCompatActivity {
    GridView gridView;
    DatabaseReference Mdata;
    ArrayList<Hinhanh> listHinhAnh = new ArrayList<>();
    sanphamAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitysapxep);
        gridView=findViewById(R.id.grid_sapxep);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivitysapxep.this, MainActivitychitiet.class);
                intent.putExtra("ten",listHinhAnh.get(position).getTenhinh());
                intent.putExtra("gia",listHinhAnh.get(position).getGia());
                intent.putExtra("noidung",listHinhAnh.get(position).getNoidung());
                intent.putExtra("hinh",listHinhAnh.get(position).getLink());
                intent.putExtra("sdt",listHinhAnh.get(position).getSdt());
                intent.putExtra("tinhtrang",listHinhAnh.get(position).getTinhtrang());
                intent.putExtra("khuvuc",listHinhAnh.get(position).getKhuvuc());


                startActivity(intent);
            }
        });

        DatafromFirebase();
        adapter = new sanphamAdapter(this,listHinhAnh);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
                    String tinhtrang = ds.child("tinhtrang").getValue(String.class);
                    String khu =ds.child("khuvuc").getValue(String.class);
                    AtomicBoolean isSP = new AtomicBoolean();
                    listHinhAnh.forEach(sanpham -> {
                        if (sanpham.getId() == key) {
                            isSP.set(true);

                        }
                    });


                    Hinhanh ha = new Hinhanh(khu,tinhtrang,"",key,ten,gia,noidung,hinh,sdt);
                    listHinhAnh.add(ha);
                    Collections.sort(listHinhAnh, new Comparator<Hinhanh>() {
                        @Override
                        public int compare(Hinhanh o1, Hinhanh o2) {
                            if (Integer.parseInt(o1.getGia()) > Integer.parseInt(o2.getGia()))
                                return 1;
                            else {
                                if (o1.getGia() == o2.getGia()) {
                                    return 0;
                                } else {
                                    return -1;
                                }
                            }
                        }
                        //   return (o1.getTenhinh().compareTo(o2.getTenhinh()));


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