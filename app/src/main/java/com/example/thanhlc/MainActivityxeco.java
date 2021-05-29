package com.example.thanhlc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

public class    MainActivityxeco extends AppCompatActivity {
GridView gridView;
    DatabaseReference Mdata;
    Button sapxesp;
    EditText text;
    ArrayList<Hinhanh> listHinhAnh = new ArrayList<>();
    sanphamAdapter adapter;
    String tendm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityxeco);
        gridView = findViewById(R.id.grxeco);

        loadData();
        DatafromFirebase();
        adapter = new sanphamAdapter(this,listHinhAnh);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivityxeco.this, MainActivitychitiet.class);
                intent.putExtra("ten",listHinhAnh.get(position).getTenhinh());
                intent.putExtra("gia",listHinhAnh.get(position).getGia());
                intent.putExtra("noidung",listHinhAnh.get(position).getNoidung());
                intent.putExtra("hinh",listHinhAnh.get(position).getLink());
                intent.putExtra("sdt",listHinhAnh.get(position).getSdt());
                intent.putExtra("tinhtrang",listHinhAnh.get(position).getTinhtrang());
                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();

    }
    private void DatafromFirebase(){
        listHinhAnh = new ArrayList<>();

        Mdata= FirebaseDatabase.getInstance().getReference("sanpham");
        Mdata.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //  Log.d("abc", "onDataChange: vao day");
                        String key = ds.getKey();
                        String d=ds.child("tendm").getValue(String.class);
                        String ten = ds.child("tenhinh").getValue(String.class);
                        String gia = ds.child("gia").getValue(String.class);
                        String hinh = ds.child("link").getValue(String.class);
                        String noidung = ds.child("noidung").getValue(String.class);
                        String sdt = ds.child("sdt").getValue(String.class);
                        String tinhtrang = ds.child("tinhtrang").getValue(String.class);
                        if(d.equals(tendm)) {
                            Hinhanh ha = new Hinhanh(tinhtrang,d, "", ten, gia, noidung, hinh,sdt);
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
    private void loadData(){
        Intent intent = getIntent();
        tendm = intent.getStringExtra("tendm");
    }
}