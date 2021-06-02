package com.example.thanhlc;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivitytimkiem extends AppCompatActivity {
        RecyclerView recyclerView;
        EditText editText;
    DatabaseReference Mdata;
    ArrayList<Hinhanh> listHinhAnh;
    Recyclerview_Search adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitytimkiem);
        editText=findViewById(R.id.testimkiem);
        DatafromFirebase();
       editText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               adapter.getFilter().filter(s);
           }

           @Override
           public void afterTextChanged(Editable s) {

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
                    String tendm = ds.child("tendm").getValue(String.class);
                    String ten = ds.child("tenhinh").getValue(String.class);
                    String gia = ds.child("gia").getValue(String.class);
                    String hinh = ds.child("link").getValue(String.class);
                    String noidung = ds.child("noidung").getValue(String.class);
                    String sdt = ds.child("sdt").getValue(String.class);
                    String tinhtrang = ds.child("tinhtrang").getValue(String.class);
                    String khu = ds .child("khuvuc").getValue(String.class);
                    Hinhanh ha = new Hinhanh(khu,tinhtrang,tendm,key,ten,gia,noidung,hinh,sdt);
                    listHinhAnh.add(ha);
                }
                recyclerView= findViewById(R.id.listimkiem);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivitytimkiem.this);
                adapter = new Recyclerview_Search(MainActivitytimkiem.this, listHinhAnh);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}